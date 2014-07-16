/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rollupmedia.jobs.test;

import com.rollupmedia.control.util.RMIHelper;  
import java.util.Calendar;
import java.util.Date;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.rollupedia.core.interfaces.JobManager;

/**
 *
 * @author armen arzumanyan
 */
public class TestJobTask implements Job {
  
  // Temporary.Should be moved to separate JobStatus class.
  private static final int JOB_STATUS_OK = 1;
  private static final int JOB_STATUS_FAILED = 0;
  
  
  Logger logger = Logger.getLogger(getClass());
  
  public void execute(JobExecutionContext context) throws JobExecutionException {
    RMIHelper rmiHelper = new RMIHelper(); 
    String jobName = context.getJobDetail().getName();
    Date lastRunDate = null;
   // JobManager jobManager = null;
    
   System.out.println("Started a job ");
    //catch (Exception ex) {
      //if(lastRunDate != null) {
        //jobManager.saveLastRun(jobName, lastRunDate, JOB_STATUS_FAILED, ex.getMessage());
      //}
      //logger.error("Failed to finish CrossCheckDBCreationJob", ex) ;
  //    context.setResult("Failed to finish CrossCheckDBCreationJob" + ex.getMessage()) ;
    //}
  }
  
}
