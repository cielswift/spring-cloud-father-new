package com.ciel.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.entity.Permission;
import com.ciel.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
public interface IRoleService extends IService<Role> {

    public List<Permission> getPermissionsByRole(Role role);

}
