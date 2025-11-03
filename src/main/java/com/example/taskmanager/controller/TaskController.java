/**
 * Handles HTTP requests
 * Contoller exposes REST endpoints to clients(Expose the task operations through REST endpoints)
 */
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks") /**tells springboot that all endpoints 
* in this controller will start with /api/task
* so put mapping: /api/tasks/{id}/done
*/
public class TaskController {

    @Autowired
    private TaskService service; //private so can be used only in this class

    @GetMapping
    /**
     * Postman/Browser -> Controller -> Service -> Repository -> Database -> Service
     * -> Controller ->JSON Response
     */
    public List<Task> getTasks() {
        return service.getAllTasks();
        /**
         * Calls service layer which then calls Repository which later calls Database
         */
    }

    @PostMapping
    //Postman/Browser -> Controller -> Service -> Repository -> Database
    public Task add(@RequestBody Task t) {
        /**
         * @RequestBody Task t : tells spring to take the JSON body 
         *  of the HTTP request, convert it to Task object t
         */
        return service.addTask(t);
    }

    @PutMapping("/{id}/done") //Updation
    public Task markDone(@PathVariable Long id) {//binds the {id} from url to id variable
        return service.markDone(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTask(id);
    }

}
/**
 * POST browser testing in console:
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
 * fetch("http://localhost:8080/api/tasks/1/",
 {
    method: "DELETE"
 });
 */
