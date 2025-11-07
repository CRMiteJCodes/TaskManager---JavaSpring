package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;

//testing and mocking tools
import org.junit.jupiter.api.BeforeEach;//runs befor every test method
import org.junit.jupiter.api.Test;//marks method as JUnit test case
import org.mockito.InjectMocks;//creates the actual class under test and automatically injects mock into it
import org.mockito.Mock;//Creates fake versions of dependencies
import org.mockito.MockitoAnnotations;//initialize all mock and InjectMocks

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//for test data and Optional handling
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    //we only test the TaskService Class

    @Mock//so database in never touched
    private TaskRepository repo;

    @InjectMocks
    private TaskService taskService;
    //mock the Repository and inject it into Service, so wherever service expects repository,
    // give it the mock. Here Mockito is the middleman

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //when : define the fake behavior when mock is called
    //when(method)is called on any mock object,(retrun this) instead of running the real code. 
    @Test
    void testGetAllTasks(){
        Task t1=new Task(1L, "Task 1", false);
        Task t2=new Task(2L, "Task 2", false);
        
        when(repo.findAll()).thenReturn(Arrays.asList(t1, t2));
        //when .findAll() is called, don't run the actual method but return t1 and t2 as List so,
        //it mocks the actual behavior of findAll and database is not touched.
        List<Task> result = taskService.getAllTasks();
        //getAllTasks() would call repo.findAll() but when() will be executed as it mocks.
        assertEquals(2, result.size());//verifies result.size()==2
        verify(repo, times(1)).findAll();//verifies that repo.findAll() was called once
        //This method tests that the getAllTasks() of service does exactly what we want
    }

    @Test
    void testAddTask(){
        Task newTask = new Task(null, "New Task", false);
        Task savedTask = new Task(1L, "New Task", false);
        when(repo.save(newTask)).thenReturn(savedTask);
        //mocking what exaclty would happen if database was called
        //the newTask would get an id if it gets saved
        Task result = taskService.addTask(newTask);
        assertNotNull(result.getId());//verifies id is not null 
        assertEquals("New Task", result.getTitle());
        verify(repo, times(1)).save(newTask);
    }

    @Test
    void testMarkDone_Success(){
        Task task = new Task(1L, "Test Task", false);
        when(repo.findById(1L)).thenReturn(Optional.of(task));
        //since it mark's done based on the id
        when(repo.save(any(Task.class))).thenReturn(new Task(1L, "Test Task", true));
        //any object of Task class
        Task updated=taskService.markDone(1L);
        assertTrue(updated.isCompleted());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(any(Task.class));
    }

    @Test
    void testMarkDone_TaskNotFound(){
        when(repo.findById(999L)).thenReturn(Optional.empty());
        
        RuntimeException ex = assertThrows(TaskNotFoundException.class, ()->taskService.markDone(999L));
        assertEquals("Task not found with id: 999", ex.getMessage());
        verify(repo, times(1)).findById(999L);
    }

}