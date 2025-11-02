package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    /**
     * !TaskRepository is an interface, so we cant create instance for it like
     * TaskRepository repo=new TaskREpository();
     * Spring automatically provides ready to use instance/bean from its
     * ApplicationContext.
     * ""Dependency Injection""
     * Dependency can be anything(class, object...) that a class needs, to work. 
     * The class cannot function without it. Here service cannot function without repository^^
     */
    private TaskRepository repo;

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task addTask(Task t) {
        return repo.save(t);
    }

    public void deleteTask(Long id) {
        repo.deleteById(id);
    }

    public Task markDone(Long id) {
        Task t = repo.findById(id).orElseThrow(() -> new RuntimeException("Task not found!"));
        /**
         * repo.findById(id) returns an Optional(a container Object)
         * because there is a chance that the given id does not exist and returning null
         * may cause null pointer exception so Optional, and orElseThrow is method on Optional
         * 
         * equivalent to:
         * Optional<Task> ot = repo.findById(id);
         * if(ot.isPresent()){
         * Task t=ot.get();
         * }
         * else{
         * throw new NoSuchElementException();
         * }
         */
        t.setCompleted(true);
        return repo.save(t);
    }
}

/**
 * ^^ A bean is object managed by Spring container, Spring can create it,
 * share it and inject it wherever needed.
 * Eg. @Service annotation tells spring to create an create an instance of this class
 *  and manage it as a bean
 * In spring you dont mannually write objects as 
 * i.you have to mannually manage dependencies 
 * ii.testing becomes difficult 
 * TaskRepository is also a bean(it's a @Repository)***, 
 * so at runtime, Component Scanning finds all classes annotated with @Component, @Service, 
 * @Repository etc it automatically creates beans for each of them.
 * So when Spring sees that TaskService needs a TaskRepository dependency, it injects the existing bean 
 * into "TaskService.repo", @autowired automatically injects dependency.
 * 
 *  ***TaskRepository has no annotation, but it extends JpaRepository, 
 *     so Springboot automatically generates bean
 *      for non-Jpa(own logics or Non relational data - MongoDB,\) you need to mention the @Repository  
 * 
 * -> is a lambda expression-shorthand way to define a tiny function
 * Controller @RestController talks to the user(API request/response):
 * Service @service Applies Business Logic and prepares the table:
 * Repository @Repository fetches or saves the data
 * Model/Entity @entity represents the raw data (table row = object) Defines
 * Table structure
 * 
 * ^when create/Post, Controller Takes JSON, turns to Task object and calls
 * service.addTask(t), service recieves object from contoller, adds logic passes
 * to repository, Repository using hibernate automatcally creates a row using
 * the model/entity
 * 
 * 
 * ^when read/GET data, Controller receives the GET request and calls service
 * the
 * service calls repository which then automatically excutes SQL while entity
 * maps rows to objects and sends back to repository to service to controller to
 * Client as JSON
 * here SERVICE before calling repository may validate input parameters, add
 * acess rules/security(check which user is logged in, filter to show only that
 * user's tasks)
 * After the service fetches the List, before returning it to the controller it
 * may sort, hide some fields which u dont want to show, or show extra info
 * based on the data, handle empty results gracefully
 */