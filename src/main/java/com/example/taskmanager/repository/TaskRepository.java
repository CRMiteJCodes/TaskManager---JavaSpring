/**
 * Repository is the project's data acess layer - middle man btw Service(or Controller) and the Database
 * hides SQL details and gives clean Java methods like save(), findAll(), deleteById().
 */
package com.example.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;//base repository interface that provides CRUD methods
import com.example.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {// hibernate does the work automatically
    /**
     * provides basic CRUD methods
     * u can later define custom queries later like:
     * List<Task> findByCompleted(boolean completed);
     * here JPA smartly understands and interprets what the method is asking so
     * camelCase is important
     * ex: List<Task> findByCompleted(boolean completed); is user defined and JPA
     * converts it to
     * SELECT * FROM task WHERE completed = ?
     * 
     * then in service layer Now u can create a method and call List<Task>
     * findByCompleted(boolean);
     */
}