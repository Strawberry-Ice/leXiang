package com.jfdh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.ScoreBalanceRecord;
@Repository
public interface ScoreBalanceRecordRepository extends PagingAndSortingRepository<ScoreBalanceRecord, String>{

	public Page<ScoreBalanceRecord> findByFrom_UserIdOrTo_UserId(String id1, String id2, Pageable pageable);

}
