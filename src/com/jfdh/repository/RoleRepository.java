package com.jfdh.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Role;
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
	public Role findByRoleKey(String roleKey);

	public List<Role> findByRoleKeyNotIn(String roleKey);
}
