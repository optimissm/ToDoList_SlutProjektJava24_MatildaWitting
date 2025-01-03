package org.example.demolist;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloController {

    @FXML
    private TextField input_TaskId;

    @FXML
    private TextField input_TaskName;

    @FXML
    private RadioButton input_TaskPriorityNo;

    @FXML
    private RadioButton input_TaskPriorityYes;

    @FXML
    private TextArea textArea_AllTasks;

    @FXML
    private ListView<ToDoList> listView_allTasks;

    @FXML
    void addNewTask(ActionEvent event) {
        try {
            // För att ställa in rätt namn på Task
            int taskId = Integer.parseInt(input_TaskId.getText());
            String taskName = input_TaskName.getText();

            // för att ställa in viktigt/inte
            // boolean taskPriority = Boolean.parseBoolean(input_TaskPriorityYes.getText());
            boolean important = input_TaskPriorityYes.isSelected();

            ToDoList myList = new ToDoList(taskId, taskName, important);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(myList);


            URL url = new URL("http://localhost:8080/api/todolist");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            String response = readResponse(connection);
            textArea_AllTasks.setText(response);

        } catch (Exception e) {
            textArea_AllTasks.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    void showAllTasks(ActionEvent event) {

        try {
            URL url = new URL ("http://localhost:8080/api/todolist");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            String json = readResponse(connection);
            // debugging json så att den fungerar korrekt
            System.out.println("Fetched JSON: " + json);

            ObjectMapper mapper = new ObjectMapper();
            ToDoList[] toDoLists = mapper.readValue(json, ToDoList[].class);

            StringBuilder sbList = new StringBuilder();
            for (ToDoList item : toDoLists) {
                sbList.append(item.toString()).append("\n");
            }

            listView_allTasks.getItems().clear();
            listView_allTasks.getItems().addAll(toDoLists);


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader;
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }



}