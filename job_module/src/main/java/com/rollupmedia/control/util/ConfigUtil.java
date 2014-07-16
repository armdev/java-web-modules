package com.rollupmedia.control.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author armen arzumanyan
 */
public class ConfigUtil {

//  public static String getHome() {
//    String home = System.getenv("TP3_CONFIG") ;
//    if (home == null) {
//      home = System.getProperty("TP3_CONFIG") ;
//    }
//    if (home == null) {
//      throw new RuntimeException("TP3_CONFIG is not defined in ENV or java start up.") ;
//    }
//    return home ;
//  }
//  
//  public static File getConfDir() {
//    return new File(getHome(), "config") ;
//  }
//
//  public static Properties loadConfigProperties(String name) throws FileNotFoundException, IOException {
//	  Properties props = new Properties();
//	  props.load(new FileInputStream(new File(getConfDir(), name))) ;
//     return props ;
//  }
    public static InputStream getJobsFile() {
        InputStream inputStream = null;
        try {
            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = ConfigUtil.class.getResourceAsStream("com/rollupedia/control/init/jobs.xml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputStream;
    }

    public static InputStream getJobsFile11() {
        
        String relativeWebPath = "/WEB-INF/uploads";
////String absoluteFilePath = getServletContext().getRealPath(relativeWebPath);
//File uploadedFile = new File(absoluteFilePath, FilenameUtils.getName(item.getName()));

        //    URL file = ConfigUtil.class.getResource("jobs.xml");
//File imageFile = new File(file.toURI());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("/com/rollupedia/control/init/jobs.xml");
        //System.out.println("is " + is.toString());
//        String path = "com/rollupedia/control/init/jobs.xml";
//        File jobs = new File("jobs.xml");
//        if (!jobs.isFile()) {
//            throw new RuntimeException("Jobs XML not found : " + jobs.getAbsolutePath());
//        }
        return is;
    }
}