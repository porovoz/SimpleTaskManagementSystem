package com.bestapp.SimpleTaskManagementSystem.dto;

import com.bestapp.SimpleTaskManagementSystem.model.Completed;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CreateOrUpdateTaskDTO {

    @Size(max = 64)
    private String title;

    @Size(max = 255)
    private String description;

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss")
    @Schema(pattern = "yyyy-MM-dd'T'hh:mm:ss", example = "2024-04-01'T'11:00:00")
    private LocalDateTime dueDate;
    
    @Enumerated(EnumType.STRING)
    private Completed completed;
}
