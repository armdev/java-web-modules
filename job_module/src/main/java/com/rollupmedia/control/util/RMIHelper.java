package com.rollupmedia.control.util;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.rollupedia.core.interfaces.JobManager;


public class RMIHelper {

    InitialContext context;

    public RMIHelper() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        props.setProperty("java.naming.provider.url", "jnp://localhost:1099");

        try {
            context = new InitialContext(props);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }  
  

    public JobManager lookupJobManager() throws NamingException {
        return (JobManager) context.lookup("taxsystem/TpManagerFacade/remote");
    }

  
}
