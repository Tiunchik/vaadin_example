package org.myvaadin.server.player;


import org.myvaadin.server.school.ChessSchoolDTO;

import java.util.ArrayList;
import java.util.List;

public class ChessPlayerDTO {

    private int id;

    private String name;

    private int elo;

    private ChessSchoolDTO school;

    private List<String> roles = new ArrayList();

    private String login;

    private String password;

    public ChessPlayerDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public ChessSchoolDTO getSchool() {
        return school;
    }

    public void setSchool(ChessSchoolDTO school) {
        this.school = school;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
