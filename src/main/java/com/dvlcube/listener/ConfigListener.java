package com.dvlcube.listener;

import com.dvlcube.controller.Server;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Wonka
 */
public class ConfigListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent event) {
        Properties properties = new Properties();
        ServletContext context = event.getServletContext();
        Enumeration keys = context.getInitParameterNames();
        while(keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            String value = context.getInitParameter(key);
            properties.setProperty(key, value);
        }
        Server.setConfiguration(properties);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
