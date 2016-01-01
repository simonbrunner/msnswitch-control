package com.simonbrunner.msnswitchctrl.config;

public class SwitchConfiguration {

    public static final String PROPERTY_POSTFIX_DESCRIPTION = ".description";
    public static final String PROPERTY_POSTFIX_IPADDRESS = ".ipaddress";
    public static final String PROPERTY_POSTFIX_USER = ".user";
    public static final String PROPERTY_POSTFIX_PASSWORD = ".passwd";
    public static final String PROPERTY_POSTFIX_PLUG1NAME = ".plug1name";
    public static final String PROPERTY_POSTFIX_PLUG2NAME = ".plug2name";

    private String name;
    private String description;
    private String ipAdress;
    private String user;
    private String password;
    private String plug1Name;
    private String plug2Name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

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

    public String getPlug1Name() {
        return plug1Name;
    }

    public void setPlug1Name(String plug1Name) {
        this.plug1Name = plug1Name;
    }

    public String getPlug2Name() {
        return plug2Name;
    }

    public void setPlug2Name(String plug2Name) {
        this.plug2Name = plug2Name;
    }
}
