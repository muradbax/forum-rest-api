package org.forum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data model class representing sub-model of {@link Message} model with message
 * {@code body} field.
 */
@Data
@AllArgsConstructor
public class MessageBody {

	/** Message body. */
	private String body;

}
