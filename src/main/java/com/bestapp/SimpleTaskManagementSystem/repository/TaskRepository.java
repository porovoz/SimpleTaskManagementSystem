package com.bestapp.SimpleTaskManagementSystem.repository;

import com.bestapp.SimpleTaskManagementSystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}