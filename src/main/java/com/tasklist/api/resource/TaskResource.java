package com.tasklist.api.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasklist.api.domain.Task;
import com.tasklist.api.domain.dto.TaskDto;
import com.tasklist.api.domain.dto.TaskStatusDto;
import com.tasklist.api.resource.payload.ApiResponse;
import com.tasklist.api.service.TaskService;
import com.tasklist.api.service.exceptions.BadRequestException;
import com.tasklist.api.utils.PagedResult;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {

	@Autowired
	private TaskService service;

	@ApiOperation(value = "Find Task by Id")
	@GetMapping("/{id}")
	public ResponseEntity<Task> find(@PathVariable Integer id) {
		Task obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@ApiOperation(value = "Task List")
	@GetMapping
	public ResponseEntity<?> findPage(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "orderBy", defaultValue = "title") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Task> page = service.findPage(offset, limit, orderBy, direction.toUpperCase());
		PagedResult<Task> result = new PagedResult<Task>(page.getContent(), page.getTotalElements(), page.getNumber(),
				page.getSize());

		return ResponseEntity.ok().body(result);
	}

	@ApiOperation(value = "Create Task")
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody TaskDto objDto) {
		Task task = new Task();
		try {
			task = service.fromDTO(objDto);

		} catch (IllegalArgumentException e) {
			System.err.println("IllegalArgumentException: " + e.getMessage());
			throw new BadRequestException(e.getMessage());
		}

		task = service.insert(task);
		return ResponseEntity.ok().body(new ApiResponse(true, task));
	}

	@ApiOperation(value = "Update Task")
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TaskDto objDto, @PathVariable Integer id) {
		Task task = new Task();
		try {
			task = service.fromDTO(objDto);
			task.setId(id);
			task = service.updateTask(task);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(e.getMessage());
		}

		return ResponseEntity.ok().body(new ApiResponse(true, task));
	}

	@ApiOperation(value = "Update Status Task")
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateStatus(@Valid @RequestBody TaskStatusDto objDto, @PathVariable Integer id) {
		Task task = new Task();
		try {
			task = service.fromDTO(objDto);
			task.setId(id);
			task = service.updateStatusTask(task);
		} catch (IllegalArgumentException e) {
			throw new BadRequestException(e.getMessage());
		}
		return ResponseEntity.ok().body(new ApiResponse(true, task));
	}

	@ApiOperation(value = "Deleta Task")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().body(new ApiResponse(true, "Task successfully deleted!"));
	}
}
