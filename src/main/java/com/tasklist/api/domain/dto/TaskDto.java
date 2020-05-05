package com.tasklist.api.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "The title is required")
	@Length(min = 3, max = 120, message = "The length must be between 3 and 120 characters.")
	private String title;

	private Integer status;

	private String description;

}
