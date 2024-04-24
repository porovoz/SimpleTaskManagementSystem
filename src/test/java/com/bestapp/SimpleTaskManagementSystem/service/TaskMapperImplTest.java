package com.bestapp.SimpleTaskManagementSystem.service;

import com.bestapp.SimpleTaskManagementSystem.dto.CreateOrUpdateTaskDTO;
import com.bestapp.SimpleTaskManagementSystem.dto.TaskDTO;
import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import com.bestapp.SimpleTaskManagementSystem.model.Task;
import com.bestapp.SimpleTaskManagementSystem.service.impl.TaskMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A class for checking methods of TaskMapperImpl class
 * @see TaskMapperImpl
 */
@ExtendWith(MockitoExtension.class)
class TaskMapperImplTest {

    @InjectMocks
    private TaskMapperImpl taskMapper;

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

    /**
     * Checking <b>toTaskDTO()</b> method of TaskMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to task DTO method")
    void shouldProperlyMapTaskToTaskDTO() {

        TaskDTO taskDTO = taskMapper.toTaskDTO(expected);

        assertNotNull(taskDTO);
        assertEquals(expected.getId(), taskDTO.getId());
        assertEquals(expected.getTitle(), taskDTO.getTitle());
        assertEquals(expected.getDescription(), taskDTO.getDescription());
        assertEquals(expected.getDueDate(), taskDTO.getDueDate());
        assertEquals(expected.getCompleted(), taskDTO.getCompleted());
    }

    /**
     * Checking <b>toTask()</b> method of TaskMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to task method")
    void shouldProperlyMapCreateOrUpdateTaskDTOToTask() {

        Task task = taskMapper.toTask(createOrUpdateTaskDTO);

        assertNotNull(task);
        assertEquals(createOrUpdateTaskDTO.getTitle(), task.getTitle());
        assertEquals(createOrUpdateTaskDTO.getDescription(), task.getDescription());
        assertEquals(createOrUpdateTaskDTO.getDueDate(), task.getDueDate());
        assertEquals(createOrUpdateTaskDTO.getCompleted(), task.getCompleted());
    }

    /**
     * Checking <b>updateTask()</b> method of TaskMapperImpl class<br>
     */
    @Test
    @DisplayName("Check update task method")
    void shouldProperlyMapCreateOrUpdateDTOAndTaskToTask() {

        taskMapper.updateTask(createOrUpdateTaskDTO, expected);

        assertEquals(expected.getTitle(), createOrUpdateTaskDTO.getTitle());
        assertEquals(expected.getDescription(), createOrUpdateTaskDTO.getDescription());
        assertEquals(expected.getDueDate(), createOrUpdateTaskDTO.getDueDate());
        assertEquals(expected.getCompleted(), createOrUpdateTaskDTO.getCompleted());
    }

    /**
     * Checking <b>toTaskDTOList()</b> method of TaskMapperImpl class<br>
     */
    @Test
    @DisplayName("Check to task DTO list method")
    void shouldProperlyMapTaskListToTaskDTOList() {

        List<Task> expectedTasks = List.of(expected, expected2, expected3);

        List<TaskDTO> actualTasks = taskMapper.toTaskDTOList(expectedTasks);

        Task expectedTask = expectedTasks.stream().findFirst().orElse(new Task());
        TaskDTO actualTask = actualTasks.stream().findFirst().orElse(new TaskDTO());

        assertEquals(expectedTasks.size(), actualTasks.size());

        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getTitle(), actualTask.getTitle());
        assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        assertEquals(expectedTask.getDueDate(), actualTask.getDueDate());
        assertEquals(expectedTask.getCompleted(), actualTask.getCompleted());
    }
}
