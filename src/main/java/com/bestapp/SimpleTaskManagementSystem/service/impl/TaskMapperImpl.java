package com.bestapp.SimpleTaskManagementSystem.service.impl;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.model.Task;
import com.bestapp.SimpleTaskManagementSystem.service.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCompleted(task.getCompleted());
        return taskDTO;
    }

    @Override
    public Task toTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO) {
        Task task = new Task();
        task.setTitle(createOrUpdateTaskDTO.getTitle());
        task.setDescription(createOrUpdateTaskDTO.getDescription());
        task.setDueDate(createOrUpdateTaskDTO.getDueDate());
        task.setCompleted(createOrUpdateTaskDTO.getCompleted());
        return task;
    }

    @Override
    public void updateTask(CreateOrUpdateTaskDTO createOrUpdateTaskDTO, Task task) {
        task.setTitle(createOrUpdateTaskDTO.getTitle());
        task.setDescription(createOrUpdateTaskDTO.getDescription());
        task.setDueDate(createOrUpdateTaskDTO.getDueDate());
        task.setCompleted(createOrUpdateTaskDTO.getCompleted());
    }

    @Override
    public List<TaskDTO> toTaskDTOList(List<Task> tasks) {
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task : tasks) {
            TaskDTO taskDTO = toTaskDTO(task);
            taskDTOS.add(taskDTO);
        }
        return taskDTOS;
    }
}
