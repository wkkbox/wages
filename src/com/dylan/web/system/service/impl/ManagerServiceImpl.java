package com.dylan.web.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.core.util.StringUtil;
import com.dylan.web.system.mapper.ManagerMapper;
import com.dylan.web.system.service.ManagerService;

@Service
@Transactional(readOnly = false)
public class ManagerServiceImpl extends AbstractService implements ManagerService {

	@Resource
	private ManagerMapper managerMapper;

	public BaseMapper setMapper() {
		return managerMapper;
	}

	public int insertManagerAndRole(FormMap param) {
		return managerMapper.insertManagerAndRole(param);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 事务可写，记住方法中不能捕获异常，捕获异常不会回滚事务，trycatch也不能用
	public void createTrans(FormMap param) {
		managerMapper.insert(param);
		E e = managerMapper.selectOne(param);
		param.set("managerId", e.getStr("managerId"));
		String[] roleSigns = (String[]) param.get("roleSigns");
		for (String roleId : roleSigns) {
			param.set("roleId", roleId);
			managerMapper.insertManagerAndRole(param);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 事务可写，记住方法中不能捕获异常，捕获异常不会回滚事务，trycatch也不能用
	public void updateTrans(FormMap param) {
		managerMapper.removeManagerAndRole(param);
		managerMapper.edit(param);
		String[] roleSigns = (String[]) param.get("roleSigns");
		for (String roleId : roleSigns) {
			param.set("roleId", roleId);
			managerMapper.insertManagerAndRole(param);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // 事务可写，记住方法中不能捕获异常，捕获异常不会回滚事务，trycatch也不能用
	public void deleteTrans(FormMap param) {
		managerMapper.remove(param);
		managerMapper.removeManagerAndRole(param);
	}

	public E selectShopByManagerId(FormMap param) {
		return managerMapper.selectShopByManagerId(param);
	}

	@Override
	public List<E> selectManagerNames() {
		return managerMapper.selectManagerNames();
	}

	@Override
	public boolean selectManager(FormMap param) {
		E e = managerMapper.selectManager(param);
		if (StringUtil.isEmpty(e)) {// 表示不存在 需要新增
			return true;
		}
		return false;
	}

	@Override
	public List<E> selectAccountName(FormMap param) {
		return managerMapper.selectAccountName(param);
	}

}
