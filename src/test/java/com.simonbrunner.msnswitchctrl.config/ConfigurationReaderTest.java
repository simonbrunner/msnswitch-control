package com.simonbrunner.msnswitchctrl.config;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConfigurationReaderTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationReaderTest.class);

    @Before
    public void init() {
        URL url = ConfigurationReaderTest.class.getClassLoader().getResource("test-config.properties");
        log.info("Using test configuration file {}", url.getFile());
        System.setProperty(ConfigurationReader.SYSPROP_APPCONFIG_LOCATION, url.getFile());
    }

    @Test
    public void test1() {
        ApplicationConfiguration appConfig = ConfigurationReader.getInstance().getApplicationConfiguration();

        assertThat(appConfig, notNullValue());
        assertThat(appConfig.getUser(), is("admin"));
        assertThat(appConfig.getPassword(), is("123"));
    }

}
