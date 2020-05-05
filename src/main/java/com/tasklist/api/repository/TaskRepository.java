package com.tasklist.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tasklist.api.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	Page<Task> findAll(Pageable pageable);
}
