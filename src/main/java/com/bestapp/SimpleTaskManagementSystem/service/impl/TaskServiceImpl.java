package com.bestapp.SimpleTaskManagementSystem.service.impl;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.exception.TaskNotFoundException;
import com.bestapp.SimpleTaskManagementSystem.model.Task;
import com.bestapp.SimpleTaskManagementSystem.repository.TaskRepository;
import com.bestapp.SimpleTaskManagementSystem.service.TaskMapper;
import com.bestapp.SimpleTaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDTO createTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        Task createdTask = taskRepository.save(taskMapper.toTask(createOrUpdateTaskDTO));
        return taskMapper.toTaskDTO(createdTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO findTaskById(Long id) {
        Task foundTask = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return taskMapper.toTaskDTO(foundTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAllTasks(Integer pageNumber, Integer pageSize) {
        if (pageSize > 50 || pageSize <= 0) {
            pageSize = 50;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        List<Task> tasks = taskRepository.findAll(pageRequest).getContent();
        return taskMapper.toTaskDTOList(tasks);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(Long id, CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        taskMapper.updateTask(createOrUpdateTaskDTO, task);
        task = taskRepository.save(task);
        return taskMapper.toTaskDTO(task);
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
