package com.example.todolist.Models;

// i Muhannads Model Class:'
// Getters och setters för hans böcker
// hur applicerar jag det på en to do lista?
// kan tex ha
// id, name of task och description of task


public class ToDoList {

    // instansvariablerna
    private int id;
    private String task;
    private boolean important;

    // men detta kan jag väl vid det här laget att
    // detta är constructor
    public ToDoList(int id, String task, boolean important){
        this.id = id;
        this.task = task;
        this.important = important;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public boolean isImportant() {
        return important;
    }
    public void setImportant(boolean important) {
        this.important = important;
    }

}
