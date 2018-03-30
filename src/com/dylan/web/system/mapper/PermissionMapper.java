package com.dylan.web.system.mapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.mapper.BaseMapper;


public interface PermissionMapper extends BaseMapper{
    List<E> selectPermissionsByRoles(List<E> list);
}