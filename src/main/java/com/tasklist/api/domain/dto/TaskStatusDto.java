package com.tasklist.api.domain.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TaskStatusDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;

}
