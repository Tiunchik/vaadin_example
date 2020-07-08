package org.myvaadin.ui.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import org.myvaadin.server.security.Utils;
import org.myvaadin.ui.main.children.SchoolViewList;


@Route("login")
@PageTitle("Login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver, AfterNavigationObserver {

    public LoginView() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.setAdditionalInformation(null);
        i18n.setForm(new LoginI18n.Form());
        i18n.getForm().setSubmit("Login");
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Username");
        i18n.getForm().setPassword("Password");
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setAction("login");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (Utils.isUserLoggedIn()) {
            event.forwardTo(SchoolViewList.class);
        } else {
            setOpened(true);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
