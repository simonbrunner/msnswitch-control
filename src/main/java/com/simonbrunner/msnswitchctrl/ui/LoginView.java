package com.simonbrunner.msnswitchctrl.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "User Login";

    public LoginView() {
        setMargin(true);

        Label h1 = new Label("User login");
        h1.addStyleName("h1");
        addComponent(h1);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        addComponent(row);

        Button button = new Button("Primary");
        button.addStyleName("primary");
        row.addComponent(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}