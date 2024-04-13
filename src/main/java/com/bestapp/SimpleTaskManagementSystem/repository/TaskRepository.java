package com.bestapp.SimpleTaskManagementSystem.repository;

import com.bestapp.SimpleTaskManagementSystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface containing methods for working with a database of objects of the Task class
 * @see Task
 * @see com.bestapp.SimpleTaskManagementSystem.service.TaskService
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}