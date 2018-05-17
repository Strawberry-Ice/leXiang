package com.jfdh.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.User;
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String>{
	public User findByUserName(String userName);

	public Page<User> findByGovOrg_Id(String id, Pageable pageable);

	public Page<User> findByOrg_Id(String id, Pageable pageable);

	public User findByOpenid(String openid);

	public Page<User> findAll(Specification<User> spe, Pageable pageable);
	
	public User findByUserNameAndPassword(String userName,String password);
	
	public List<User> findByGovOrg_Id(String govOrgId);
	
	public List<User> findByOpenidIsNull();

	public List<User> findByVirtual(boolean virtual);
	
}
