package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.model.Task;

import java.util.List;

public interface TaskMapper {

    TaskDTO toTaskDTO(Task task);
    Task toTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO);
    void updateTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO, Task task);
    List<TaskDTO> toTaskDTOList(List<Task> tasks);
}
