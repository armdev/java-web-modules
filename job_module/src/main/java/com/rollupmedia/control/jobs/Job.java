/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rollupmedia.control.jobs;

import java.util.Date;
import java.util.Hashtable;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

/**
 *
 * @author Armen
 */
public class Job {
  
  private Class handlerClass;
  private String name;
  private String description;
  private CronExpression cronPattern;
  private Date startDate;
  private Date endDate;
  private Hashtable<String, String> paramMap = new Hashtable<String, String>();

  public Class getHandlerClass() {
    return handlerClass;
  }
  
  public Trigger getTrigger() {
      if(cronPattern == null) {
            return null;
      }

    SimpleTrigger trigger = new SimpleTrigger();
     //trigger.setCronExpression(cronPattern);
    trigger.setName(name + "Trigger");
    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);                
    trigger.setRepeatInterval(30000);       

      if(startDate != null) {
            trigger.setStartTime(startDate);
      } 
      if(endDate != null) {
            trigger.setEndTime(endDate);
      }

      return trigger;    
  }
  
  public JobDetail getJobDetail() {
        JobDetail detail = new JobDetail();

        detail.setName(name);
        detail.setDescription(description);
        detail.setJobClass(handlerClass);
        detail.getJobDataMap().putAll(paramMap);
    
        return detail;
  }

  public void setHandlerClass(Class handlerClass) {
    this.handlerClass = handlerClass;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CronExpression getCronPattern() {
    return cronPattern;
  }

  public void setCronPattern(CronExpression cronPattern) {
    this.cronPattern = cronPattern;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Hashtable getParamMap() {
    return paramMap;
  }
  
}
