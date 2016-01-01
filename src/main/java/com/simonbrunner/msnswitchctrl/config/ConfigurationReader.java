package com.simonbrunner.msnswitchctrl.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationReader {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationReader.class);

    private static final String APP_CONFIG = "~/msnswitch-control.properties";

    private static class InstanceHolder {
        public static ConfigurationReader instance = new ConfigurationReader();
    }

    private Properties properties;
    private ApplicationConfiguration appConfig;

    private ConfigurationReader() {
        super();

        init();
    }

    public static ConfigurationReader getInstance() {
        return InstanceHolder.instance;
    }

    public String getProperty(String key) {
        return (String) properties.get(key);
    }

    private void init() {
        try {
            InputStream inputStream = new FileInputStream(APP_CONFIG);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Failure while loading application properties", e);
        }
    }

    private void parseProperties() {
    }
}
