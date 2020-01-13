package com.ciel.springcloudfathernewsso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient  //服务注册eureka
@ComponentScan(basePackages = "com.ciel")  //扫描整个ciel下的包
@EnableAspectJAutoProxy  //开启基于注解的aop
@MapperScan("com.ciel.common.mapper")
@EnableTransactionManagement //开启事务

public class SpringCloudFatherNewSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFatherNewSsoApplication.class, args);
    }



    //我们配置了 Http 的请求端口为 8081，所有来自 8081 的请求，将被自动重定向到 8080 这个 https 的端口上
//    @Bean
//    TomcatServletWebServerFactory tomcatServletWebServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//
//        Connector connector = new
//                Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8081);
//        connector.setSecure(false);
//        connector.setRedirectPort(8080);
//
//        factory.addAdditionalTomcatConnectors(connector);
//        return factory;
//    }


}
