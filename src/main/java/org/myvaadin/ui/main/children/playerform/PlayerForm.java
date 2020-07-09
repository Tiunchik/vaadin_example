package org.myvaadin.ui.main.children.playerform;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.myvaadin.server.player.ChessPlayerDTO;
import org.myvaadin.server.school.ChessSchoolDTO;
import org.myvaadin.server.school.ChessSchoolService;
import org.myvaadin.server.security.Roles;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class PlayerForm extends FormLayout {

    private ChessSchoolService schoolService;

    private ChessPlayerDTO player;

    TextField name = new TextField("Name");
    TextField login = new TextField("Login");
    PasswordField password = new PasswordField("Password");
    CheckboxGroup<String> rights = new CheckboxGroup<>();
    ComboBox<ChessSchoolDTO> school = new ComboBox<>("School");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ChessPlayerDTO> binder = new BeanValidationBinder<>(ChessPlayerDTO.class);

    public PlayerForm(ChessSchoolService schoolService) {
        this.schoolService = schoolService;
        rights.setLabel("Rights");
        rights.addThemeVariants(CheckboxGroupVariant.MATERIAL_VERTICAL);
        rights.setItems(Arrays.stream(Roles.values())
                .map(e -> e.toString())
                .collect(Collectors.toList()));
        school.setItems(schoolService.getAllSchools());
        school.setItemLabelGenerator(ChessSchoolDTO::getCalled);
        binder.bindInstanceFields(this);

        addClassName("player-form");
        add(name,
                login,
                password,
                rights,
                school,
                createButtonsLayout());
    }

    public void setPlayer(ChessPlayerDTO player) {
        this.player = player;
        binder.readBean(player);
    }

    public void checkRights(ChessPlayerDTO player) {
        if (player != null) {
            rights.setValue(new HashSet<>(player.getRoles()));
        } else {
            rights.setValue(new HashSet<>());
        }

    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, player)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(player);
            fireEvent(new SaveEvent(this, player));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class PlayerFormEvent extends ComponentEvent<PlayerForm> {
        private ChessPlayerDTO player;

        protected PlayerFormEvent(PlayerForm source, ChessPlayerDTO player) {
            super(source, false);
            this.player = player;
        }

        public ChessPlayerDTO getPlayer() {
            return player;
        }
    }

    public static class SaveEvent extends PlayerFormEvent {
        SaveEvent(PlayerForm source, ChessPlayerDTO contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends PlayerFormEvent {
        DeleteEvent(PlayerForm source, ChessPlayerDTO contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends PlayerFormEvent {
        CloseEvent(PlayerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
