package org.forum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model class representing sub-model of {@link Message} model with message
 * {@code id} and {@code header} field.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageHeader {

	/** Message Id. */
	private long id;

	/** Message header. */
	private String header;

}
