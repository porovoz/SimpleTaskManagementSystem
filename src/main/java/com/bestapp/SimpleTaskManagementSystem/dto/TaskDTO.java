package com.bestapp.SimpleTaskManagementSystem.dto;

import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Completed completed;
}
