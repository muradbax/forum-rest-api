package org.forum.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageSingle {
	
	private LocalDateTime timestamp;
	private int errorCode;
	private String errorMessage;
	
}
