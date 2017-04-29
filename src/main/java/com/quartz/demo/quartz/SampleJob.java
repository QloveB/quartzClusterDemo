package com.quartz.demo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chensy
 * @since 17/4/27.
 */
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
        System.out.println("job start ...  " + Thread.currentThread().getId() + "   ... time = " + simpleDateFormat.format(new Date()));
    }
}
