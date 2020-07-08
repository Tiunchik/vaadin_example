package org.myvaadin.ui.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.myvaadin.ui.main.children.BestViewList;
import org.myvaadin.ui.main.children.SchoolViewList;
import org.myvaadin.ui.main.children.PlayerViewList;

@CssImport("./styles/shared-styles.css")
public class MainVew extends AppLayout {

    public MainVew() {
        createHeader();
        createPage();
    }

    private void createHeader(){
        H1 logo = new H1("Closed chess championship at Hogwarts");
        logo.addClassName("logo");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createPage(){
        RouterLink listLink = new RouterLink("Player list", PlayerViewList.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("School list", SchoolViewList.class),
                new RouterLink("Best players", BestViewList.class)
        ));
    }
}
