package com.simonbrunner.msnswitchctrl.network;

import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwitchControlTest {

    @Test
    public void readStatusBothPlugsOff() {
        String response = "<?xml version=\"1.0\"?><request><site_ip>0,0,0,0,0</site_ip><connect_status>-1,-1,-1,-1,-1</connect_status><outlet_status>0,0</outlet_status><site_lost>0,0,0,0,0</site_lost><UIS_fun>1</UIS_fun><g_target1>3</g_target1><g_target2>0</g_target2></request>";

        readStatus(response, Boolean.FALSE, Boolean.FALSE);
    }

    @Test
    public void readStatusPlug1On() {
        String response = "<?xml version=\"1.0\"?><request><site_ip>0,0,0,0,0</site_ip><connect_status>-1,-1,-1,-1,-1</connect_status><outlet_status>1,0</outlet_status><site_lost>0,0,0,0,0</site_lost><UIS_fun>1</UIS_fun><g_target1>3</g_target1><g_target2>0</g_target2></request>";

        readStatus(response, Boolean.TRUE, Boolean.FALSE);
    }

    @Test
    public void readStatusBothPlugsOn() {
        String response = "<?xml version=\"1.0\"?><request><site_ip>0,0,0,0,0</site_ip><connect_status>-1,-1,-1,-1,-1</connect_status><outlet_status>1,1</outlet_status><site_lost>0,0,0,0,0</site_lost><UIS_fun>1</UIS_fun><g_target1>3</g_target1><g_target2>0</g_target2></request>";

        readStatus(response, Boolean.TRUE, Boolean.TRUE);
    }

    private void readStatus(String response, Boolean expectedStatusPlug1, Boolean expectedStatusPlug2) {
        SwitchConfiguration switchConfiguration = new SwitchConfiguration();
        switchConfiguration.setIpAdress("10.0.0.1");
        switchConfiguration.setUser("admin");
        NetworkRequestInvoker networkRequestInvoker = mock(NetworkRequestInvoker.class);
        when(networkRequestInvoker.invokeRequest(switchConfiguration, NetworkRequestInvoker.RequestType.READ_STATUS, null)).thenReturn(response);

        SwitchControl objectUnderTest = new SwitchControl();
        objectUnderTest.networkRequestInvoker = networkRequestInvoker;

        SwitchStatus switchStatus = objectUnderTest.readStatus(switchConfiguration);

        assertThat(switchStatus, notNullValue());
        assertThat(switchStatus.getPlug1(), is(expectedStatusPlug1));
        assertThat(switchStatus.getPlug2(), is(expectedStatusPlug2));
    }
}
