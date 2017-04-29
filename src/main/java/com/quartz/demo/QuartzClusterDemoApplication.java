package com.quartz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@Import({QuartzConfig.class})
public class QuartzClusterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzClusterDemoApplication.class, args);
    }


}
