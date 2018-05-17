package com.jfdh.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jfdh.model.Image;
@Repository
public interface ImageRepository extends CrudRepository<Image, String>{

	Image findByUrlAndSize(String url, String size);
}
