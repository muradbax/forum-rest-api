package org.forum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageHeader {
	
	private long id;
	private String header;
	
}
