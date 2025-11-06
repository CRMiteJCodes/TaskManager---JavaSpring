/**
 * Handles HTTP requests
 * Contoller exposes REST endpoints to clients(Expose the task operations through REST endpoints)
 */
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;

import jakarta.validation.Valid;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j; //for logging 
//logging records what the program is doing while it runs

@Slf4j//Simple Logging Facade for Java
@RestController
@RequestMapping("/api/tasks") /**tells springboot that all endpoints 
* in this controller will start with /api/task
* so put mapping: /api/tasks/{id}/done
*/
public class TaskController {

    //Constructor Injection
    private final TaskService service;
    public TaskController(TaskService service){
        this.service = service;
    }
    /*
     * or: using Lombok
     * @RequiredArgsConstructor //creates cunstructor for all final fields
     * ......
     * private final TaskService service;
     * ......
     */
    //Contructor injection is preferred, safer and better for testing
    /**
     * or: field injection using Autowired
     * @Autowired
     * private TaskService service; 
     */

    @GetMapping
    /**
     * Postman/Browser -> Controller -> Service -> Repository -> Database -> Service
     * -> Controller ->JSON Response
     */
    public List<Task> getTasks() {
        log.info("Fetching all tasks...");
        return service.getAllTasks();
        /**
         * Calls service layer which then calls Repository which later calls Database
         */
    }

    @PostMapping
    //Postman/Browser -> Controller -> Service -> Repository -> Database
    public Task add(@Valid @RequestBody Task t) {
        //@Valid tells Spring, before this request enters this method, check this object
        // using its Annotation(in Model class).
        /**
         * @RequestBody Task t : tells spring to take the JSON body 
         *  of the HTTP request, convert it to Task object t
         */
        log.info("Adding new task: {}",t.getTitle());
        return service.addTask(t);
    }

    @PutMapping("/{id}/done") //Updation
    public Task markDone(@PathVariable Long id) {//binds the {id} from url to id variable
        log.info("Marking task {} as done", id);
        return service.markDone(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.warn("Deleting task {}", id);
        service.deleteTask(id);
    }

}
/**
 * POST browser testing in chrome console:
 * fetch("http://localhost:8080/api/tasks", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({title: "From Browser", completed: false })
    });
 */
/**
 * PUT:
 * fetch("http://localhost:8080/api/tasks/1/done",
 {
    method: "PUT"
 });
 */
/**
 * Delete:
 * fetch("http://localhost:8080/api/tasks/1",
 {
    method: "DELETE"
 });
 */