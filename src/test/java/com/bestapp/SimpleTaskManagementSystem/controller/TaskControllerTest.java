package com.bestapp.SimpleTaskManagementSystem.controller;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.exception.TaskNotFoundException;
import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import com.bestapp.SimpleTaskManagementSystem.service.impl.TaskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A class for checking methods of TaskController class
 * @see TaskServiceImpl
 */
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TaskServiceImpl taskService;

    private final TaskDTO taskDTO = new TaskDTO();
    private final TaskDTO taskDTO2 = new TaskDTO();
    private final TaskDTO taskDTO3 = new TaskDTO();

    private final CreateOrUpdateTaskDTO createOrUpdateTaskDTO = new CreateOrUpdateTaskDTO();

    @BeforeEach
    public void SetUp() {
        taskDTO.setId(1L);
        taskDTO.setTitle("Title");
        taskDTO.setDescription("Description");
        taskDTO.setDueDate(LocalDateTime.now().plusDays(1));
        taskDTO.setCompleted(Completed.IN_PROCESS);

        taskDTO2.setId(2L);
        taskDTO2.setTitle("Title");
        taskDTO2.setDescription("Description");
        taskDTO2.setDueDate(LocalDateTime.now().plusDays(2));
        taskDTO2.setCompleted(Completed.IN_PROCESS);

        taskDTO3.setId(3L);
        taskDTO3.setTitle("Title");
        taskDTO3.setDescription("Description");
        taskDTO3.setDueDate(LocalDateTime.now().plusDays(3));
        taskDTO3.setCompleted(Completed.IN_PROCESS);

        createOrUpdateTaskDTO.setTitle("New title");
        createOrUpdateTaskDTO.setDescription("New description");
        createOrUpdateTaskDTO.setDueDate(LocalDateTime.now().plusDays(1));
        createOrUpdateTaskDTO.setCompleted(Completed.IN_PROCESS);
    }

    /**
     * Checking <b>createTask()</b> method of TaskController class<br>
     * When the <b>TaskService::createTask()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Create task method check")
    void createTaskTest() {

        when(taskService.createTask(createOrUpdateTaskDTO)).thenReturn(taskDTO);

        mvc.perform(post("/tasks")
                        .content(objectMapper.writeValueAsString(createOrUpdateTaskDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(taskDTO.getDueDate().format(DATE_TIME_FORMATTER)))
                .andExpect(jsonPath("$.completed").value(taskDTO.getCompleted().toString()))
                .andDo(print());

        verify(taskService, times(1)).createTask(any(CreateOrUpdateTaskDTO.class));
    }

    /**
     * Checking <b>getTaskById()</b> method of TaskController class<br>
     * When the <b>TaskService::findTaskById()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Get task by id method check")
    void getTaskByIdTest() {

        when(taskService.findTaskById(anyLong())).thenReturn(taskDTO);

        mvc.perform(get( "/tasks/{id}", taskDTO.getId())
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(taskDTO.getDueDate().format(DATE_TIME_FORMATTER)))
                .andExpect(jsonPath("$.completed").value(taskDTO.getCompleted().toString()))
                .andDo(print());

        verify(taskService, times(1)).findTaskById(anyLong());
    }

    /**
     * Checking for throwing an exception in the <b>getTaskById()</b> method of TaskController class<br>
     * When the <b>TaskService::findTaskById()</b> method is called, <b>TaskNotFoundException</b> throws
     */
    @Test
    @SneakyThrows
    @DisplayName("Checking for throwing an exception in the get task by id method")
    void getTaskByIdExceptionTest() {
        Long id = 10L;
        when(taskService.findTaskById(anyLong())).thenThrow(TaskNotFoundException.class);
        mvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isNotFound());
    }

    /**
     * Checking <b>getAllTasks()</b> method of TaskController class<br>
     * When the <b>TaskService::findAllTasks()</b> method is called, the expected object list of the TaskDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Get all tasks method check")
    void getAllTasksTest() {
        List<TaskDTO> taskDTOList = List.of(taskDTO, taskDTO2, taskDTO3);

        when(taskService.findAllTasks(anyInt(), anyInt())).thenReturn(taskDTOList);

       mvc.perform(get("/tasks").param("pageNumber", "1").param("pageSize", "20")
                        .content(objectMapper.writeValueAsString(taskDTOList))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(taskDTOList.size())))
               .andDo(print());

        verify(taskService, times(1)).findAllTasks(anyInt(), anyInt());
    }

    /**
     * Checking <b>updateTask()</b> method of TaskController class<br>
     * When the <b>TaskService::updateTask()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @SneakyThrows
    @Test
    @DisplayName("Update task method check")
    void updateTaskTest() {

        when(taskService.updateTask(anyLong(), eq(createOrUpdateTaskDTO))).thenReturn(taskDTO);

        mvc.perform(put("/tasks/{id}", taskDTO.getId())
                        .content(objectMapper.writeValueAsString(createOrUpdateTaskDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskDTO.getId()))
                .andExpect(jsonPath("$.title").value(taskDTO.getTitle()))
                .andExpect(jsonPath("$.description").value(taskDTO.getDescription()))
                .andExpect(jsonPath("$.dueDate").value(taskDTO.getDueDate().format(DATE_TIME_FORMATTER)))
                .andExpect(jsonPath("$.completed").value(taskDTO.getCompleted().toString()))
                .andDo(print());

        verify(taskService, times(1)).updateTask(anyLong(), any(CreateOrUpdateTaskDTO.class));
    }

    /**
     * Checking <b>deleteTaskById()</b> method of TaskController class<br>
     */
    @SneakyThrows
    @Test
    @DisplayName("Delete task by id method check")
    void deleteTaskByIdTest() {
        long id = 1L;

        mvc.perform(delete("/tasks/{id}", id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTaskById(anyLong());
    }

    /**
     * Checking <b>deleteTaskById()</b> method of TaskController class<br>
     * When the <b>TaskService::findTaskById()</b> method is called, <b>TaskNotFoundException</b> throws
     */
    @SneakyThrows
    @Test
    @DisplayName("Checking for throwing an exception in the delete task by id method")
    void deleteTaskByIdExceptionTest() {
        Long id = 10L;
        when(taskService.findTaskById(anyLong())).thenThrow(TaskNotFoundException.class);
        mvc.perform(delete("/tasks/{id}", id))
                .andExpect(status().isOk());
    }
}