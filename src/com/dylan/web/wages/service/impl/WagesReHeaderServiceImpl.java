package com.dylan.web.wages.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.wages.mapper.WagesReHeaderMapper;
import com.dylan.web.wages.service.WagesReHeaderService;

@Service
@Transactional(readOnly = false)
public class WagesReHeaderServiceImpl extends AbstractService implements WagesReHeaderService {
	@Autowired
	private WagesReHeaderMapper wagesReHeaderMapper;

	@Override
	public BaseMapper setMapper() {
		return wagesReHeaderMapper;
	}

}
