package com.jfdh.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.ScoreShopItem;
@Repository
public interface ScoreShopItemRepository extends PagingAndSortingRepository<ScoreShopItem, String>{

	Page<ScoreShopItem> findByRestNumGreaterThanAndExpiryGreaterThan(int restNum,
			Date date, Pageable page);

	Page<ScoreShopItem> findByRestNumGreaterThanAndExpiryGreaterThanAndStatusTrue(
			int i, Date date, Pageable page);

	Page<ScoreShopItem> findAll(Specification<ScoreShopItem> spe,
			Pageable pageable);

	List<ScoreShopItem> findByExpiryGreaterThanAndStatusTrue(Date date);

	List<ScoreShopItem> findByExpiryLessThanAndStatusTrue(Date date);

}
