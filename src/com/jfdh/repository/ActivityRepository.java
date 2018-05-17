package com.jfdh.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Activity;
@Repository
public interface ActivityRepository extends CrudRepository<Activity, String>{
	public Page<Activity> findByCreator_IdAndCategory_IdNotIn(String userId,String categoryId,
			Pageable pageable);
}
