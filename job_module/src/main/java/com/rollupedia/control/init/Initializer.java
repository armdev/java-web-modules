package com.rollupedia.control.init;

import com.rollupmedia.control.jobs.JobsManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class Initializer
 * 
 */
public class Initializer implements ServletContextListener {

	Logger logger = Logger.getLogger(getClass()) ;
	
	/**
	 * Default constructor.
	 */
	public Initializer() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		logger.info("Initializing scheduler manager") ;
		JobsManager.getInstance().initializeJobScheduler() ;

		logger.info("Application started") ;
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
