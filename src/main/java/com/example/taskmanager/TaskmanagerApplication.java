package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**for CommandLineRunner Test
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
*/

@SpringBootApplication
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}
	/* 
	for testing TaskService using CommandLineRunner (not a unit test like JUnit but automatic run once test)
	@Bean
	public CommandLineRunner demo(TaskService service){ //dependency injection of service
		return args ->{
			System.out.println("========CLR Started=========");
			service.addTask(new Task(null, "Test Task", true));
			System.out.println(service.getAllTasks());
			System.out.println("========CLR Finished=========");
		};
	}
	//@Bean so Spring manages it and executes it by itself
	*/
}
