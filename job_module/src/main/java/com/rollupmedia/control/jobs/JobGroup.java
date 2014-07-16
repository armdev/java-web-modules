/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rollupmedia.control.jobs;

import java.util.List;

/**
 *
 * @author Armen Arzumanyan
 */
public class JobGroup {
  
  private String name;
  private List<Job> jobs;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Job> getJobs() {
    return jobs;
  }

  public void setJobs(List<Job> jobs) {
    this.jobs = jobs;
  }
}
