package com.simonbrunner.msnswitchctrl.network;

import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchControl {

    private static final Logger log = LoggerFactory.getLogger(SwitchControl.class);

    NetworkRequestInvoker networkRequestInvoker;

    public SwitchControl() {
        super();

        networkRequestInvoker = new NetworkRequestInvoker();
    }

    public SwitchStatus readStatus(SwitchConfiguration switchConfiguration) {
        final String prefix = "<outlet_status>";

        log.info("Reading status for Switch {}", switchConfiguration.getName());
        SwitchStatus switchStatus = new SwitchStatus();

        String response = networkRequestInvoker.invokeRequest(switchConfiguration, NetworkRequestInvoker.RequestType.READ_STATUS, null);

        if (response != null && response.length() > 0) {
            int prefixPosition = response.indexOf(prefix) + prefix.length();
            String responseWithoutPrefix = response.substring(prefixPosition);
            log.info("Substring with status response: {}", responseWithoutPrefix);

            // now the next 3 characters are in format a,b (where a and b represent the plug status)
            char plug1 = responseWithoutPrefix.charAt(0);
            char plug2 = responseWithoutPrefix.charAt(2);
            log.info("Status Plug 1: {}", plug1);
            log.info("Status Plug 2: {}", plug2);

            if (plug1 == '1') {
                switchStatus.setPlug1(Boolean.TRUE);
            } else {
                switchStatus.setPlug1(Boolean.FALSE);
            }

            if (plug2 == '1') {
                switchStatus.setPlug2(Boolean.TRUE);
            } else {
                switchStatus.setPlug2(Boolean.FALSE);
            }

            log.info("Parsed Status Plug 1: {}", switchStatus.getPlug1());
            log.info("Parsed Status Plug 2: {}", switchStatus.getPlug2());
        }

        return switchStatus;
    }

    public SwitchStatus changeStatus(SwitchConfiguration switchConfiguration, SwitchStatus currentStatus, PlugEnum plug) {
        log.info("Changing status for Switch {} and Plug {}", switchConfiguration.getName(), plug.name());

        Boolean plugCurrentlyEnabled = plug == PlugEnum.PLUG_1 ? currentStatus.getPlug1() : currentStatus.getPlug2();
        String switchControl = plugCurrentlyEnabled ? "0" : "1";

        String urlPostfix = "-d \\\"outlet=" + plug.getPlugNumber() + "&command=" + switchControl + " && echo\\\"";

        networkRequestInvoker.invokeRequest(switchConfiguration, NetworkRequestInvoker.RequestType.SWITCH_PLUG, urlPostfix);

        return readStatus(switchConfiguration);
    }

}
