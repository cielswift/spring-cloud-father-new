package com.ciel.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    public List<User> myList();
}
