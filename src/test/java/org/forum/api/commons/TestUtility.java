package org.forum.api.commons;

import java.io.IOException;

import org.forum.api.dto.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtility {	
	
	public static Message messageBuilderWithId(Long id, String header, String body) {
		Message message = new Message();
		message.setId(id);
		message.setHeader(header);
		message.setBody(body);

		return message;
	}
	
	public static Message messageBuilderWithoutId(String header, String body) {
		Message message = new Message();
		message.setHeader(header);
		message.setBody(body);

		return message;
	}

	public static byte[] convertToJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
