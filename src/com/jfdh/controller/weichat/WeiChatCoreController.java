package com.jfdh.controller.weichat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfdh.controller.weichat.message.ImageMessage;
import com.jfdh.controller.weichat.message.InputMessage;
import com.jfdh.controller.weichat.message.MsgType;
import com.jfdh.controller.weichat.message.OutputMessage;
import com.jfdh.controller.weichat.util.HttpUtil;
import com.jfdh.controller.weichat.util.WeiChatHelper;
import com.jfdh.controller.weichat.util.XStreamFactory;
import com.jfdh.httpmodel.Json;
import com.jfdh.httpmodel.WeiChartHttpUser;
import com.jfdh.model.GovOrg;
import com.jfdh.model.User;
import com.jfdh.service.UserService;
import com.jfdh.service.weichat.WeichatUserService;
import com.jfdh.util.Constants;
import com.jfdh.util.StringUtil;
import com.thoughtworks.xstream.XStream;

@Controller
public class WeiChatCoreController {
	public static final Logger LOG = LoggerFactory
			.getLogger(WeiChatCoreController.class);
	@Value("#{configProperties['weichatToken']}")
	private String weichatToken;

	@Value("#{configProperties['appid']}")
	private String appid;
	@Value("#{configProperties['secret']}")
	private String secret;
	@Value("#{configProperties['needLogin']}")
	private String needLogin;
	@Autowired
	private WeiChatHelper weiChatHelper;
	@Autowired
	private WeichatUserService weichatUserService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/weichatCoreController", method = RequestMethod.GET)
	@ResponseBody
	public String weichatCoreController(String signature, String echostr,
			String timestamp, String nonce) {
		LOG.info("signature>>>>>>>" + signature);
		LOG.info("echostr>>>>>>>" + echostr);
		LOG.info("timestamp>>>>>>>" + timestamp);
		LOG.info("nonce>>>>>>>" + nonce);

		LOG.info("weichatToken>>>>>>>" + weichatToken);

		String[] str = { weichatToken, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = null;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(bigStr.getBytes("UTF-8"));
			digest = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 确认请求来至微信
		if (digest.equals(signature)) {
			return echostr;
		}
		return null;

	}

	@RequestMapping(value = "/weichatCoreController", method = RequestMethod.POST,produces = "application/xml;charset=UTF-8")
	@ResponseBody
	public String weichatCoreController(HttpServletRequest request) {
		try {
			// 处理接收消息
			ServletInputStream in = request.getInputStream();
			// 将POST流转换为XStream对象
			XStream xs = XStreamFactory.createXstream();
			xs.processAnnotations(InputMessage.class);
			xs.processAnnotations(OutputMessage.class);
			// 将指定节点下的xml节点数据映射为对象
			xs.alias("xml", InputMessage.class);
			// 将流转换为字符串
			StringBuilder xmlMsg = new StringBuilder();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				xmlMsg.append(new String(b, 0, n, "UTF-8"));
			}
			// 将xml内容转换为InputMessage对象
			InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());

			String servername = inputMsg.getToUserName();// 服务端
			String custermname = inputMsg.getFromUserName();// 客户端
			long createTime = inputMsg.getCreateTime();// 接收时间
			Long returnTime = System.currentTimeMillis() / 1000;// 返回时间

			// 取得消息类型
			String msgType = inputMsg.getMsgType();
			// 根据消息类型获取对应的消息内容
			if (msgType.equals(MsgType.Text.toString())) {
				// 文本消息
				LOG.info("开发者微信号：" + inputMsg.getToUserName());
				LOG.info("发送方帐号：" + inputMsg.getFromUserName());
				LOG.info("消息创建时间：" + inputMsg.getCreateTime()
						+ new Date(createTime * 1000l));
				LOG.info("消息内容：" + inputMsg.getContent());
				LOG.info("消息Id：" + inputMsg.getMsgId());

				StringBuffer str = new StringBuffer();
				str.append("<xml>");
				str.append("<ToUserName><![CDATA[" + custermname
						+ "]]></ToUserName>");
				str.append("<FromUserName><![CDATA[" + servername
						+ "]]></FromUserName>");
				str.append("<CreateTime>" + returnTime + "</CreateTime>");
				str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
				str.append("<Content><![CDATA[你说的是：" + inputMsg.getContent()
						+ "，吗？]]></Content>");
				str.append("</xml>");
				LOG.info(str.toString());
				return new String(str.toString().getBytes(), "utf-8");
			}
			// 获取并返回多图片消息
			if (msgType.equals(MsgType.Image.toString())) {
				LOG.info("获取多媒体信息");
				LOG.info("多媒体文件id：" + inputMsg.getMediaId());
				LOG.info("图片链接：" + inputMsg.getPicUrl());
				LOG.info("消息id，64位整型：" + inputMsg.getMsgId());

				OutputMessage outputMsg = new OutputMessage();
				outputMsg.setFromUserName(servername);
				outputMsg.setToUserName(custermname);
				outputMsg.setCreateTime(returnTime);
				outputMsg.setMsgType(msgType);
				ImageMessage images = new ImageMessage();
				images.setMediaId(inputMsg.getMediaId());
				outputMsg.setImage(images);
				LOG.info("xml转换：/n" + xs.toXML(outputMsg));
				return xs.toXML(outputMsg);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	@RequestMapping("/weichatMyCommunity2")
	public String weichatMyCommunity2(String signature, String echostr,
			String timestamp, String nonce, Model model) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=APPID&redirect_uri=REDIRECT_URI&response_type=code"
				+ "&scope=SCOPE&state=STATE#wechat_redirect";
		url = url.replace("APPID", appid)
				.replace("REDIRECT_URI", "REDIRECT_URI")
				.replace("SCOPE", "snsapi_userinfo").replace("STATE", "123");
		String json = HttpUtil.getUrl(url);
		LOG.info("url" + json);
		return "";

	}
	
	@RequestMapping("/weichatLogo")
	public String logo(String code, Model model,HttpSession session){
		return "/weichat/smile_community";
	}

	@RequestMapping("/weichatMyCommunity")
	public String weichatMyCommunity(String code, Model model,
			HttpSession session) {

		try {
			JsonNode jsonUser = weiChatHelper.getUserInfo(code);
			String openid = jsonUser.path("openid").asText();
			// 测试
			// String openid="oynFNuD0yadopNg_Lxy_ywoaKnBk";
			User user = weichatUserService.findByOpenid(openid);
			if (null == user) {
				WeiChartHttpUser httpUser = new WeiChartHttpUser();
				LOG.info("jsonUser" + jsonUser);
				httpUser.setOpenid(openid);
				httpUser.setNeedReceive(true);
				httpUser.setNickName(jsonUser.path("nickname").asText());
				httpUser.setAddress(jsonUser.path("country").asText()
						+ jsonUser.path("province").asText()
						+ jsonUser.path("city").asText());
				httpUser.setHeadimgurl(jsonUser.path("headimgurl").asText());
//				List<GovOrg> govOrgs = userService.findAllGovOrgs();
//				model.addAttribute("govOrgs", govOrgs);
//				model.addAttribute("httpUser", httpUser);
//				return "/weichat/index_login";
				user=weichatUserService.saveUserInfo(httpUser);
				session.setAttribute("USER_FOR_WEICHAT", user);
				return "redirect:/weichatActivity/activity/"
				+ Constants.CONSTANTS_OCCASION_SQUARE;
			} else {
				if ("匿名用户".equalsIgnoreCase(user.getNickName()) || user.getGovOrg()==null) {
					session.setAttribute("USER_FOR_WEICHAT", user);
					return "redirect:/weichatActivity/activity/"
							+ Constants.CONSTANTS_OCCASION_SQUARE;
				} else {
					Date bundling = user.getBundlingDate();
					Date now = new Date();
					long l = now.getTime() - bundling.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					if (day - 30 >= 0 && "Y".equalsIgnoreCase(needLogin)) {
						WeiChartHttpUser httpUser = new WeiChartHttpUser();
						httpUser.setId(user.getId());
						httpUser.setOpenid(openid);
						httpUser.setNickName(jsonUser.path("nickname").asText());
						httpUser.setAddress(jsonUser.path("country").asText()
								+ jsonUser.path("province").asText()
								+ jsonUser.path("city").asText());
						List<GovOrg> govOrgs = userService.findAllGovOrgs();
						model.addAttribute("govOrgs", govOrgs);
						model.addAttribute("httpUser", httpUser);
						return "/weichat/index_login";
					} else {
						session.setAttribute("USER_FOR_WEICHAT", user);
						return "redirect:/weichatActivity/activity/"
								+ Constants.CONSTANTS_OCCASION_COMMUNITY;
					}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "/weichat/index_login";

	}

	@RequestMapping("/weichatLoginForAnonymity")
	public String weichatLoginForAnonymity(String code, Model model,
			HttpSession session) {

		try {
			JsonNode jsonUser = weiChatHelper.getUserInfo(code);
			String openid = jsonUser.path("openid").asText();
			// String openid="oynFNuD0yadopNg_Lxy_ywoaKnBk";
			User user = weichatUserService.findByOpenid(openid);
			if (null != user && ("匿名用户".equalsIgnoreCase(user.getNickName()) || user.getGovOrg()==null)) {
				WeiChartHttpUser httpUser = new WeiChartHttpUser();
				LOG.info("jsonUser" + jsonUser);
				httpUser.setOpenid(openid);
				httpUser.setNeedReceive(true);
				httpUser.setId(user.getId());
				httpUser.setNickName(jsonUser.path("nickname").asText());
				httpUser.setAddress(jsonUser.path("country").asText()
						+ jsonUser.path("province").asText()
						+ jsonUser.path("city").asText());
				httpUser.setHeadimgurl(jsonUser.path("headimgurl").asText());
				List<GovOrg> govOrgs = userService.findAllGovOrgs();
				model.addAttribute("govOrgs", govOrgs);
				model.addAttribute("httpUser", httpUser);
				return "/weichat/index_login_for_anonymity";
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "/weichat/index_login";

	}

	@RequestMapping("/test")
	public String test(String code, Model model) {
		WeiChartHttpUser httpUser = new WeiChartHttpUser();
		List<GovOrg> govOrgs = userService.findAllGovOrgs();
		model.addAttribute("govOrgs", govOrgs);
		model.addAttribute("httpUser", httpUser);
		return "/weichat/index_login";

	}

	@RequestMapping("/weiChatGovOrgChildren")
	@ResponseBody
	public Json getGovOrgChildren(HttpServletRequest request) {
		String id = request.getParameter("id");
		Json j = new Json();
		List<GovOrg> govOrgs = userService.getGovOrgsByParentId(id);
		j.setObj(govOrgs);
		j.setSuccess(true);
		return j;
	}

}
