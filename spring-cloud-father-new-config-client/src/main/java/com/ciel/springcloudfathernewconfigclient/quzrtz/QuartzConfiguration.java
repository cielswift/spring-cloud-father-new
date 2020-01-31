package com.ciel.springcloudfathernewconfigclient.quzrtz;

import com.ciel.springcloudfathernewconfigclient.stream.Msg;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class QuartzConfiguration {

    @Autowired
    private ApplicationContext applicationContext;
    /**
     * spring的上下文对象,可以直接获取bean
     */
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        //applicationContext.getBeansWithAnnotation(Cron.class); //根据注解获取所有对象

        Map<String, QuartzJobBean> beans = applicationContext.getBeansOfType(QuartzJobBean.class);
        beans.entrySet().stream().forEach(t -> {
            if (t.getValue().getClass().isAnnotationPresent(Cron.class)) {

                Cron annotation = t.getValue().getClass().getAnnotation(Cron.class);
                if (!StringUtils.isEmpty(annotation.value())) {
                    /**
                     *  创建jobdetail
                     */
                    JobDetail build = JobBuilder.newJob(t.getValue().getClass()).withIdentity(t.getValue().getClass().getName() + "SMJOB").storeDurably().build();
                    /**
                     * cron表达式
                     */
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(annotation.value());

                    CronTrigger trigger = TriggerBuilder.newTrigger()
                            .forJob(build)
                            .withIdentity(t.getValue().getClass().getName() + "SMTRIGEE")
                            .withSchedule(cronScheduleBuilder)
                            .build();


                    ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

                    // 获取bean工厂并转换为DefaultListableBeanFactory
                    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

                    // 通过BeanDefinitionBuilder创建bean定义
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Msg.class);

                    // 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
                    beanDefinitionBuilder.addPropertyReference("userService", "userService");

                    // 注册bean
                    defaultListableBeanFactory.registerBeanDefinition("msgg", beanDefinitionBuilder.getRawBeanDefinition());

                    //删除bean.
                    //defaultListableBeanFactory.removeBeanDefinition("testService");
                }

            }
        });
    }


}