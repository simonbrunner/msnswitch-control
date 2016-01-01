package com.simonbrunner.msnswitchctrl.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.simonbrunner.msnswitchctrl.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationReader {

    public static final String SYSPROP_APPCONFIG_LOCATION = "msnswitch-control.configuration.location";

    private static final Logger log = LoggerFactory.getLogger(ConfigurationReader.class);

    private static class InstanceHolder {
        public static ConfigurationReader instance = new ConfigurationReader();
    }

    private Properties properties;
    private ApplicationConfiguration applicationConfiguration;

    private ConfigurationReader() {
        super();

        init();
        parseProperties();
    }

    public static ConfigurationReader getInstance() {
        return InstanceHolder.instance;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    private void init() {
        try {
            String configFileLocation = System.getProperty(SYSPROP_APPCONFIG_LOCATION);
            InputStream inputStream = new FileInputStream(configFileLocation);
            properties = new Properties();
            properties.load(inputStream);

            log.info(StringUtil.padLeft("=", 80));
            for (Object key : properties.keySet()) {
                String value = properties.getProperty((String) key);
                log.info(StringUtil.WHITESPACE + key.toString() + ":" + StringUtil.WHITESPACE + value);
            }
            log.info(StringUtil.padLeft("=", 80));

        } catch (IOException e) {
            log.error("Failure while loading application properties", e);
        }
    }

    private void parseProperties() {
        applicationConfiguration = new ApplicationConfiguration();
        applicationConfiguration.setUser(properties.getProperty(ApplicationConfiguration.PROPERTY_USER));
        applicationConfiguration.setPassword(properties.getProperty(ApplicationConfiguration.PROPERTY_PASSWD));
    }
}
