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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDTO createTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        log.info("Create task method was invoked");
        Task createdTask = taskRepository.save(taskMapper.toTask(createOrUpdateTaskDTO));
        TaskDTO taskDTO = taskMapper.toTaskDTO(createdTask);
        log.info("Task {} was created successfully", taskDTO);
        return taskDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO findTaskById(Long id) {
        log.info("Find task by id = {} method was invoked", id);
        Task foundTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        log.info("Task with id = {} was successfully found", id);
        return taskMapper.toTaskDTO(foundTask);
    }

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

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        log.info("Delete task by id = {} method was invoked", id);
        taskRepository.deleteById(id);
        log.info("Task with id = {} was deleted successfully", id);
    }
}
