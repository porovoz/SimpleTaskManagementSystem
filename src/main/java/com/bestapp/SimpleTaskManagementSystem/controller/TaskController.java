package com.bestapp.SimpleTaskManagementSystem.controller;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        TaskDTO createdTaskDTO = taskService.createTask(createOrUpdateTaskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable ("id") @Positive Long id) {
        TaskDTO foundTaskDTO = taskService.findTaskById(id);
        if (foundTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundTaskDTO);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam("pageNumber") @Positive Integer pageNumber,
                                                     @RequestParam("pageSize") @Positive Integer pageSize) {
        List<TaskDTO> foundTaskDTOS = taskService.findAllTasks(pageNumber, pageSize);
        if (foundTaskDTOS == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundTaskDTOS);
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") @Positive Long id,
                                              @RequestBody @Valid CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        TaskDTO updatedTaskDTO = taskService.updateTask(id, createOrUpdateTaskDTO);
        if (updatedTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTaskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("id") @Positive Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
