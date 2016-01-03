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
        final String postfix = "</outlet_status>";

        log.info("Reading status for Switch {}", switchConfiguration.getName());
        SwitchStatus switchStatus = new SwitchStatus();

        String response = networkRequestInvoker.invokeRequest(switchConfiguration, NetworkRequestInvoker.RequestType.READ_STATUS);

        if (response != null && response.length() > 0) {
            int prefixPosition = response.indexOf(prefix) + prefix.length();
            String responseWithoutPrefix = response.substring(prefixPosition);
            log.info("Substring with status response: {}", responseWithoutPrefix);
            // now the next 3 characters are in format a,b (where a and b represent the plug status)
            char plug1 = responseWithoutPrefix.charAt(0);
            char plug2 = responseWithoutPrefix.charAt(2);
            log.info("Status Plug 1: {}", plug1);
            log.info("Status Plug 2: {}", plug2);
            switchStatus.setPlug1(Boolean.parseBoolean(Character.toString(plug1)));
            switchStatus.setPlug2(Boolean.parseBoolean(Character.toString(plug2)));
        }

        return switchStatus;
    }

}
