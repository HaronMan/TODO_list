package fr.haronman.todolist;

public class Todo {
    private int id;
    private String titre;
    private boolean check;

    public Todo(String titre) {
        this.titre = titre;
        this.check = false;
    }

    public Todo(int id, String titre) {
        this.id = id;
        this.titre = titre;
        this.check = false;
    }

    public void check() {
        check= !check;
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }
}