package com.jfdh.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Resource;
@Repository
public interface ResourceRepository extends CrudRepository<Resource, String>{

}
