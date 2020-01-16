package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IProductService;
import com.ciel.common.mapper.ProductMapper;
import com.ciel.entity.Product;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品信息 服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
