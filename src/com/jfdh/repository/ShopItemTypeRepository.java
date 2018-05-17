package com.jfdh.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.ShopItemType;
@Repository
public interface ShopItemTypeRepository extends CrudRepository<ShopItemType, String>{
	public List<ShopItemType> findByConstantTrue();
}
