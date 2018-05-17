package com.jfdh.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.model.Resource;
import com.jfdh.model.Role;
import com.jfdh.repository.UserRepository;

/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式： WebUserDetails webUserDetails =
 * (WebUserDetails)SecurityContextHolder
 * .getContext().getAuthentication().getPrincipal();
 * 
 * 或在JSP中： <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可 权限验证类
 * 
 * @author lanyuan 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// 登录验证
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// System.err.println("-----------MyUserDetailServiceImpl loadUserByUsername ----------- ");

		// 取得用户的权限
		com.jfdh.model.User user = userRepository
				.findByUserName(username);
//		System.out.println(username+">>>>>>>>>>>>>>>>>>>");
		if (user == null)
			throw new UsernameNotFoundException(username + " not exist!");
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);
		// 封装成spring security的user
		BackendUserDtail userdetail = new BackendUserDtail(user.getUserName(), user.getPassword(),
				user.getState().booleanValue(), // 账号状态 0 表示停用 1表示启用
				true, true, true, grantedAuths // 用户的权限
		);
		BeanUtils.copyProperties(user, userdetail);
		
		return userdetail;
	}

	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(
			com.jfdh.model.User user) {
		Role role = user.getRole();
		List<Resource> resources = role.getResources();
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		authSet.add(new SimpleGrantedAuthority("ROLE_Role_" + role.getRoleKey()));
		for (Resource res : resources) {
			authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getResKey()));
		}
		return authSet;
	}
}