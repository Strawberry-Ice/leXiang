package com.jfdh.controller.weichat.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class WeichatSessionTimeOutFilter extends OncePerRequestFilter {

	private WebApplicationContext wac;
	private String appid;
	private String redirect_uri;
	@Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		// 方法一：wac =WebApplicationContextUtils.getRequiredWebApplicationContext(
		// this.getServletContext());
		wac = WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
		PropertiesFactoryBean config = wac.getBean(PropertiesFactoryBean.class);
		try {
			appid = config.getObject().getProperty(
					"appid");
			redirect_uri=config.getObject().getProperty(
					"redirect_uri");
			redirect_uri=URLEncoder.encode(redirect_uri, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// wac的类型：org.springframework.web.context.support.XmlWebApplicationContext
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		// 判断用户是否登录，进行页面的处理
		if (request.getRequestURI().startsWith("/weichat")&&null == request.getSession().getAttribute("USER_FOR_WEICHAT")) {
			// 未登录用户，重定向到登录页面
			response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?&appid="+appid+"&redirect_uri="+redirect_uri+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
			return;
		} else {
			// 已登录用户，允许访问
			chain.doFilter(request, response);
		}
	}

}
