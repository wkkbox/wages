package com.dylan.web.wages.service.impl;

import java.io.IOException;
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
import com.dylan.web.wages.mapper.WagesHeaderMapper;
import com.dylan.web.wages.service.WagesHeaderService;

@Service
@Transactional(readOnly = false)
public class WagesHeaderServiceImpl extends AbstractService implements WagesHeaderService {
	@Autowired
	private WagesHeaderMapper wagesHeaderMapper;

	@Override
	public BaseMapper setMapper() {
		return wagesHeaderMapper;
	}

	@Override
	public List<E> selectTotal(FormMap formMap) {
		List<E> valueTotal= wagesHeaderMapper.selectTotal(formMap);
		try {
			if(!StringUtil.isEmpty(valueTotal)){
				for (int i = 0; i < valueTotal.size(); i++) {
					E e = valueTotal.get(i);
					e.set("headerValue", BASE64Util.decrypt(e.getStr("headerValue"), e.getStr("salt")));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wagesHeaderMapper.selectTotal(formMap);
	}

}
