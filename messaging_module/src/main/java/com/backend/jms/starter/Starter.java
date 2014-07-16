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
public class Starter {    
    
    @PostConstruct
    public void init(){
        System.out.println("I started message listener only once");
        ApplicationContext serverContext = 
                new ClassPathXmlApplicationContext
                ("ApplicationContextJmsServer.xml");        
    }
}
