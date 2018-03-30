package com.dylan.web.system.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.system.mapper.ResourceMapper;
import com.dylan.web.system.service.ResourceService;


@Service
@Transactional(readOnly = false)
public class ResourceServiceImpl extends AbstractService implements ResourceService {

    @Resource
    private ResourceMapper resourceMapper;

	public BaseMapper setMapper() {
		return resourceMapper;
	}

}
