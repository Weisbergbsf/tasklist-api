package com.tasklist.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tasklist.api.domain.enuns.StatusEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "tasks")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	@NotEmpty(message = "The title is required")
	@Length(min = 3, max = 120, message = "The length must be between 3 and 120 characters.")
	private String title;

	@Column(nullable = true)
	private Integer status;

	@Column(length = 200)
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime doneAt;

	public Task(Integer id, String title, StatusEnum status, String description, LocalDateTime createAt,
			LocalDateTime updateAt, LocalDateTime doneAt) {
		super();
		this.id = id;
		this.title = title;
		this.status = (status == null) ? StatusEnum.TO_DO.getId() : status.getId();
		this.description = description;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.doneAt = doneAt;
	}

}
