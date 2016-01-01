package com.simonbrunner.msnswitchctrl.config;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

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

        List<SwitchConfiguration> switchConfigurations = appConfig.getSwitchConfigurations();
        assertThat(switchConfigurations.size(), is(2));

        SwitchConfiguration config1 = switchConfigurations.get(0);
        assertThat(config1.getDescription(), is("a short description"));
        assertThat(config1.getIpAdress(), is("192.168.1.100"));
        assertThat(config1.getUser(), is("admin"));
        assertThat(config1.getPassword(), is(""));
        assertThat(config1.getPlug1Name(), is("Sun Fire V120"));
        assertThat(config1.getPlug2Name(), is("Sun Fire V245"));

        SwitchConfiguration config2 = switchConfigurations.get(1);
        assertThat(config2.getDescription(), is("Another device"));
        assertThat(config2.getIpAdress(), is("192.168.1.101"));
        assertThat(config2.getUser(), is("admin"));
        assertThat(config2.getPassword(), is("987"));
        assertThat(config2.getPlug1Name(), is("Silicon Graphics O2"));
        assertThat(config2.getPlug2Name(), is("Silicon Graphics Indy"));
    }

}
