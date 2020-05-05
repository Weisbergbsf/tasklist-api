package com.tasklist.api.domain.enuns;

public enum StatusEnum {

	TO_DO(1, "TO DO"), DOING(2, "DOING"), DONE(3, "DONE");

	private Integer id;
	private String description;

	private StatusEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static StatusEnum toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (StatusEnum sn : StatusEnum.values()) {
			if (id.equals(sn.getId())) {
				return sn;
			}
		}

		throw new IllegalArgumentException("Id invalid: " + id);
	}

}
