package com.example.todolist.Controllers;

import com.example.todolist.Models.ToDoList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todolist")
public class ToDoController {
    // let's go with HashMap here
    // så att man kan stoppa in och ut i okronologisk ordning
    // sen är det de där med att Concurrent är mer trådsäkert.

    // to do
    // det jag vill kunna göra med min lista
    // är att om important = true
    // så ska de hamna överst i listan

    // private Map<Integer, ToDoList> toDos = new HashMap<>();

    private List<ToDoList> toDos = new ArrayList<>();

    // sen har han då skaffat w/e the @GetMapping shit
    // som antagligen har med det där api shittet att göra
    // GET, POST, DELETE och PUT
    public ToDoController() {
        // säger till listan att lägga till Integer och ToDoList klassen
        // som sen i sin tur är (1, "task", true/false)
        // okej så kanske syntaxen sitter lite bättre då.
//        toDos.put(1, new ToDoList(1, "Köpa avslutningspresent", true));
//        toDos.put(2, new ToDoList(2, "Dricka kaffe", false));
//        toDos.put(3, new ToDoList(3, "Slå in julklappar", false));

        // såhär ser det ut i Postman då.. Inte det snyggaste.
        // så får se om jag bytar struktur på min map då
//        "3": {
//            "id": 3,
//                    "task": "Slå in julklappar",
//                    "important": false
//        }

        // så kanske en Array är snyggare... Vi kan testa.
        toDos.add(new ToDoList(1, "Handla julklappar", true));
        toDos.add(new ToDoList(2, "Hälsa på Kungafamiljen", false));
        toDos.add(new ToDoList(3, "Knyta skorna", true));
        toDos.add(new ToDoList(4, "Fira nyår", true));

        // k uppenbart snyggare
//        {
//            "id": 3,
//                "task": "Knyta skorna",
//                "important": true
//        }

    }

    // hanterar HTTP GET förfrågningar
    @GetMapping
    // public Map<Integer, ToDoList> getAllTasks() { return toDos; }

    public List<ToDoList> getAllTasks() { return toDos; }

    // behöver jag denna?
    // kan inte se användningsfunktionen för att säka på en sak via ID...
    @GetMapping("/{id}")
    public ResponseEntity<ToDoList> getTaskById(@PathVariable int id) {
        return toDos.stream()
                .filter(toDoList -> toDoList.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // detta fattar jag däremot! Vi måste ha en funktion för att
    // lägga till nya tasks
    @PostMapping

    // to do
    // generera nummer som id
    // så man inte behöver tillsätta det själv

    public ResponseEntity<ToDoList> addTask(@RequestBody ToDoList toDoList) {
        if (toDoList != null && toDoList.getId() > 0) {
            toDos.add(toDoList);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDoList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // this also makes sense.
    // fall jag skrev fel så behöver jag ha en funktion
    // där jag kan ändra (y)
    // och då vill jag ju söka på id för att ändra den
    @PutMapping("/{id}")
    public ResponseEntity<ToDoList> updateTask(@PathVariable int id, @RequestBody ToDoList updateToDoList) {
        for (ToDoList toDoList : toDos){
            if(toDoList.getId() == id) {
              toDoList.setTask(updateToDoList.getTask());
              toDoList.setImportant(updateToDoList.isImportant());

              return ResponseEntity.ok(toDoList);

            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    // okej så om jag lägger till en task via postman så kan jag inte
    // använda den som ex till att ändra task i postman


    // denna makes också sense för vi vill kunna ta bort en task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        boolean removed = false;
        for (int i = 0; i < toDos.size(); i++){
            if (toDos.get(i).getId() == id) {
                toDos.remove(i);
                removed = true;
            }
        }
        if (removed) {
            return ResponseEntity.ok("Task " + id + " removed successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no task with id " + id);
        }
    }




}
