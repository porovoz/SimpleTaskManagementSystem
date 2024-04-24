package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.model.Task;

import java.util.List;

/**
 * The {@code TaskMapper} is an interface responsible for converting task entities (Task) and DTO
 * (Data Transfer Object) to various formats and vice versa.
 */
public interface TaskMapper {

    TaskDTO toTaskDTO(Task task);
    Task toTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO);
    void updateTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO, Task task);
    List<TaskDTO> toTaskDTOList(List<Task> tasks);
}
