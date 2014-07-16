/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollupmedia.control.util;

import com.rollupmedia.control.jobs.Job;
import com.rollupmedia.control.jobs.Job;
import com.rollupmedia.control.jobs.JobGroup;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.quartz.CronExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author armen arzumanyan
 */
public class XMLParser {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static List<JobGroup> getJobGroupList() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(ConfigUtil.getJobsFile());
            // Document document = builder.parse(new File("/jobs.xml"));
            document.getDocumentElement().normalize();

            List<JobGroup> jobGroups = new ArrayList<JobGroup>();
            NodeList listOfJobGroups = document.getElementsByTagName("group");

            for (int i = 0; i < listOfJobGroups.getLength(); i++) {
                JobGroup jobGroup = new JobGroup();
                Node jobGroupNode = listOfJobGroups.item(i);
                String name = jobGroupNode.getAttributes().getNamedItem("name").getNodeValue();
                jobGroup.setName(name);
                jobGroup.setJobs(parseJobs(jobGroupNode.getChildNodes()));
                jobGroups.add(jobGroup);
            }
            //NodeList listOfJobs = document.getElementsByTagName("job");    

            return jobGroups;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static List<Job> parseJobs(NodeList listOfJobs) throws ParseException, ClassNotFoundException {
        List<Job> jobs = new Vector<Job>();
        for (int i = 0; i < listOfJobs.getLength(); i++) {
            Job job = new Job();
            Node jobNode = listOfJobs.item(i);

            if (jobNode.getNodeType() == Node.ELEMENT_NODE) {
                Element jobEl = (Element) jobNode;

                NodeList classList = jobEl.getElementsByTagName("class");
                NodeList nameList = jobEl.getElementsByTagName("name");
                NodeList descList = jobEl.getElementsByTagName("description");

                NodeList cronList = jobEl.getElementsByTagName("cronPattern");
                NodeList startDateList = jobEl.getElementsByTagName("startDate");
                NodeList endDateList = jobEl.getElementsByTagName("endDate");

                NodeList paramList = jobEl.getElementsByTagName("param");

                if (nameList.item(0) != null) {
                    Node nameEl = nameList.item(0).getChildNodes().item(0);
                    job.setName(nameEl.getNodeValue());
                }

                if (classList.item(0) != null) {
                    Node classNameEl = classList.item(0).getChildNodes().item(0);
                    job.setHandlerClass(Class.forName(classNameEl.getNodeValue()));
                }

                if (descList.item(0) != null) {
                    Node descriptionEl = descList.item(0).getChildNodes().item(0);
                    job.setDescription(descriptionEl.getNodeValue());
                }

                if (cronList.item(0) != null) {
                    Node patterEl = cronList.item(0).getChildNodes().item(0);
                    job.setCronPattern(new CronExpression(patterEl.getNodeValue()));
                }

                if (startDateList.item(0) != null) {
                    Node startDateEl = startDateList.item(0).getChildNodes().item(0);
                    job.setStartDate(dateFormat.parse(startDateEl.getNodeValue()));
                }

                if (endDateList.item(0) != null) {
                    Node endDateEl = endDateList.item(0).getChildNodes().item(0);
                    job.setEndDate(dateFormat.parse(endDateEl.getNodeValue()));
                }

                if (paramList != null) {
                    for (int j = 0; j < paramList.getLength(); j++) {
                        Node paramEl = paramList.item(j);
                        NamedNodeMap attributes = paramEl.getAttributes();
                        Node name = attributes.getNamedItem("name");
                        if (name != null && name.getNodeValue() != null && name.getNodeValue().trim().length() > 0) {
                            job.getParamMap().put(name.getNodeValue().trim(), paramEl.getChildNodes().item(0).getNodeValue());
                        }
                    }
                }

                jobs.add(job);

            }
        }
        return jobs;
    }

    public static void main(String[] args) {
        try {
            /*
             Job j1 = new Job();
             j1.setName("sdfsdf");
             j1.setHandlerClass(XMLParser.class);
             j1.setStartDate(new Date(System.currentTimeMillis()));
      
             Job j2 = new Job();
             j2.setName("sdfsdf");
             j2.setHandlerClass(XMLParser.class);
             j2.setStartDate(new Date(System.currentTimeMillis()));
              
             List<Job> jl = new ArrayList<Job>();
             jl.add(j2);
             jl.add(j1);
      
      
             XMLEncoder enc = new XMLEncoder(new FileOutputStream("D:\\jobss.xml"));
             enc.writeObject(j1);
             enc.flush();
      
             XMLDecoder dec = new XMLDecoder(new FileInputStream("D:\\jobss.xml"));
             List<Job> jDESER = (List<Job>) dec.readObject();
      
             dec.setOwner("");
      
             System.err.println(jl.equals(jDESER));
   
             */

            List<JobGroup> d = XMLParser.getJobGroupList();
            for (JobGroup g : d) {
                System.out.println(g.getName());
                System.out.println("Jobs" + g.getJobs().size());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //List<Job> d = XMLParser.getJobList();
        //System.out.println(d.size());
    }
}
