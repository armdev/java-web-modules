/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rollupedia.core.interfaces;

import java.util.Date;

/**
 *
 * @author armen arzumanyan
 */
public interface JobManager {
  void saveLastRun(String jobName,Date runDate,Integer status,String message);
  Date getLastRunDate(String jobName);
}
