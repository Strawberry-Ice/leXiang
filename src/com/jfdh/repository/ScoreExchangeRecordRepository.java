package com.jfdh.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Org;
import com.jfdh.model.ScoreExchangeRecord;
@Repository
public interface ScoreExchangeRecordRepository extends PagingAndSortingRepository<ScoreExchangeRecord, String>{
	public ScoreExchangeRecord findByCoupon(String key);

	public List<ScoreExchangeRecord> findByStatusTrue();

	public ScoreExchangeRecord findTop1ByExchanger_idAndStatusFalseAndExpiryGreaterThan(
			String userId, Date date);

	public Page<ScoreExchangeRecord> findByExchanger_Id(String userId,
			Pageable pageable);

	public ScoreExchangeRecord findByExchanger_Id(String userId);

	public Page<ScoreExchangeRecord> findByStatusTrue(Pageable pageable);

	public Page<ScoreExchangeRecord> findByStatusTrueAndScoreShop_Org(Org org,
			Pageable pageable);

	public ScoreExchangeRecord findByExchanger_IdAndScoreShop_Id(String userId,
			String id);

	public Page<ScoreExchangeRecord> findAll(
			Specification<ScoreExchangeRecord> spe, Pageable pageable);
}
