package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IUserRoleService;
import com.ciel.common.mapper.UserRoleMapper;
import com.ciel.entity.UserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
