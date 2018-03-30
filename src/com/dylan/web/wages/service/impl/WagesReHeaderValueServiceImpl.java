package com.dylan.web.wages.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.wages.mapper.WagesHeaderValueMapper;
import com.dylan.web.wages.service.WagesReHeaderValueService;

@Service
@Transactional(readOnly = false)
public class WagesReHeaderValueServiceImpl extends AbstractService implements WagesReHeaderValueService {

	@Autowired
	private WagesHeaderValueMapper wagesHeaderValueMapper;

	@Override
	public BaseMapper setMapper() {
		return wagesHeaderValueMapper;
	}

}
