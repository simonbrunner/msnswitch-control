package com.simonbrunner.msnswitchctrl.ui;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gwt.codegen.server.StringGenerator;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.LinkedHashMap;
import java.util.Map;

@Theme("mytheme")
@Widgetset("com.simonbrunner.msnswitchctrl.MyAppWidgetset")
public class ControlUI extends UI {

    private MenuLayout root = new MenuLayout();
    private ComponentContainer viewDisplay = root.getContentContainer();
    private CssLayout menu = new CssLayout();
    private CssLayout menuItemsLayout = new CssLayout();
    private Navigator navigator;
    private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ControlUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("MSNswitch-control");
        root.setWidth("100%");
        root.addMenu(buildMenu());
        addStyleName(ValoTheme.UI_WITH_MENU);

        navigator = new Navigator(this, viewDisplay);
        navigator.addView("", ButtonsAndLinks.class);
        navigator.addView("buttons-and-links", ButtonsAndLinks.class);

        setContent(root);
    }

    CssLayout buildMenu() {
        menuItems.put("common", "Common UI Elements");
        menuItems.put("buttons-and-links", "Buttons & Links");
        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);

        Label label = new Label("MSN Switches", ContentMode.HTML);
        label.setPrimaryStyleName("valo-menu-subtitle");
        label.addStyleName("h4");
        label.setSizeUndefined();
        menuItemsLayout.addComponent(label);

        int count = -1;
        for (final Map.Entry<String, String> item : menuItems.entrySet()) {
            final Button b = new Button(item.getValue(), new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    navigator.navigateTo(item.getKey());
                }
            });
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName("valo-menu-item");
            menuItemsLayout.addComponent(b);
            count++;
        }
        label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
                + count + "</span>");

        return menu;
    }
}
