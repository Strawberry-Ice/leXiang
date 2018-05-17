package com.jfdh.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.jfdh.model.Resource;
import com.jfdh.repository.ResourceRepository;

/**
 * 加载资源与权限的对应关系
 * */
@Service
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	@Autowired
	private ResourceRepository resourceRepository;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * @PostConstruct是Java EE 5引入的注解， Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
	 * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，
	 * 
	 *                                        //加载所有资源与权限的关系
	 */
	@PostConstruct
	private void loadResourceDefine() {
		// System.err.println("-----------MySecurityMetadataSource loadResourceDefine ----------- ");
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> menus = (List<Resource>) resourceRepository
					.findAll();
			for (Resource m : menus) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_"
						+ m.getResKey());
				configAttributes.add(configAttribute);
				resourceMap.put(m.getResUrl(), configAttributes);
			}
		}
	}

	// 返回所请求资源所需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// System.err.println("-----------MySecurityMetadataSource getAttributes ----------- ");
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		// System.out.println("requestUrl is " + requestUrl);
		if (resourceMap == null) {
			loadResourceDefine();
		}
		if (requestUrl.indexOf("?") > -1) {
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
		Collection<ConfigAttribute> configAttributes = resourceMap
				.get(requestUrl);
		return configAttributes;
	}

}