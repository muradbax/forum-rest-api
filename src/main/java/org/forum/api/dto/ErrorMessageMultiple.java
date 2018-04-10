package org.forum.api.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data model class representing error message with map.
 */
@Data
@AllArgsConstructor
public class ErrorMessageMultiple {

	/** Time stamp of error. */
	private LocalDateTime timestamp;

	/** Error code HTTP. */
	private int errorCode;

	/** Error message map. */
	private Map<String, String> errorMessageMap;

}
