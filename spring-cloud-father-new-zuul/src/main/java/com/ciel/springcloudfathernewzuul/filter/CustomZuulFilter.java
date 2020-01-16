package com.ciel.springcloudfathernewzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul 过滤器
 */
@Component
public class CustomZuulFilter extends ZuulFilter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 过滤逻辑
     * `请求前拦截, 如果无效直接断路;
     * `请求结束后对结果进行加工处理;
     */
    @Override
    public Object run() throws ZuulException {


        RequestContext rc=RequestContext.getCurrentContext();
        HttpServletRequest req = rc.getRequest();
        String token = req.getParameter("token");
        if(StringUtils.isEmpty(token)) {


            redisTemplate.opsForValue().set("xiapeixin","ciel");
            //throw new RuntimeException("未登录异常");

//            rc.setSendZuulResponse(false);//设置不向下传递，拦截不通过
//            rc.setResponseStatusCode(401);
//            rc.setResponseBody("未登录！！请先登录");
//            rc.getResponse().setContentType("text/html;charset=utf-8");



        }else {
            log.info("已登录，通过过滤器！！！");

        }

        log.info("请求url为:",req.getMethod(),req.getRequestURL().toString() );
        return null;
    }

    /**
     * 是否开启过滤,是否执行过滤器
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 拦截优先级   数字越小越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 拦截器类型
     * PRE过滤器：是在请求路由到具体服务之前执行的，可以做安全验证，如身份验证，参数验证。
     *ROUTING过滤器：它用于将请求 路由到具体的微服务实例。默认使用Http Client进行网络请求。 路由执行之后调用
     *POST过滤器：在ROUTING和ERROR之后被调用;在请求已被路由到微服务后执行的。可用作收集统计信息、指标，以及将响应传输到客户端。
     *ERROR过滤器：在其他过滤器发生错误时执行。
     *只能小写
     */

    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return "pre";
    }


}
