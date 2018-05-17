package com.jfdh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.UserPasswordValid;
import com.jfdh.httpmodel.UserProfile;
import com.jfdh.model.GovOrg;
import com.jfdh.model.Org;
import com.jfdh.model.User;

public interface UserService {
	public Page<User> findAllUsers();

	public Page<User> getUsers(int pageNumber, int pageSize, String sortType);

	public DataResponse search(DataRequest request);

	public User save(User user);

	public User findById(String id);

	public User update(User user);
	
	public List<GovOrg> findAllGovOrgs();
	
	public List<GovOrg> findAllGovOrgsAndChildren();

	public List<GovOrg> getGovOrgsByParentId(String parentId);

	public void delete(String ids);

	public boolean changePassword(UserPasswordValid userPasswordValid);

	public void updateProfile(UserProfile userProfile);

	public List<Org> findAllOrgs();

	public DataResponse search(DataRequest request, HttpSearch httpSearch);
	
	public List<User> findUserByVirtual(boolean virtual);
	
	public void saveUsers(String names);
}
