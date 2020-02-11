package com.ciel.springcloudconsulslave1.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;

public class NacosConfig {


    /**
     * 发布配置
     *
     * 修改 cluster.conf
     * 192.168.56.101:13100
     * 192.168.56.102:13100
     * 192.168.56.103:13100
     *
     * nacos.inetutils.ip-address=127.0.0.1
     *
     * startup -m cluster
     * 启动时输出多个地址
     *
     *application.proportion  把spring.security.enable=false 取消认证
     *
     *
     * 服务下线 不再能参与负载均衡
     */

    public static void main(String[] args) throws NacosException, InterruptedException {
        Properties properties = new Properties();

        properties.put("serverAddr", "192.168.56.101:13100,192.168.56.102:13101,192.168.56.103:13102");
        properties.put("namespace", "a998603d-3546-4ddc-9099-2ed82a6f3a68");

        ConfigService configService = NacosFactory.createConfigService(properties);

        String content = configService.getConfig("xiapeixin", "DEFAULT_GROUP", 5000);

        System.out.println(content);

        configService.addListener("xiapeixin", "DEFAULT_GROUP", new Listener() {

            @Override
            public Executor getExecutor() {
               return (run -> run.run());
            }

            /**
             * 配置有变化时获取通知;
             * @param configInfo
             */
            @Override
            public void receiveConfigInfo(String configInfo) {


                System.out.println(configInfo);
            }
        });

        while (true){
            Thread.sleep(1000);
        }
    }

}
