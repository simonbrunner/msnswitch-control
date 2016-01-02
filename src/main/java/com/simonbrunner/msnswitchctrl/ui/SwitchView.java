package com.simonbrunner.msnswitchctrl.ui;

import com.simonbrunner.msnswitchctrl.config.ApplicationConfiguration;
import com.simonbrunner.msnswitchctrl.config.ConfigurationReader;
import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchView extends VerticalLayout implements View {

    private static final Logger log = LoggerFactory.getLogger(SwitchView.class);

    private ApplicationConfiguration appConfig = ConfigurationReader.getInstance().getApplicationConfiguration();

    private Label titleLabel;
    private Label descriptionLabel;
    private Button plug1;
    private Button plug2;

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
        row.addComponent(plug1);
        plug2 = new Button();
        row.addComponent(plug2);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.info("Entering view with name {}", event.getViewName());
        SwitchConfiguration switchConfig = appConfig.getSwitchConfiguration(event.getViewName());

        titleLabel.setValue(switchConfig.getName());
        descriptionLabel.setValue(switchConfig.getDescription());
        plug1.setCaption(switchConfig.getPlug1Name());
        plug2.setCaption(switchConfig.getPlug2Name());

        plug1.addStyleName("friendly");
        plug2.addStyleName("friendly");
        // button.addStyleName("danger");
    }
}