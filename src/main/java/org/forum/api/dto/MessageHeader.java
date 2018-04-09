package org.forum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data model class representing sub-model of {@link Message} model with message
 * {@code id} and {@code header} field.
 */
@Data
@AllArgsConstructor
public class MessageHeader {

	/** Message Id. */
	private long id;

	/** Message header. */
	private String header;

}
