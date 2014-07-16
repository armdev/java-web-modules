package com.rollupmedia.control.jobs;

import com.rollupmedia.control.util.XMLParser;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

public class JobsManager {

    Logger logger = Logger.getLogger(getClass());
    Scheduler sched;

    private JobsManager() {
    }

    public void initializeJobScheduler() {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        try {
            sched = schedFact.getScheduler();
            scheduleJobs();
            sched.start();
        } catch (SchedulerException e) {
            throw new RuntimeException("Error starting scheduler", e);
        } catch (ParseException ex) {
            throw new RuntimeException("Error parsing cron expression", ex);
        }
    }

    public String[] getJobGroups() {
        try {
            return sched.getJobGroupNames();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, JobExecutionContext> getWorkingJobs() {
        Map<String, JobExecutionContext> ret = new HashMap<String, JobExecutionContext>();

        try {
            List<JobExecutionContext> jobs = sched.getCurrentlyExecutingJobs();
            for (JobExecutionContext job : jobs) {
                ret.put(job.getJobDetail().getName(), job);
            }

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return ret;

    }

    public List<JobDetail> listJobs(String groupName) {
        List<JobDetail> ret = new ArrayList<JobDetail>();

        try {
            String[] jobNames = sched.getJobNames(groupName);
            for (int i = 0; i < jobNames.length; i++) {
                ret.add(sched.getJobDetail(jobNames[i], groupName));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    public void startGroup(String groupName) {

        for (JobDetail job : listJobs(groupName)) {
            startJob(job.getName(), groupName);
        }
    }

    public void startJob(String jobName, String groupName) {
        if (getWorkingJobs().containsKey(jobName)) {
            logger.info("Job " + jobName + " is currently working.");
            return;
        }

        Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 1000L);
        trigger.setJobGroup(groupName);
        trigger.setJobName(jobName);
        trigger.setName(jobName + "ImmediateTrigger");
        try {
            sched.scheduleJob(trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    static JobsManager instance;

    public static JobsManager getInstance() {
        if (instance == null) {
            instance = new JobsManager();
        }
        return instance;
    }

    private void scheduleJobs() throws ParseException, SchedulerException {
        List<JobGroup> jobGroups = XMLParser.getJobGroupList();
        for (JobGroup jobGroup : jobGroups) {
            for (Job job : jobGroup.getJobs()) {

                JobDetail jobDetail = job.getJobDetail();
                jobDetail.setGroup(jobGroup.getName());
                Trigger cronTrigger = job.getTrigger();
                cronTrigger.setStartTime(new Date(System.currentTimeMillis() + 1000));              

                if (cronTrigger != null) {//not cron trigger
                    sched.scheduleJob(jobDetail, cronTrigger);
                } else {
                    sched.scheduleJob(jobDetail, createNeverHappensTrigger());
                }
            }
        }
    }
    private int counter = 0;

    private Trigger createNeverHappensTrigger() {
        return new SimpleTrigger("NeverHappensTrigger" + counter, "NeverHappensTriggerGroup" + counter++,
                new Date(2 * System.currentTimeMillis()),
                new Date(3 * System.currentTimeMillis()), 0, 0);
    }
}
