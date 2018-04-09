package org.forum.api.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageMultiple {

	private LocalDateTime timestamp;
	private int errorCode;
	private Map<String,String> errorMessageMap;
	
}
