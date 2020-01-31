package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IClientdetailsService;
import com.ciel.common.mapper.ClientdetailsMapper;
import com.ciel.entity.Clientdetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiapeixin
 * @since 2020-01-29
 */
@Service
public class ClientdetailsServiceImpl extends ServiceImpl<ClientdetailsMapper, Clientdetails> implements IClientdetailsService {

}
