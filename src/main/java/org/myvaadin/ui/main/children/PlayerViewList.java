package org.myvaadin.ui.main.children;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.myvaadin.server.player.ChessPlayerDTO;
import org.myvaadin.server.player.ChessPlayerService;
import org.myvaadin.server.school.ChessSchoolService;
import org.myvaadin.ui.main.MainVew;
import org.myvaadin.ui.main.children.playerform.PlayerForm;

@Route(value = "", layout = MainVew.class)
@PageTitle("Player list")
@CssImport("./styles/shared-styles.css")
public class PlayerViewList extends VerticalLayout {


    private final Grid<ChessPlayerDTO> grid = new Grid<>(ChessPlayerDTO.class);

    private final ChessPlayerService playerService;

    private final ChessSchoolService schoolService;

    private PlayerForm form;

    public PlayerViewList(ChessPlayerService playerService,
                          ChessSchoolService schoolService) {
        this.playerService = playerService;
        this.schoolService = schoolService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new PlayerForm(schoolService);
        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "elo", "login");
        grid.addColumn(chessPlayerDTO -> chessPlayerDTO
                .getSchool()
                .getCalled())
                .setHeader("School");
    }

    private void updateList() {
        grid.setItems(playerService.getAllPlayers());
    }

}
