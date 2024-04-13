package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO);
    TaskDTO findTaskById(Long id);
    List<TaskDTO> findAllTasks(Integer pageNumber, Integer pageSize);
    TaskDTO updateTask(Long id, CreateOrUpdateTaskDTO createOrUpdateTaskDTO);
    void deleteTaskById(Long id);
}
