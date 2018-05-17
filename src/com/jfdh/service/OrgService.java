package com.jfdh.service;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.model.Org;

public interface OrgService {

	DataResponse search(DataRequest request);

	Org update(Org org);

	Org save(Org org);

	Org findById(String id);

	void delete(String id);

}
