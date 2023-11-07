package com.masanz.ami;

import com.masanz.ami.utils.Exec;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.net.URL;

public class Main {

    public static final URL LOG4J_PROPERTIES = Main.class.getResource("log4j.properties");
    public static final String SYSTEM_PROPERTY = "userApp.hostName";
    private static Logger log = Logger.getLogger(Main.class);



    public static void main(String[] args) {

        System.setProperty(SYSTEM_PROPERTY, Exec.getHostName());
        PropertyConfigurator.configure(LOG4J_PROPERTIES);
        log.info("-".repeat(64));

        com.masanz.ami.terminal.Main.main(args);

    }

}
