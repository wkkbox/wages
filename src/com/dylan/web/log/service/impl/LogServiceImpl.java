package com.dylan.web.log.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.log.mapper.LogMapper;
import com.dylan.web.log.service.LogService;




/**
 * 
* @ClassName: LogServiceImpl 
* @Description: 日志业务接口实现类 
* @author lcm
* @date 2017年2月18日 下午11:10:35 
*
 */
@Service
@Transactional(readOnly=false)//需要事务操作必须加入此注解
public class LogServiceImpl extends AbstractService implements LogService {

    @Resource
    private LogMapper logMapper;

	@Override
	public BaseMapper setMapper() {
		// TODO Auto-generated method stub
		return logMapper;
	}
}
