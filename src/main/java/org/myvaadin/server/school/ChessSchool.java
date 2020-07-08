package org.myvaadin.server.school;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="school")
public class ChessSchool {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String called;

    public ChessSchool() {
    }

    public ChessSchool(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessSchool that = (ChessSchool) o;
        return id == that.id
                && Objects.equals(called, that.called);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, called);
    }

    @Override
    public String toString() {
        return "ChessSchool{" +
                "id=" + id +
                ", called='" + called + '\'' +
                '}';
    }
}
