package com.quartz.demo.config;

import com.quartz.demo.quartz.SampleJob;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;


/**
 * @author chensy
 * @since 2017-04-28
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariConfig getHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean SchedulerFactoryBean(DataSource dataSource, Properties properties) throws ParseException {

        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setDataSource(dataSource);

        bean.setQuartzProperties(properties);
        bean.setSchedulerName("CRMscheduler");
        bean.setStartupDelay(10);
        bean.setApplicationContextSchedulerContextKey("applicationContextKey");
        bean.setOverwriteExistingJobs(true);
        bean.setAutoStartup(true);


        JobDetail job = newJob(SampleJob.class)
                .withDescription("任务描述")
                .withIdentity("job1", "group1")
                .storeDurably()
                .build();

        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName("trigger1");
        cronTrigger.setGroup("group1");
        // 每隔几秒执行一次
        cronTrigger.setCronExpression("*/5 * * * * ?");
        cronTrigger.setJobKey(job.getKey());

        bean.setTriggers(cronTrigger);
        bean.setJobDetails(job);

        return bean;
    }


}
