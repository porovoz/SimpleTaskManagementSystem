package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.exception.TaskNotFoundException;
import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import com.bestapp.SimpleTaskManagementSystem.model.Task;
import com.bestapp.SimpleTaskManagementSystem.repository.TaskRepository;
import com.bestapp.SimpleTaskManagementSystem.service.impl.TaskMapperImpl;
import com.bestapp.SimpleTaskManagementSystem.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * A class for checking methods of TaskServiceImpl class
 * @see TaskServiceImpl
 * @see TaskRepository
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepositoryMock;

    @Spy
    private TaskMapperImpl taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private final Task expected = new Task();
    private final Task expected2 = new Task();
    private final Task expected3 = new Task();

    private final CreateOrUpdateTaskDTO createOrUpdateTaskDTO = new CreateOrUpdateTaskDTO();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setTitle("Test title");
        expected.setDescription("Test description");
        expected.setDueDate(LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS));
        expected.setCompleted(Completed.IN_PROCESS);

        expected2.setId(2L);
        expected2.setTitle("Test title");
        expected2.setDescription("Test description");
        expected2.setDueDate(LocalDateTime.now().plusDays(2).truncatedTo(ChronoUnit.HOURS));
        expected2.setCompleted(Completed.IN_PROCESS);

        expected3.setId(3L);
        expected3.setTitle("Test title");
        expected3.setDescription("Test description");
        expected3.setDueDate(LocalDateTime.now().plusDays(3).truncatedTo(ChronoUnit.HOURS));
        expected3.setCompleted(Completed.IN_PROCESS);

        createOrUpdateTaskDTO.setTitle("New title");
        createOrUpdateTaskDTO.setDescription("New description");
        createOrUpdateTaskDTO.setDueDate(LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS));
        createOrUpdateTaskDTO.setCompleted(Completed.IN_PROCESS);
    }

    @AfterEach
    public void clearDB() {
        taskRepositoryMock.deleteAll();
    }

    /**
     * Checking <b>createTask()</b> method of TaskServiceImpl class<br>
     * When the <b>TaskRepository::save()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @Test
    @DisplayName("Task creation and saving it to database check")
    void createTaskTest() {

        when(taskRepositoryMock.save(any(Task.class))).thenReturn(expected);

        TaskDTO actual = taskService.createTask(createOrUpdateTaskDTO);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.getCompleted(), actual.getCompleted());

        verify(taskRepositoryMock, times(1)).save(any(Task.class));
    }

    /**
     * Checking <b>findTaskById()</b> method of TaskServiceImpl class<br>
     * When the <b>TaskRepository::findById()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @Test
    @DisplayName("Checking the search for the task by id")
    void getTaskByIdTest() {
        Long id = 1L;

        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expected));

        TaskDTO actual = taskService.findTaskById(id);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.getCompleted(), actual.getCompleted());

        verify(taskRepositoryMock, times(1)).findById(anyLong());
    }

    /**
     * Checking for throwing an exception in the <b>findTaskById()</b> method of the TaskServiceImpl class<br>
     * When the <b>TaskRepository::findById()</b> method is called, an exception is thrown <b>TaskNotFoundException</b>
     */
    @Test
    @DisplayName("Checking for throwing an exception when searching for a task by id")
    void getTaskByIdNotFoundExceptionTest() {
        Long id = 10L;

        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById(id));

        verify(taskRepositoryMock, times(1)).findById(anyLong());
    }

    /**
     * Checking <b>findALLTasks()</b> method of TaskServiceImpl class<br>
     * When the <b>TaskRepository::findAll()</b> method is called, a collection of expected objects of the TaskDTO class is returned
     */
    @Test
    @DisplayName("Checking the search for a list of all tasks")
    void getAllTasksTest() {

        List<Task> expectedTasks = List.of(expected, expected2, expected3);

        when(taskRepositoryMock.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(expectedTasks));

        List<TaskDTO> actualTasks = taskService.findAllTasks(1, 5);

        Task expectedTask = expectedTasks.stream().findFirst().orElse(new Task());
        TaskDTO actualTask = actualTasks.stream().findFirst().orElse(new TaskDTO());

        assertEquals(expectedTasks.size(), actualTasks.size());

        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getTitle(), actualTask.getTitle());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getDueDate(), actualTask.getDueDate());
        assertEquals(expectedTask.getCompleted(), actualTask.getCompleted());
    }

    /**
     * Checking <b>updateTask()</b> method of TaskServiceImpl class<br>
     * When the <b>TaskRepository::findById()</b> method is called, the expected object of the TaskDTO class is returned
     * When the <b>TaskRepository::save()</b> method is called, the expected object of the TaskDTO class is returned
     */
    @Test
    @DisplayName("Checking for updates of the task data and adding it to the database")
    void updateTaskTest() {
        Long id = 1L;

        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expected));
        when(taskRepositoryMock.save(any(Task.class))).thenReturn(expected);

        TaskDTO actual = taskService.updateTask(id, createOrUpdateTaskDTO);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDueDate(), actual.getDueDate());
        assertEquals(expected.getCompleted(), actual.getCompleted());

        verify(taskRepositoryMock, times(1)).save(any(Task.class));
    }

    /**
     * Checking for throwing an exception in the <b>updateTask()</b> method of the TaskServiceImpl class<br>
     */
    @Test
    @DisplayName("Checking for throwing an exception when updating the task data and adding it to the database")
    void updateTaskNotFoundExceptionTest() {
        Long id = 10L;

        when(taskRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, createOrUpdateTaskDTO));

        verify(taskRepositoryMock, never()).save(any(Task.class));
    }

    /**
     * Checking <b>deleteTaskById()</b> method of TaskServiceImpl class<br>
     */
    @Test
    @DisplayName("Checking the deletion of the task by id")
    void deleteTaskByIdTest() {
        Long id = 1L;

        taskService.deleteTaskById(id);

        verify(taskRepositoryMock, times(1)).deleteById(id);
    }
}
