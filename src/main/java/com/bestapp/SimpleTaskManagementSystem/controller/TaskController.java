package com.bestapp.SimpleTaskManagementSystem.controller;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the tasks
 * @see TaskDTO
 * @see TaskService
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "API for task management")
public class TaskController {

    private final TaskService taskService;

    /**
     * Creating a new task.
     * @return the response with the created task in JSON format and the HTTP 201 status code (Created).<br>
     */
    @Operation(
            summary = "Create new task",
            description = "Create new task with task id",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Task was successfully created",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(responseCode = "201", description = "Task was successfully created", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        TaskDTO createdTaskDTO = taskService.createTask(createOrUpdateTaskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDTO);
    }

    /**
     * Getting task by id.
     * @param id task identification number.
     * @return the response with the found task in JSON format and the HTTP 200 status code (Ok).<br>
     * If the task not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find task by id",
            description = "Search by task id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Task was successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "Task was successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable ("id") @Positive Long id) {
        TaskDTO foundTaskDTO = taskService.findTaskById(id);
        if (foundTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundTaskDTO);
    }

    /**
     * Getting all tasks pageable.
     * @param pageNumber page number
     * @param pageSize page size number
     * @return the response with the found task list in JSON format and the HTTP 200 status code (Ok).<br>
     * If the task list not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find all tasks pageable",
            description = "Search all tasks pageable",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All tasks successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "All tasks successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Tasks not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam("pageNumber") @Positive Integer pageNumber,
                                                     @RequestParam("pageSize") @Positive Integer pageSize) {
        List<TaskDTO> foundTaskDTOS = taskService.findAllTasks(pageNumber, pageSize);
        if (foundTaskDTOS == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundTaskDTOS);
    }

    /**
     * Updating task.
     * @param id task identification number.
     * @return the response with the updated task in JSON format and the HTTP 200 status code (Ok).<br>
     * If the task list not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Update task by id",
            description = "Search task by id to update",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Task was successfully updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskDTO.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "Task was successfully updated", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") @Positive Long id,
                                              @RequestBody @Valid CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        TaskDTO updatedTaskDTO = taskService.updateTask(id, createOrUpdateTaskDTO);
        if (updatedTaskDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTaskDTO);
    }

    /**
     * Deleting task by id.
     * @param id task identification number.
     */
    @Operation(
            summary = "Delete task by id",
            description = "Search task by id to delete",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task was successfully deleted", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("id") @Positive Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }
}
