package org.forum.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data model class representing error message with string.
 */
@Data
@AllArgsConstructor
public class ErrorMessageSingle {
	
	/** Time stamp of error. */
	private LocalDateTime timestamp;
	
	/** Error code HTTP. */
	private int errorCode;
	
	/** Error message string. */
	private String errorMessage;
	
}
