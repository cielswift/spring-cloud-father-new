package com.ciel.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ciel.entity.Role;
import com.ciel.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
public interface IUserService extends IService<User> {

    public boolean transfer(User send,User receive,String money);

    public User findByUserName(String userName);

    public List<Role> getRolesByUser(User user);
}
