package org.myvaadin.server.player;

import org.myvaadin.server.school.ChessSchool;
import org.myvaadin.server.security.Roles;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "player")
public class ChessPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(nullable = false)
    private int elo;

    @ManyToOne
    @JoinColumn(name = "school_id",
            foreignKey = @ForeignKey(name = "SCHOOL_ID_FK")
    )
    private ChessSchool school;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Roles> roles = new ArrayList<>();

    public ChessPlayer() {
    }

    public ChessPlayer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public ChessSchool getSchool() {
        return school;
    }

    public void setSchool(ChessSchool school) {
        this.school = school;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPlayer that = (ChessPlayer) o;
        return id == that.id
                && elo == that.elo
                && name.equals(that.name)
                && school.equals(that.school);
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, elo, school.getCalled());
    }

    @Override
    public String toString() {
        return "ChessPlayer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", elo=" + elo +
                ", school=" + school.getCalled() +
                '}';
    }
}
