package com.sys.botica.crce.pe.sys_botica.param;

import java.util.Optional;
import lombok.Data;

@Data
public class CategoryParam {
	private String key_word;
	private Integer pageNumber;
	private Integer perPage;
	private Optional<String> sortField;
	private Optional<Integer> sortOrder;
}
