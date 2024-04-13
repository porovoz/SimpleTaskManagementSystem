package com.bestapp.SimpleTaskManagementSystem.exception;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException() {
        super("Task not found!");
    }
}
