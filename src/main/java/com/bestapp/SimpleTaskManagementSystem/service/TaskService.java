package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    /**
     * Creation of a task object
     * @param createOrUpdateTaskDTO object containing all necessary information for creation a task object
     */
    TaskDTO createTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO);

    /**
     * Getting task by id
     * @param id task identification number in database
     */
    TaskDTO findTaskById(Long id);


    /**
     * Getting all tasks pageable
     * @param pageNumber page number
     * @param pageSize page size number
     */
    List<TaskDTO> findAllTasks(Integer pageNumber, Integer pageSize);

    /**
     * Updating task
     * @param id task identification number in database
     * @param createOrUpdateTaskDTO object containing all necessary information for updating a task object
     */
    TaskDTO updateTask(Long id, CreateOrUpdateTaskDTO createOrUpdateTaskDTO);

    /**
     * Deleting task by id
     * @param id task identification number in database
     */
    void deleteTaskById(Long id);
}
