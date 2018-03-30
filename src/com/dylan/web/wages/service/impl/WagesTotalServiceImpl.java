
package com.dylan.web.wages.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.core.util.BASE64Util;
import com.dylan.core.util.StringUtil;
import com.dylan.web.wages.mapper.WagesTotalMapper;
import com.dylan.web.wages.service.WagesTotalService;

@Service
@Transactional(readOnly = false)
public class WagesTotalServiceImpl extends AbstractService implements WagesTotalService{

	@Autowired
	private WagesTotalMapper totalMapper;
	
	@Override
	public BaseMapper setMapper() {
		return totalMapper;
	}

	@Override
	public List<E> selectMainTotal(FormMap formMap) {
		if(StringUtil.isEmpty(formMap.getStr("month"))){
			formMap.put("month", null);
		}
		if(StringUtil.isEmpty(formMap.getStr("year"))){
			formMap.put("year", null);
		}
		List<E> list = totalMapper.selectMainTotal(formMap);
		try {
			for (int i = 0; i < list.size(); i++) {
				E e = list.get(i);
				if (!StringUtil.isEmpty(e)) {
					e.set("yfTotal", BASE64Util.decrypt(e.getStr("yfTotal"), e.getStr("salt")));
					e.set("sfTotal", BASE64Util.decrypt(e.getStr("sfTotal"), e.getStr("salt")));
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public List<E> selectReTotal(FormMap formMap) {
		if(StringUtil.isEmpty(formMap.getStr("month"))){
			formMap.put("month", null);
		}
		if(StringUtil.isEmpty(formMap.getStr("year"))){
			formMap.put("year", null);
		}
		List<E> list = totalMapper.selectReTotal(formMap);
		try {
			for (int i = 0; i < list.size(); i++) {
				E e = list.get(i);
				if (!StringUtil.isEmpty(e)) {
					e.set("sfTotal", BASE64Util.decrypt(e.getStr("sfTotal"), e.getStr("salt")));
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return list;
	}
}

	