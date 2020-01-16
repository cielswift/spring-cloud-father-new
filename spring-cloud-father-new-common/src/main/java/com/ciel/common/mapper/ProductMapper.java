package com.ciel.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ciel.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 产品信息 Mapper 接口
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
