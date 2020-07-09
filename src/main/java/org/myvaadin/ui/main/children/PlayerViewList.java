package org.myvaadin.ui.main.children;

import com.vaadin.flow.component.button.Button;
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
        configuredForm();
        Button button = new Button();
        button.setText("Add player");
        add(button);
        button.addClickListener(e -> {
            ChessPlayerDTO dto = new ChessPlayerDTO();
            dto.setId(0);
            dto.setElo(400);
            editContact(dto);
        });

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateList();

    }

    private void configureGrid() {
        grid.addClassName("player-grid");
        grid.setSizeFull();
        grid.setColumns("name", "elo", "login");
        grid.addColumn(chessPlayerDTO -> chessPlayerDTO
                .getSchool()
                .getCalled())
                .setHeader("School");
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private void updateList() {
        grid.setItems(playerService.getAllPlayers());
    }

    private void configuredForm() {
        form = new PlayerForm(schoolService);
        form.setVisible(false);
        form.addListener(PlayerForm.SaveEvent.class, this::saveContact);
        form.addListener(PlayerForm.DeleteEvent.class, this::deleteContact);
        form.addListener(PlayerForm.CloseEvent.class, e -> closeEditor());
    }


    public void editContact(ChessPlayerDTO plr) {
        if (plr == null) {
            closeEditor();
        } else {
            form.setPlayer(plr);
            form.checkRights(plr);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPlayer(null);
        form.checkRights(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveContact(PlayerForm.SaveEvent event) {
        playerService.saveUpdatePlayer(event.getPlayer());
        updateList();
        closeEditor();
    }

    private void deleteContact(PlayerForm.DeleteEvent event) {
        playerService.deletePlayer(event.getPlayer().getId());
        updateList();
        closeEditor();
    }
}
