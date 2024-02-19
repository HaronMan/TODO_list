package fr.haronman.todolist.model;

import java.util.Date;

public class Todo {
    int id;
    String titre, description;
    Date date;
    boolean fait;

    public Todo(){}

    public Todo(int id, String titre, String description, Date date) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.fait = false;
    }
}
