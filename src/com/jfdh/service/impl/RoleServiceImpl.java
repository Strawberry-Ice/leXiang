package com.jfdh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfdh.model.Role;
import com.jfdh.repository.RoleRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Role> findAll() {
		return (List<Role>) roleRepository.findByRoleKeyNotIn("sys_admin");
	}

}
