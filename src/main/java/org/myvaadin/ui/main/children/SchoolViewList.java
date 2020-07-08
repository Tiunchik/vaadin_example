package org.myvaadin.ui.main.children;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.myvaadin.server.player.ChessPlayerService;
import org.myvaadin.server.school.ChessSchoolDTO;
import org.myvaadin.server.school.ChessSchoolService;
import org.myvaadin.ui.main.MainVew;

@Route(value = "school", layout = MainVew.class)
@PageTitle("School list")
public class SchoolViewList extends VerticalLayout {


    private final Grid<ChessSchoolDTO> grid = new Grid<>(ChessSchoolDTO.class);

    private final ChessSchoolService schoolService;

    private final ChessPlayerService playerService;

    public SchoolViewList(ChessSchoolService schoolService,
                          ChessPlayerService playerService) {
        this.playerService = playerService;
        this.schoolService = schoolService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(schoolDTO -> schoolDTO.getCalled())
                .setHeader("School name");
        grid.addColumn(schoolDTO -> playerService
                .count(schoolDTO.getId()))
                .setHeader("School students");
    }

    private void updateList() {
        grid.setItems(schoolService.getAllSchools());
    }

}
