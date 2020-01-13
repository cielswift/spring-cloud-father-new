package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IPermissionService;
import com.ciel.api.IRolePermissonService;
import com.ciel.api.IRoleService;
import com.ciel.common.mapper.RoleMapper;
import com.ciel.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IRolePermissonService rolePermissonService;

    @Autowired
    private IPermissionService permissionService;


    @Override
    public List<Permission> getPermissionsByRole(Role role) {

        List<RolePermisson> list =
                rolePermissonService.list(new LambdaQueryWrapper<RolePermisson>().select(RolePermisson::getPermissionId).eq(RolePermisson::getRoleId, role.getId()));
        if(list.isEmpty()){
            return new ArrayList<Permission>();
        }

        return permissionService.listByIds(list.stream().map(t -> t.getPermissionId()).collect(Collectors.toList()));

    }

}
