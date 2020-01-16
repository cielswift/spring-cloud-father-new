package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.ITradeService;
import com.ciel.common.mapper.TradeMapper;
import com.ciel.entity.Trade;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易 服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-16
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements ITradeService {

}
