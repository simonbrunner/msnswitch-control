package com.simonbrunner.msnswitchctrl.config;

import java.util.List;

public class ApplicationConfiguration {

    public static final String PROPERTY_USER = "application.user";
    public static final String PROPERTY_PASSWD = "application.passwd";
    public static final String PROPERTY_SWITCHES = "switches";

    private String user;
    private String password;

    private List<SwitchConfiguration> switchConfigurations;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SwitchConfiguration> getSwitchConfigurations() {
        return switchConfigurations;
    }

    public void setSwitchConfigurations(List<SwitchConfiguration> switchConfigurations) {
        this.switchConfigurations = switchConfigurations;
    }
}
