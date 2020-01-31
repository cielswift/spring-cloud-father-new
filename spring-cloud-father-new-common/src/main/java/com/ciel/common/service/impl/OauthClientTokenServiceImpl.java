package com.ciel.common.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ciel.api.IOauthClientTokenService;
import com.ciel.common.mapper.OauthClientTokenMapper;
import com.ciel.entity.OauthClientToken;
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
public class OauthClientTokenServiceImpl extends ServiceImpl<OauthClientTokenMapper, OauthClientToken> implements IOauthClientTokenService {

}
