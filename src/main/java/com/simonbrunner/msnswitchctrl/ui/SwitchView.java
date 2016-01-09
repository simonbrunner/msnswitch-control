package com.simonbrunner.msnswitchctrl.ui;

import com.simonbrunner.msnswitchctrl.config.ApplicationConfiguration;
import com.simonbrunner.msnswitchctrl.config.ConfigurationReader;
import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import com.simonbrunner.msnswitchctrl.network.PlugEnum;
import com.simonbrunner.msnswitchctrl.network.SwitchControl;
import com.simonbrunner.msnswitchctrl.network.SwitchStatus;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchView extends VerticalLayout implements View {

    private static final Logger log = LoggerFactory.getLogger(SwitchView.class);

    private ApplicationConfiguration appConfig = ConfigurationReader.getInstance().getApplicationConfiguration();

    private Label titleLabel;
    private Label descriptionLabel;
    private Button plug1;
    private Button plug2;

    private SwitchConfiguration switchConfig;
    private SwitchStatus switchStatus;

    public SwitchView() {
        setMargin(true);

        titleLabel = new Label();
        titleLabel.addStyleName("h1");
        addComponent(titleLabel);

        descriptionLabel = new Label();
        descriptionLabel.addStyleName("h3");
        addComponent(descriptionLabel);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        addComponent(row);

        plug1 = new Button();
        plug1.addClickListener((Button.ClickListener) event -> {
            SwitchControl switchControl = new SwitchControl();
            switchStatus = switchControl.changeStatus(switchConfig, switchStatus, PlugEnum.PLUG_1);
            readSwitchStatus();
        });
        row.addComponent(plug1);

        plug2 = new Button();
        plug2.addClickListener((Button.ClickListener) event -> {
            SwitchControl switchControl = new SwitchControl();
            switchStatus = switchControl.changeStatus(switchConfig, switchStatus, PlugEnum.PLUG_2);
            readSwitchStatus();
        });
        row.addComponent(plug2);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.info("Entering view with name {}", event.getViewName());
        if (switchConfig == null) {
            switchConfig = appConfig.getSwitchConfiguration(event.getViewName());
        }

        titleLabel.setValue(switchConfig.getName());
        descriptionLabel.setValue(switchConfig.getDescription());
        plug1.setCaption(switchConfig.getPlug1Name());
        plug2.setCaption(switchConfig.getPlug2Name());

        readSwitchStatus();
    }

    private void readSwitchStatus() {
        try {
            SwitchControl switchControl = new SwitchControl();
            switchStatus = switchControl.readStatus(switchConfig);

            String plug1Style;
            if (switchStatus.getPlug1()) {
                plug1Style = "friendly";
            } else {
                plug1Style = "danger";
            }
            log.info("Setting Plug1 to Style {}", plug1Style);
            plug1.setStyleName(plug1Style);

            String plug2Style;
            if (switchStatus.getPlug2()) {
                plug2Style = "friendly";
            } else {
                plug2Style = "danger";
            }
            log.info("Setting Plug2 to Style {}", plug2Style);
            plug2.setStyleName(plug2Style);

            // Enable Switches
            plug2.setEnabled(true);
            plug1.setEnabled(true);

        } catch (Exception e) {
            log.error("Unable to read Switch status", e);

            plug1.setEnabled(false);
            plug2.setEnabled(false);

            plug1.setStyleName("primary");
            plug2.setStyleName("primary");

            addErrorMessage(switchConfig);
        }
    }

    private void addErrorMessage(SwitchConfiguration switchConfig) {
        Notification notification = new Notification(
                "Warning",
                "Unable to connect to " + switchConfig.getIpAdress(),
                Notification.TYPE_ERROR_MESSAGE);
        notification.setPosition(Position.TOP_CENTER);
        notification.show(Page.getCurrent());
    }
}