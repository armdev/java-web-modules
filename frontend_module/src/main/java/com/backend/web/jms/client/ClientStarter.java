/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.web.jms.client;

import javax.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author armen
 */
public class ClientStarter {

    public static void main(String[] args) {
        System.out.println("Now starting client send to listener ");
        ApplicationContext clientContext =
                new ClassPathXmlApplicationContext("ApplicationContextJmsClient.xml");
    }
}
