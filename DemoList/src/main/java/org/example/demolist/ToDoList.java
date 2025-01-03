package org.example.demolist;

public class ToDoList {
    private int id;
    private String task;
    private boolean important;

    public ToDoList() {}

    public ToDoList (int id, String taskName, boolean taskPriority){
        this.id = id;
        this.task = taskName;
        this.important = taskPriority;
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

    @Override
    public String toString() {
        return id + ". " + (task != null ? task : "Okänt") + ", " + (important ? "Viktigt" : "Inte viktigt");
    }


}
