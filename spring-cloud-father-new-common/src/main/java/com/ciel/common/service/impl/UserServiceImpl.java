package com.ciel.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IRoleService;
import com.ciel.api.IUserRoleService;
import com.ciel.api.IUserService;
import com.ciel.common.mapper.UserMapper;
import com.ciel.entity.Role;
import com.ciel.entity.User;
import com.ciel.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;

    @Override
    public User findByUserName(String UserName) {
       return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, UserName));
    }

    @Override
    public List<Role> getRolesByUser(User user) {
        List<UserRole> list =
                userRoleService.list(new LambdaQueryWrapper<UserRole>().select(UserRole::getRoleId).eq(UserRole::getUserId, user.getId()));
        if(list.isEmpty()){
            return new ArrayList<Role>();
        }
        return roleService.listByIds(list.stream().map(t -> t.getRoleId()).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public boolean transfer(User send, User receive, String money) {
        send.setPrice(send.getPrice().subtract(new BigDecimal(money)));
        baseMapper.updateById(send);

        if(System.currentTimeMillis()%2==0){
            throw new RuntimeException("jjjjjjjjjjjjjjjjjjjjjjjjjj");
        }

        receive.setPrice(receive.getPrice().add(new BigDecimal(money)));
        baseMapper.updateById(receive);

        return true;
    }

}
