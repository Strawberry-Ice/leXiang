package com.jfdh.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.GovOrg;
@Repository
public interface GovOrgRepository extends CrudRepository<GovOrg, String>{
	public List<GovOrg> findByValidTrueAndParentIsNullAndAdminOrgFalse();
	
	public List<GovOrg> findByValidTrueAndParent_Id(String id);

	public List<GovOrg> findByIdIn(List<String> ids);

	public List<GovOrg> findByValidTrueAndParentIsNullAndAdminOrgFalseOrderByLevelAsc();

	public GovOrg findByAdminOrgTrue();
}
