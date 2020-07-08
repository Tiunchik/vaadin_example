package org.myvaadin.ui.main.children;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.myvaadin.server.player.ChessPlayerDTO;
import org.myvaadin.server.player.ChessPlayerService;
import org.myvaadin.ui.main.MainVew;

@Route(value = "top", layout = MainVew.class)
@PageTitle("Top players list")
public class BestViewList extends VerticalLayout {


    private final Grid<ChessPlayerDTO> bestFivePlayersInLastFiveMinutes = new Grid<>(ChessPlayerDTO.class);

    private final ChessPlayerService playerService;

    public BestViewList(ChessPlayerService playerService) {
        H4 logo = new H4("List of players with best rating changed in 5 minutes");
        logo.addClassName("logo");
        add(logo);
        this.playerService = playerService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(bestFivePlayersInLastFiveMinutes);
        updateList();
    }

    private void configureGrid() {
        bestFivePlayersInLastFiveMinutes.addClassName("contact-grid");
        bestFivePlayersInLastFiveMinutes.setSizeFull();
        bestFivePlayersInLastFiveMinutes.setColumns("name", "elo", "login");
        bestFivePlayersInLastFiveMinutes.addColumn(chessPlayerDTO -> chessPlayerDTO
                .getSchool()
                .getCalled())
                .setHeader("Faculty");
    }

    private void updateList() {
        bestFivePlayersInLastFiveMinutes.setItems(playerService
                .getAllBestPlayersInLast5Minutes());
    }

}
