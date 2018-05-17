package com.jfdh.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.ScorePool;
@Repository
public interface ScorePoolRepository extends PagingAndSortingRepository<ScorePool, String>{
	public ScorePool findByGovOrg_AdminOrgTrue();
	public List<ScorePool> findTop5ByGovOrg_AdminOrgFalseAndGovOrg_ValidTrueAndGovOrg_Parent_IdIsNullOrderByBalanceDesc();
	public List<ScorePool> findTop5ByGovOrg_AdminOrgFalseAndGovOrg_ValidTrueAndGovOrg_Parent_IdIsNotNullOrderByBalanceDesc();
	public List<ScorePool> findTop5ByUser_StateTrueOrderByBalanceDesc();
	public ScorePool findByGovOrg_Id(String id);
	public ScorePool findByOrg_Id(String id);
	public List<ScorePool> findTop5ByUser_GovOrg_IdAndUser_StateTrueOrderByBalanceDesc(
			String id);
	public List<ScorePool> findTop10ByAllGetPointsGreaterThanOrderByAllGetPointsDesc(
			long i);
	public List<ScorePool> findTop10ByAllGetPointsGreaterThanAndUserIsNotNullOrderByAllGetPointsDesc(
			long i);
}
