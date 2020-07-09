package org.myvaadin.ui.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.myvaadin.server.security.Utils;
import org.myvaadin.ui.main.children.BestViewList;
import org.myvaadin.ui.main.children.EloHistogram;
import org.myvaadin.ui.main.children.PlayerViewList;

@CssImport("./styles/shared-styles.css")
public class MainVew extends AppLayout {

    public MainVew() {
        createHeader();
        createPage();
    }

    private void createHeader() {
        H1 logo = new H1("Closed chess championship at Hogwarts");
        logo.addClassName("logo");
        NativeButton button = new NativeButton(
                "Logout");
        button.setClassName("vaadin-button");
        button.addClickListener(event -> {
                    Utils.logoutAction();
                }
        );
        HorizontalLayout mainHeader = new HorizontalLayout();
        mainHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        HorizontalLayout leftHeadner = new HorizontalLayout(new DrawerToggle(), logo);
        HorizontalLayout rightHeader = new HorizontalLayout(button);
        leftHeadner.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        rightHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        mainHeader.setWidth("100%");
        mainHeader.addClassName("header");
        mainHeader.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        leftHeadner.setWidth("100%");
        leftHeadner.addClassName("header");
        leftHeadner.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightHeader.setWidth("100%");
        rightHeader.addClassName("header");
        rightHeader.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        mainHeader.add(leftHeadner, rightHeader);
        addToNavbar(mainHeader);
    }

    private void createPage() {
        RouterLink listLink = new RouterLink("Player list", PlayerViewList.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("School score", EloHistogram.class),
                new RouterLink("Best players", BestViewList.class)
        ));
    }
}
