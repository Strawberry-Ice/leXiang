package com.jfdh.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Org;
@Repository
public interface OrgRepository extends PagingAndSortingRepository<Org, String>{
}
