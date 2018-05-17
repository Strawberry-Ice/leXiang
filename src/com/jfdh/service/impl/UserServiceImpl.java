package com.jfdh.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpData;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.UserPasswordValid;
import com.jfdh.httpmodel.UserProfile;
import com.jfdh.model.GovOrg;
import com.jfdh.model.Org;
import com.jfdh.model.User;
import com.jfdh.repository.GovOrgRepository;
import com.jfdh.repository.OrgRepository;
import com.jfdh.repository.RoleRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.UserService;
import com.jfdh.util.DynamicSpecifications;
import com.jfdh.util.FileCopyHelper;
import com.jfdh.util.JpaTransactional;
import com.jfdh.util.SearchFilter;
import com.jfdh.util.SearchFilter.Operator;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GovOrgRepository govOrgRepository;
	@Autowired
	private OrgRepository orgRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FileCopyHelper fileCopyHelper;
	@Value("#{configProperties['logoPath']}")
	private String logoPath;
	@Override
	public Page<User> findAllUsers() {
		return null;
	}

	@Override
	public Page<User> getUsers(int pageNumber, int pageSize, String sortType) {
		return userRepository
				.findAll(new PageRequest(pageNumber - 1, pageSize));
	}

	@Override
	public DataResponse search(DataRequest request) {
		
		DataResponse response = new DataResponse();
		List<User> list;

		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		
		
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				Page<User> users = userRepository.findAll(new PageRequest(request
						.getPage() - 1, request.getRows(), sort));
				list = users.getContent();
				response.setRecords((int) users.getTotalElements());
				response.setTotal(users.getTotalPages());
				response.setPage(request.getPage());
				response.setRows(list);

				return response;
			}
		}
		
		User user=userRepository.findOne(userDetails.getId());
		if(null!=user.getGovOrg()){
			Page<User> users = userRepository.findByGovOrg_Id(user.getGovOrg().getId(),new PageRequest(request
					.getPage() - 1, request.getRows(), sort));
			list = users.getContent();
			response.setRecords((int) users.getTotalElements());
			response.setTotal(users.getTotalPages());
			response.setPage(request.getPage());
			response.setRows(list);

			return response;
		}
		
		if(null!=user.getOrg()){
			Page<User> users = userRepository.findByOrg_Id(user.getOrg().getId(),new PageRequest(request
					.getPage() - 1, request.getRows(), sort));
			list = users.getContent();
			response.setRecords((int) users.getTotalElements());
			response.setTotal(users.getTotalPages());
			response.setPage(request.getPage());
			response.setRows(list);

			return response;
		}

		return response;
	}

	@Override
	@Transactional()
	public User save(User user) {
		if(null!=userRepository.findByUserName(user.getUserName().trim())){
			return null;
		}
		String md5Password = new Md5PasswordEncoder().encodePassword(
				user.getPassword(), user.getUserName());
		user.setId(UUID.randomUUID().toString());
		user.setPassword(md5Password);
		user.setCreateDate(new Date());
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				return userRepository.save(user);
			}
		}
		User creator=userRepository.findOne(userDetails.getId());
		if(null!=creator.getGovOrg()){
			user.setGovOrg(creator.getGovOrg());
		}
		
		if(null!=creator.getOrg()){
			user.setOrg(creator.getOrg());
		}
		user.setRole(roleRepository.findByRoleKey(creator.getRole().getRoleKey().replace("admin", "user")));
		return userRepository.save(user);
	}

	@Override
	public User findById(String id) {
		return userRepository.findOne(id);
	}

	@Override
	@JpaTransactional
	public User update(User user) {
		User userInDb = userRepository.findOne(user.getId());
		BeanUtils.copyProperties(user, userInDb);
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String md5Password = new Md5PasswordEncoder().encodePassword(
					user.getPassword(), userInDb.getUserName());
			userInDb.setPassword(md5Password);
		}
		userRepository.save(userInDb);
		return userInDb;
	}

	@Override
	public List<GovOrg> findAllGovOrgs() {
		return govOrgRepository.findByValidTrueAndParentIsNullAndAdminOrgFalseOrderByLevelAsc();
	}

	@Override
	public List<Org> findAllOrgs() {
		return (List<Org>) orgRepository.findAll();
	}
	
	@Override
	public List<GovOrg> getGovOrgsByParentId(String parentId) {
		return govOrgRepository.findByValidTrueAndParent_Id(parentId);
	}

	@Override
	@JpaTransactional
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			userRepository.delete(id);
		}
	}

	@Override
	@JpaTransactional
	public boolean changePassword(UserPasswordValid userPasswordValid) {
		Authentication currentuser = SecurityContextHolder.getContext()
				.getAuthentication();

		if (currentuser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		String id = ((BackendUserDtail) principal).getId();

		User users=userRepository.findOne(id);
		
		String oldPassword = new Md5PasswordEncoder().encodePassword(
				userPasswordValid.getOldPassword(), users.getUserName());
		
		if (users.getPassword().equals(oldPassword)) {
			String newPassword = new Md5PasswordEncoder().encodePassword(
					userPasswordValid.getPassword(), users.getUserName());
			users.setPassword(newPassword);

			userRepository.save(users);
			return true;
		}else{
			return false;
		}

	}

	@Override
	@JpaTransactional
	public void updateProfile(UserProfile userProfile) {
		if(StringUtils.isNotEmpty(userProfile.getLogo())){
			String result=fileCopyHelper.copyFile(userProfile.getLogo(),logoPath);
			if(result.equals("failed")){
				userProfile.setLogo(null);
			}else{
				userProfile.setLogo(result);
			}
		}
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		BackendUserDtail backendUserDtail = (BackendUserDtail) principal;
		User users=userRepository.findOne(backendUserDtail.getId());
		BeanUtils.copyProperties(userProfile, users);
		BeanUtils.copyProperties(userProfile, backendUserDtail);
		userRepository.save(users);
		
	}

	@Override
	@JpaTransactional
	public List<GovOrg> findAllGovOrgsAndChildren() {
		List<GovOrg> govOrgs=govOrgRepository.findByValidTrueAndParentIsNullAndAdminOrgFalse();
		for(GovOrg govOrg:govOrgs){
			govOrg.getChildren().size();
		}
		return govOrgs;
	}

	@Override
	public DataResponse search(DataRequest request, HttpSearch httpSearch) {
		if(null==httpSearch||httpSearch.getRules().size()==0){
			return this.search(request);
		}
		

		List<HttpData> list2=httpSearch.getRules();
		HttpData httpData=list2.get(0);
		
		String fieldName=httpData.getField();
		String fieldValue=httpData.getData();
		
		DataResponse response = new DataResponse();
		List<User> list;

		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		

		Pageable pageable= new PageRequest(request.getPage() - 1, request.getRows(), sort);
		
		
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				Specification<User> spe=buildSpecification(fieldName, fieldValue);
				Page<User> users = userRepository.findAll(spe,pageable);
				list = users.getContent();
				response.setRecords((int) users.getTotalElements());
				response.setTotal(users.getTotalPages());
				response.setPage(request.getPage());
				response.setRows(list);

				return response;
			}
		}
		
		User user=userRepository.findOne(userDetails.getId());
		if(null!=user.getGovOrg()){
			Specification<User> spe=buildSpecification(fieldName, fieldValue,"govOrg.id",user.getGovOrg().getId());
			Page<User> users = userRepository.findAll(spe,pageable);
			list = users.getContent();
			response.setRecords((int) users.getTotalElements());
			response.setTotal(users.getTotalPages());
			response.setPage(request.getPage());
			response.setRows(list);

			return response;
		}
		
		if(null!=user.getOrg()){
			Page<User> users = userRepository.findByOrg_Id(user.getOrg().getId(),new PageRequest(request
					.getPage() - 1, request.getRows(), sort));
			list = users.getContent();
			response.setRecords((int) users.getTotalElements());
			response.setTotal(users.getTotalPages());
			response.setPage(request.getPage());
			response.setRows(list);

			return response;
		}
		
		
		return null;
	}
	
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<User> buildSpecification(String key,String value) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(StringUtils.isNotEmpty(value)){
			filters.put(key, new SearchFilter(key, Operator.LIKE, value));
		}
		Specification<User> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), User.class);
		return spec;
	}
	
	
	private Specification<User> buildSpecification(String key,String value,String key2,String value2) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(StringUtils.isNotEmpty(value)){
			filters.put(key, new SearchFilter(key, Operator.LIKE, value));
		}
		if(StringUtils.isNotEmpty(value2)){
			filters.put(key2, new SearchFilter(key2, Operator.EQ, value2));
		}
		Specification<User> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), User.class);
		return spec;
	}

	@Override
	public List<User> findUserByVirtual(boolean virtual) {
		return userRepository.findByVirtual(virtual);
	}

	@Override
	@JpaTransactional
	public void saveUsers(String names) {
		if(names.indexOf("，")>-1){
			String[] name=names.split("，");
			for(String str:name){
				User user=new User();
				user.setId(UUID.randomUUID().toString());
				user.setNickName(str);
				user.setCreateDate(new Date());
				user.setVirtual(true);
				user.setNeedReceive(true);
				user.setState(true);
				user.setAddress("风云科技");
				userRepository.save(user);
			}
		}else{
//			this.saveUser(names);
			User user=new User();
			user.setId(UUID.randomUUID().toString());
			user.setNickName(names);
			user.setCreateDate(new Date());
			user.setVirtual(true);
			user.setNeedReceive(true);
			user.setState(true);
			user.setAddress("风云科技");
			userRepository.save(user);
		}
	}
	
	
	private void saveUser(String name){
		
	}

}
