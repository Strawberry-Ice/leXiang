package com.jfdh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Application;
@Repository
public interface ApplicationRepository extends PagingAndSortingRepository<Application, String>{
	public Application findTop1ByUser_IdOrderByApplyDateDesc(String userId);

	public Page<Application> findByUser_Id(String userId,
			Pageable pageable);
}
