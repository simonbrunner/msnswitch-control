package com.simonbrunner.msnswitchctrl.network;

import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class SwitchControl {

    private static final Logger log = LoggerFactory.getLogger(SwitchControl.class);

    private SwitchControl() {
        super();
    }

    public static void readStatus(SwitchConfiguration switchConfiguration) {
        log.info("Reading status for Switch {}", switchConfiguration.getName());

        try {
            URL url = new URL ("http://" + switchConfiguration.getIpAdress() + "/outlet_status.xml");
            String credentials = switchConfiguration.getUser() + ":" + switchConfiguration.getPassword();
            String encoding = Base64.getEncoder().encodeToString(credentials.getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty ("Authorization", "Basic " + encoding);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch(Exception e) {
            throw new RuntimeException("Failure while reading Switch status", e);
        }
    }

}
