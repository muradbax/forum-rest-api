package org.forum.api.commons;

import java.io.IOException;

import org.forum.api.dto.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtility {
	
	public static final Long ID_1 = 1L;
	public static final String HEADER_1 = "Language?";
	public static final String BODY_1 = "Java";

	public static final Long ID_2 = 2L;
	public static final String HEADER_2 = "Database?";
	public static final String BODY_2 = "MySQL";
	
	public static final Long ID_ANY = 100L;
	public static final String BODY_EMPTY = "";
	public static final String HEADER_EMPTY = "";

	public static final Long ID_INVALID = 0L;
	public static final String BODY_NON_EMPTY = "Body";
	public static final String HEADER_NON_EMPTY = "Header";
	
	public static Message messageBuilder(Long id, String header, String body) {
		Message message = new Message();
		message.setId(id);
		message.setHeader(header);
		message.setBody(body);

		return message;
	}
	
	public static byte[] convertToJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}
