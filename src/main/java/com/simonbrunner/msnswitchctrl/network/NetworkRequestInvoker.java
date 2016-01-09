package com.simonbrunner.msnswitchctrl.network;

import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class NetworkRequestInvoker {

    private static final Logger log = LoggerFactory.getLogger(NetworkRequestInvoker.class);

    public enum RequestType {
        READ_STATUS("/outlet_status.xml"),
        SWITCH_PLUG("/control.cgi");

        private String operation;

        RequestType(String aOperation) {
            operation = aOperation;
        }

        public String getOperation() {
            return operation;
        }
    }

    // FIXME: doesn't work (ends in a 401 response code returned by the MSNswitch)
    public String invokeRequestPureJava(SwitchConfiguration switchConfiguration, RequestType requestType) {
        log.info("Reading status for Switch {}", switchConfiguration.getName());

        StringBuilder response = new StringBuilder();
        try {
            String credentials = switchConfiguration.getUser() + "@";
            if (switchConfiguration.getPassword() != null && switchConfiguration.getPassword().length() > 0) {
                credentials = switchConfiguration.getUser() + ":" + switchConfiguration.getPassword() + "@";
            }
            URL url = new URL ("http://" + switchConfiguration.getIpAdress() + requestType.getOperation());
            String credentialsEncoded = Base64.getEncoder().encodeToString(credentials.getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty ("Authorization", "Basic " + credentialsEncoded);
            InputStream content = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } catch(Exception e) {
            throw new RuntimeException("Failure while sending request to switch", e);
        }

        log.info("Response read from Switch {}: {}", switchConfiguration.getName(), response.toString());
        return response.toString();
    }

    public String invokeRequest(SwitchConfiguration switchConfiguration, RequestType requestType, String urlPostfix) {
        log.info("Reading status for Switch {}", switchConfiguration.getName());

        String response;
        try {
            String credentials = switchConfiguration.getUser() + ":";
            if (switchConfiguration.getPassword() != null && switchConfiguration.getPassword().length() > 0) {
                credentials = switchConfiguration.getUser() + ":" + switchConfiguration.getPassword();
            }
            String url = "http://" + switchConfiguration.getIpAdress() + requestType.getOperation();
            String command = "curl -u " + credentials + " " + url;
            if (urlPostfix != null) {
                command = command + " " + urlPostfix;
            }
            log.info("Invoked command {}", command);

            NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
            ScriptEngine engine = factory.getScriptEngine(new String[] { "-scripting" });
            response = (String) engine.eval("$EXEC(\"" + command + "\");");

        } catch(Exception e) {
            throw new RuntimeException("Failure while sending request to switch", e);
        }

        log.info("Response read from Switch {}: {}", switchConfiguration.getName(), response.toString());
        return response;
    }

}
