package com.bestapp.SimpleTaskManagementSystem.dto;

import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    @Schema(pattern = "yyyy-MM-dd'T'hh:mm:ss", example = "2024-04-01'T'11:00:00")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Completed completed;
}
