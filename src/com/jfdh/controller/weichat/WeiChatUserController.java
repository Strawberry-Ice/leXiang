package com.jfdh.controller.weichat;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.WeiChartHttpUser;
import com.jfdh.model.Activity;
import com.jfdh.model.Application;
import com.jfdh.model.User;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.weichat.WeichatUserService;
import com.jfdh.util.Constants;
import com.jfdh.util.FilePathHelper;

@Controller
public class WeiChatUserController {
	@Value("#{configProperties['fileUploadDisk']}")
	private String fileUploadDisk;

	@Value("#{configProperties['logoPath']}")
	private String logoPath;
	@Value("#{configProperties['fileUploadPath']}")
	private String fileUploadPath;
	@Autowired
	private WeichatUserService weichatUserService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/weichatShowUserInfo")
	public String weichatShowUserInfo(HttpSession session,Model model) {
		String userId=getUserId(session);
		User user=weichatUserService.findById(userId);
//		ScoreExchangeRecord record=weichatUserService.findTop1RecordByUserId(userId);
//		Application application=weichatUserService.findTop1ApplicationByUserId(userId);
//		model.addAttribute("record", record);
//		model.addAttribute("application", application);
		WeiChartHttpUser httpUser=new WeiChartHttpUser();
		BeanUtils.copyProperties(user, httpUser);
		model.addAttribute("user", user);
		model.addAttribute("httpUser", httpUser);
		return "/weichat/my_info";

	}
	
	@RequestMapping("/weichatCreateUser")
	public String weichatCreateUser(Model model,WeiChartHttpUser httpUser,HttpSession session,HttpServletRequest request) {
		System.out.println("request.getParameter"+request.getParameter("mode"));
		User user=weichatUserService.saveUserInfo(httpUser);
		session.setAttribute("USER_FOR_WEICHAT", user);
		if("匿名用户".equalsIgnoreCase(user.getNickName()) || user.getGovOrg()==null){
			return "redirect:/weichatActivity/activity/"+Constants.CONSTANTS_OCCASION_SQUARE;
		}else{
			return "redirect:/weichatActivity/activity/"+Constants.CONSTANTS_OCCASION_COMMUNITY;
		}

	}
	
	@RequestMapping("/weichatEditUserInfo")
	public String weichatEditUserInfo(HttpSession session,Model model) {
		String userId=getUserId(session);
		User user=weichatUserService.findById(userId);
		WeiChartHttpUser httpUser=new WeiChartHttpUser();
		BeanUtils.copyProperties(user, httpUser);
		model.addAttribute("user", user);
		model.addAttribute("httpUser", httpUser);
		return "/weichat/my_info_edit";

	}
	
	@RequestMapping(value = "/weichatUpdateUserInfo")
	public String weichatUpdateUserInfo(HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile file,WeiChartHttpUser httpUser) {
		if (null!=file&&StringUtils.isNotEmpty(file.getOriginalFilename())) {
			String temp = file.getOriginalFilename();
			String fileName = System.currentTimeMillis()
					+ temp.substring(temp.lastIndexOf("."));
			File targetFile = new File(FilePathHelper.getPath(fileUploadDisk + fileUploadPath
					+ logoPath), fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			try {
				file.transferTo(targetFile);
				httpUser.setLogo(FilePathHelper.getPath(fileUploadPath
					+ logoPath)+"/"+fileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		weichatUserService.updateUserInfo(httpUser,getUserId(session));
		return "redirect:/weichatShowUserInfo";
	}
	
	
	@RequestMapping("/weichatShowMyApplications")
	public String weichatShowMyApplications(HttpSession session,
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows,Model model) {
		String userId=getUserId(session);
		DataRequest request = new DataRequest();
		request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		request.setRows(StringUtils.isEmpty(rows) ? 6 : Integer.valueOf(rows));
		Page<Application> pagingData = weichatUserService
				.findAllMyApplication(request,userId);
		if(pagingData.hasNext()){
			model.addAttribute("nextParam","page="+(request.getPage()+1)+"&rows="+request.getRows());
		}
		model.addAttribute("pagingData", pagingData);
		return "/weichat/my_application";

	}
	
	@RequestMapping("/weichatShowMyActivity")
	public String weichatShowMyActivity(
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows,Model model,HttpSession session) {
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		DataRequest request = new DataRequest();
		request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		request.setRows(StringUtils.isEmpty(rows) ? 6 : Integer.valueOf(rows));
		Page<Activity> pagingData = weichatUserService.findAllMyActivity(request, user.getId());
		if(pagingData.hasNext()){
			model.addAttribute("nextParam","page="+(request.getPage()+1)+"&rows="+request.getRows());
		}
		model.addAttribute("pagingData", pagingData);
		return "/weichat/my_activity";

	}
	
	private User getUser(HttpSession session){
		return (User) session.getAttribute("USER_FOR_WEICHAT");
	}
	
	private String getUserId(HttpSession session){
//		return "2";
		return getUser(session).getId();
	}
	
}
