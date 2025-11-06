package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository repo;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks(){
        Task t1=new Task(1L, "Task 1", false);
        Task t2=new Task(2L, "Task 2", false);
        
        when(repo.findAll()).thenReturn(Arrays.asList(t1, t2));
        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testAddTask(){
        Task newTask = new Task(null, "New Task", false);
        Task savedTask = new Task(1L, "New Task", false);
        when(repo.save(newTask)).thenReturn(savedTask);
        Task result = taskService.addTask(newTask);
        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(repo, times(1)).save(newTask);
    }

    @Test
    void testMarkDone_Success(){
        Task task = new Task(1L, "Test Task", false);
        when(repo.findById(1L)).thenReturn(Optional.of(task));
        when(repo.save(any(Task.class))).thenReturn(new Task(1L, "Test Task", true));
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