package com.jfdh.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.model.Org;
import com.jfdh.repository.OrgRepository;
import com.jfdh.service.OrgService;
import com.jfdh.util.JpaTransactional;

@Service("orgService")
public class OrgServiceImpl implements OrgService {
	@Autowired
	private OrgRepository orgRepository;

	@Override
	public DataResponse search(DataRequest request) {
		DataResponse response = new DataResponse();
		List<Org> list;
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.ASC, "id");
		}
		Page<Org> orgs = orgRepository.findAll(new PageRequest(request
				.getPage() - 1, request.getRows(), sort));
		list = orgs.getContent();
		response.setRecords((int) orgs.getTotalElements());
		response.setTotal(orgs.getTotalPages());
		response.setPage(request.getPage());
		response.setRows(list);

		return response;
	}

	@Override
	@Transactional()
	public Org save(Org org) {
		org.setId(UUID.randomUUID().toString());
		return orgRepository.save(org);
	}

	@Override
	public Org findById(String id) {
		return orgRepository.findOne(id);
	}

	@Override
	@JpaTransactional
	public Org update(Org org) {
		orgRepository.save(org);
		return org;
	}

	

	@Override
	@JpaTransactional
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			orgRepository.delete(id);
		}
	}


}
