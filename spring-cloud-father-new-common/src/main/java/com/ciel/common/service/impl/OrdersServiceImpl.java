package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IOrdersService;
import com.ciel.common.mapper.OrdersMapper;
import com.ciel.entity.Orders;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
