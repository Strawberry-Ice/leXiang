package com.jfdh.tag;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jfdh.controller.weichat.util.WeiChatHelper;

public class WechatShareTag extends SimpleTagSupport {
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		WeiChatHelper wc = wac.getBean(WeiChatHelper.class);
		String nonceStr = UUID.randomUUID().toString();
		long timestamp = System.currentTimeMillis() / 1000;
		String[] str = { "noncestr=" + nonceStr,
				"jsapi_ticket=" + wc.getAccessTicket(),
				"timestamp=" + timestamp, "url=" + url };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + "&" + str[1] + "&" + str[2] + "&" + str[3];
		// SHA1加密
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(bigStr.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("wx.config({");
		sb.append("debug: false,");
		sb.append("appId: '" + wc.getAppid() + "',");
		sb.append("timestamp: " + timestamp + ",");
		sb.append("nonceStr: '" + nonceStr + "',");
		sb.append("signature: '" + signature + "',");
		sb.append("jsApiList: [");
		sb.append("'onMenuShareTimeline',");
		sb.append("'onMenuShareAppMessage'");
		sb.append("]");
		sb.append("});");
		sb.append("</script>");
		getJspContext().getOut().write(sb.toString());
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

}
