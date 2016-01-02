package com.simonbrunner.msnswitchctrl.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by simon on 02/01/16.
 */
public class ButtonsAndLinks extends VerticalLayout implements View {
    /**
     *
     */
    public ButtonsAndLinks() {
        setMargin(true);

        Label h1 = new Label("Buttons");
        h1.addStyleName("h1");
        addComponent(h1);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        addComponent(row);

        Button button = new Button("Normal");
        row.addComponent(button);

        button = new Button("Disabled");
        button.setEnabled(false);
        row.addComponent(button);

        button = new Button("Primary");
        button.addStyleName("primary");
        row.addComponent(button);

        button = new Button("Friendly");
        button.addStyleName("friendly");
        row.addComponent(button);

        button = new Button("Danger");
        button.addStyleName("danger");
        row.addComponent(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}