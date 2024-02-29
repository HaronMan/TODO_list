package fr.haronman.todolist.model;

import java.util.Date;

public class Todo {
    int id;
    String titre, description, date;
    boolean fait;

    public Todo(){}

    public Todo(int id, String titre, String description, String date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.fait = false;
    }

    public Todo(String titre) {
        this.titre = titre;
        this.fait = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getFait(){
        return fait;
    }

    public void setFait(boolean fait) {
        this.fait = fait;
    }
}
