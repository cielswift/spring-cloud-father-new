package com.ciel.springcloudfathernewconfigclient.quzrtz;

import com.ciel.springcloudfathernewconfigclient.bean.Xiapeixin;
import com.ciel.springcloudfathernewconfigclient.controller.IndexController;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Cron("1/5 * * * * ?")
public class MyCronJob extends QuartzJobBean {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Xiapeixin xiapeixin;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.warn(xiapeixin.toString()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
