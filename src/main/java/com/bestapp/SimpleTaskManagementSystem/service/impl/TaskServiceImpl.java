package com.bestapp.SimpleTaskManagementSystem.service.impl;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.exception.TaskNotFoundException;
import com.bestapp.SimpleTaskManagementSystem.model.Task;
import com.bestapp.SimpleTaskManagementSystem.repository.TaskRepository;
import com.bestapp.SimpleTaskManagementSystem.service.TaskMapper;
import com.bestapp.SimpleTaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the service to work with the tasks
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Creating a task object and saving it to a database.<br>
     * - The repository method is used {@link TaskRepository#save(Object)}. <br>
     * - Converting createOrUpdateTask data transfer object into task object {@link TaskMapper#toTask(CreateOrUpdateTaskDTO)}.
     * - Converting created task object into task data transfer object {@link TaskMapper#toTaskDTO(Task)}.
     * @param createOrUpdateTaskDTO an object containing the necessary information to create a task. Must not be null.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     * @return {@link TaskDTO} - created task data transfer object
     */
    @Override
    @Transactional
    public TaskDTO createTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        log.info("Create task method was invoked");
        Task createdTask = taskRepository.save(taskMapper.toTask(createOrUpdateTaskDTO));
        TaskDTO taskDTO = taskMapper.toTaskDTO(createdTask);
        log.info("Task {} was created successfully", taskDTO);
        return taskDTO;
    }

    /** Getting task by id.<br>
     * - Search for a task by id {@link TaskRepository#findById(Object)}.<br>
     * - Converting found task object into task data transfer object {@link TaskMapper#toTaskDTO(Task)}.
     * @param id task identification number in database
     * @throws TaskNotFoundException if task object was not found
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     * @return {@link TaskDTO} - found task data transfer object
     */
    @Override
    @Transactional(readOnly = true)
    public TaskDTO findTaskById(Long id) {
        log.info("Find task by id = {} method was invoked", id);
        Task foundTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        log.info("Task with id = {} was successfully found", id);
        return taskMapper.toTaskDTO(foundTask);
    }

    /** Getting all tasks pageable.<br>
     * - Search for all tasks pageable {@link TaskRepository#findAll(Example, Pageable)}.<br>
     * - Converting all task list into task data transfer object list {@link TaskMapper#toTaskDTOList(List)}.
     * @param pageNumber page number
     * @param pageSize page size number
     * @return all task object list
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAllTasks(Integer pageNumber, Integer pageSize) {
        log.info("Find all tasks method was invoked");
        if (pageSize > 50 || pageSize <= 0) {
            pageSize = 50;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Task> tasks = taskRepository.findAll(pageRequest).getContent();
        log.info("All tasks were successfully found");
        return taskMapper.toTaskDTOList(tasks);
    }

    /**
     * Updating a task object and saving it to a database.<br>
     * - Search for a task by id {@link TaskRepository#findById(Object)}.<br>
     * - Converting updated task data transfer object into task object {@link TaskMapper#updateTask(CreateOrUpdateTaskDTO, Task)}.<br>
     * - The repository method is used {@link TaskRepository#save(Object)}.<br>
     * - Converting updated task object into task data transfer object {@link TaskMapper#toTaskDTO(Task)}.<br>
     * @param id task identification number in database
     * @param createOrUpdateTaskDTO an object containing the necessary information to update a task. Must not be null.
     * @throws TaskNotFoundException if task object was not found
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     * @return {@link TaskDTO} - updated task data transfer object
     */
    @Override
    @Transactional
    public TaskDTO updateTask(Long id, CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        log.info("Update task method was invoked");
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskMapper.updateTask(createOrUpdateTaskDTO, task);
        task = taskRepository.save(task);
        TaskDTO taskDTO = taskMapper.toTaskDTO(task);
        log.info("Task {} was updated successfully", taskDTO);
        return taskDTO;
    }

    /**
     * Deleting task by id.<br>
     * - Deleting a task by id from the database {@link TaskRepository#deleteById(Object)}.<br>
     * @param id task identification number in database
     */
    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        log.info("Delete task by id = {} method was invoked", id);
        taskRepository.deleteById(id);
        log.info("Task with id = {} was deleted successfully", id);
    }
}
