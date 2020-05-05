package com.tasklist.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tasklist.api.domain.Task;
import com.tasklist.api.domain.dto.TaskDto;
import com.tasklist.api.domain.dto.TaskStatusDto;
import com.tasklist.api.domain.enuns.StatusEnum;
import com.tasklist.api.repository.TaskRepository;
import com.tasklist.api.service.exceptions.ObjectNotFoundException;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task findById(Integer id) {
		Optional<Task> obj = taskRepository.findById(id);

		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Task not found! Id: " + id + ", Type: " + Task.class.getName()));
	}

	public Page<Task> findPage(Integer offset, Integer limit, String orderBy, String direction) {
		direction.toUpperCase();
		PageRequest pageRequest = PageRequest.of(offset, limit, Direction.valueOf(direction), orderBy);

		return taskRepository.findAll(pageRequest);
	}

	@Transactional
	public Task insert(Task obj) {
		obj.setId(null);
		obj.setStatus(StatusEnum.TO_DO.getId());
		obj = taskRepository.save(obj);

		return obj;
	}

	public Task updateTask(Task obj) {
		Task newObj = findById(obj.getId());
		updateDataTask(newObj, obj);
		return taskRepository.save(newObj);
	}

	public Task updateStatusTask(Task obj) {
		Task newObj = findById(obj.getId());
		updateStatusTask(newObj, obj);
		return taskRepository.save(newObj);
	}

	public void delete(Integer id) {
		findById(id);
		taskRepository.deleteById(id);
	}

	private void updateDataTask(Task newObj, Task obj) {
		newObj.setTitle(obj.getTitle());
		newObj.setStatus(obj.getStatus());
		newObj.setDescription(obj.getDescription());
		newObj.setUpdateAt(LocalDateTime.now());

	}

	private void updateStatusTask(Task newObj, Task obj) {
		newObj.setStatus(obj.getStatus());
		if (obj.getStatus() == StatusEnum.TO_DO.getId() || obj.getStatus() == StatusEnum.DOING.getId()) {
			newObj.setUpdateAt(LocalDateTime.now());
		}
		if (obj.getStatus() == StatusEnum.DONE.getId()) {
			newObj.setDoneAt(LocalDateTime.now());
		}
	}

	public Task fromDTO(TaskDto objDto) {
		return new Task(objDto.getId(), objDto.getTitle(), StatusEnum.toEnum(objDto.getStatus()),
				objDto.getDescription(), LocalDateTime.now(), null, null);
	}

	public Task fromDTO(TaskStatusDto objDto) {
		return new Task(null, null, StatusEnum.toEnum(objDto.getStatus()), null, null, null, null);
	}

}
