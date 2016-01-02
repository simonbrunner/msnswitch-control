package com.simonbrunner.msnswitchctrl.ui;

import javax.servlet.annotation.WebServlet;

import com.simonbrunner.msnswitchctrl.config.ApplicationConfiguration;
import com.simonbrunner.msnswitchctrl.config.ConfigurationReader;
import com.simonbrunner.msnswitchctrl.config.SwitchConfiguration;
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

    private Navigator navigator;
    private Map<String, String> menuItems = new LinkedHashMap<>();
    private ApplicationConfiguration appConfig = ConfigurationReader.getInstance().getApplicationConfiguration();

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ControlUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MenuLayout root = new MenuLayout();
        ComponentContainer viewDisplay = root.getContentContainer();
        getPage().setTitle("MSNswitch-control");
        root.setWidth("100%");
        root.addMenu(buildMenu());
        addStyleName(ValoTheme.UI_WITH_MENU);

        navigator = new Navigator(this, viewDisplay);
        navigator.addView("", LoginView.class);
        for (SwitchConfiguration switchConfig : appConfig.getSwitchConfigurations()) {
            navigator.addView(switchConfig.getName(), SwitchView.class);
        }

        setContent(root);
    }

    private CssLayout buildMenu() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItems.put("common", "Common UI Elements");
        menuItems.put("buttons-and-links", "Buttons & Links");
        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        CssLayout menu = new CssLayout();
        menu.addComponent(menuItemsLayout);

        Label label = new Label("MSN Switches", ContentMode.HTML);
        label.setPrimaryStyleName("valo-menu-subtitle");
        label.addStyleName("h4");
        label.setSizeUndefined();
        menuItemsLayout.addComponent(label);

        for (Map.Entry<String, String> item : menuItems.entrySet()) {
            final Button b = new Button(item.getValue(), (Button.ClickListener) event -> navigator.navigateTo(item.getKey()));
            b.setHtmlContentAllowed(true);
            b.setPrimaryStyleName("valo-menu-item");
            menuItemsLayout.addComponent(b);
        }
        label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
                + appConfig.getSwitchConfigurations().size() + "</span>");

        return menu;
    }
}
