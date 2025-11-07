package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;//converts Java objects to JSON

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;//loads only web layer(Controller and its related configs)
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;//simulates HTTP requests

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllTasks() throws Exception {
        List<Task> task = List.of(new Task(1L, "New Task", false));
        when(taskService.getAllTasks()).thenReturn(task);
        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("New Task"));
        //Simulates GET request to /api/task
        //Expect i. HTTP 200 OK ii. JSON response first element has "title": "New Task"
        //getAllTasks() returns Array so [0].title, where 0 is index
        //but other methods only return JSON object 
    }
    
    @Test
    void shouldAddTask() throws Exception {
    Task input = new Task(null, "New Task", false);
    Task saved = new Task(1L, "New Task", false);
    when(taskService.addTask(any(Task.class))).thenReturn(saved);

    mockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("New Task"))
            .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldMarkTaskAsDone() throws Exception {
        Task updated = new Task(1L, "New Task", true);
        when(taskService.markDone(1L)).thenReturn(updated);
        mockMvc.perform(put("/api/tasks/1/done")).andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1")).andExpect(status().isOk());
        //Does not return anything only STATUS code
        Mockito.verify(taskService).deleteTask(1L);
    }

}