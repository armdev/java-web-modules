/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.jms.starter;

import javax.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author armen
 */
public class ClientStarter {    
    
    @PostConstruct
    public void init(){       
          System.out.println("Now starting client");
             ApplicationContext clientContext = 
                 new ClassPathXmlApplicationContext
                 ("ApplicationContextJmsClient.xml");
    }
}
