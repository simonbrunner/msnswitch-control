package com.simonbrunner.msnswitchctrl.network;

public enum PlugEnum {
    PLUG_1("1"),
    PLUG_2("2");

    private String plugNumber;

    PlugEnum(String plugNumber) {
        this.plugNumber = plugNumber;
    }

    public String getPlugNumber() {
        return plugNumber;
    }
}
