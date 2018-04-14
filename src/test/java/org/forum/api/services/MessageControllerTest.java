package org.forum.api.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.Arrays;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.controller.MessageController;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.exception.EmptyInputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

	private static final String BASE_URL = "/api/message/";

	private Message messageGlobal1;
	private Message messageGlobal2;
	private Message messageGlobalEmpty;

	private static final Long ID_LONG_1 = 1L;
	private static final String HEADER_1 = "Language?";
	private static final String BODY_1 = "Java";

	private static final Long ID_LONG_2 = 2L;
	private static final String HEADER_2 = "Database?";
	private static final String BODY_2 = "MySQL";

	private static final Long ID_LONG_3 = 3L;

	@Before
	public void setup() {
		messageGlobal1 = messageBuilder(ID_LONG_1, HEADER_1, BODY_1);
		messageGlobal2 = messageBuilder(ID_LONG_2, HEADER_2, BODY_2);
		messageGlobalEmpty = messageBuilder(ID_LONG_3, "", "");
	}

	@MockBean
	private MessageService messageService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetHeaderList() throws Exception {
		MessageHeader messageHeader1 = new MessageHeader(messageGlobal1.getId(), messageGlobal1.getHeader());
		MessageHeader messageHeader2 = new MessageHeader(messageGlobal2.getId(), messageGlobal2.getHeader());

		when(messageService.getMessageHeaderList()).thenReturn(Arrays.asList(messageHeader1, messageHeader2));

		mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(messageGlobal1.getId().intValue())))
				.andExpect(jsonPath("$[0].header", is(messageGlobal1.getHeader())))
				.andExpect(jsonPath("$[1].id", is(messageGlobal2.getId().intValue())))
				.andExpect(jsonPath("$[1].header", is(messageGlobal2.getHeader())));
	}

	@Test
	public void testGetBodyById() throws Exception {
		Long messageId = messageGlobal1.getId();
		MessageBody messageBody = new MessageBody(messageGlobal1.getBody());
		
		when(messageService.getMessageBodyById(messageId)).thenReturn(messageBody);

		mockMvc.perform(get(BASE_URL + messageId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.body", is(messageGlobal1.getBody())));
	}

	@Test
	public void testGetBodyById_incorrectId() throws Exception {
		Long messageId = messageGlobal1.getId();
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageId));

		when(messageService.getMessageBodyById(anyLong())).thenThrow(dataNotFoundException);

		mockMvc.perform(get(BASE_URL + messageId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));
	}

	@Test
	public void testCreateMessage() throws Exception {
		Message message = messageGlobal1;
		
		when(messageService.createMessage(message)).thenReturn(message);

		mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(convertToJson(message)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(messageGlobal1.getId().intValue())))
				.andExpect(jsonPath("$.header", is(messageGlobal1.getHeader())))
				.andExpect(jsonPath("$.body", is(messageGlobal1.getBody())));
	}

	@Test
	public void testCreateMessage_emptyParameter() throws Exception {
		Message message = messageGlobalEmpty;

		EmptyInputException emptyInputException = null;
		try {
			ErrorUtility.isEmptyFields(message);
		} catch (EmptyInputException exception) {
			emptyInputException = exception;
		}

		when(messageService.createMessage(message)).thenThrow(emptyInputException);

		mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(convertToJson(message)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputException.getMessageMap())));
	}

	@Test
	public void testUpdateMessageById() throws Exception {
		Message message = messageGlobal1;
		
		when(messageService.updateMessageById(message.getId(), message)).thenReturn(message);

		mockMvc.perform(put(BASE_URL + message.getId()).contentType(MediaType.APPLICATION_JSON).content(convertToJson(message)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(messageGlobal1.getId().intValue())))
				.andExpect(jsonPath("$.header", is(messageGlobal1.getHeader())))
				.andExpect(jsonPath("$.body", is(messageGlobal1.getBody())));
	}

	@Test
	public void testUpdateMessageById_incorrectId() throws Exception {
		Message message = messageGlobal1;		
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(message.getId()));

		when(messageService.updateMessageById(message.getId(), message)).thenThrow(dataNotFoundException);

		mockMvc.perform(put(BASE_URL + message.getId()).contentType(MediaType.APPLICATION_JSON).content(convertToJson(message)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));
	}

	@Test
	public void testUpdateMessageById_emptyParameters() throws Exception {
		Message message = messageGlobalEmpty; 
		
		EmptyInputException emptyInputException = null;
		try {
			ErrorUtility.isEmptyFields(message);
		} catch (EmptyInputException exception) {
			emptyInputException = exception;
		}

		when(messageService.updateMessageById(message.getId(), message)).thenThrow(emptyInputException);

		mockMvc.perform(put(BASE_URL + message.getId()).contentType(MediaType.APPLICATION_JSON).content(convertToJson(message)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputException.getMessageMap())));
	}

	@Test
	public void testDeleteMessageById() throws Exception {
		Long messageId = messageGlobal1.getId();
		
		mockMvc.perform(delete(BASE_URL + messageId))
				.andExpect(status().isOk());
		
		verify(messageService).deleteMessageById(messageId);
	}
	
	@Test
	public void testDeleteMessageById_incorrectId() throws Exception {
		Long messageId = messageGlobal1.getId();
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageId));
		
		doThrow(dataNotFoundException).when(messageService).deleteMessageById(messageId);
		
		mockMvc.perform(delete(BASE_URL + messageId))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));
		
		verify(messageService).deleteMessageById(messageId);
	}

	private Message messageBuilder(Long id, String header, String body) {
		Message message = new Message();
		message.setId(id);
		message.setHeader(header);
		message.setBody(body);

		return message;
	}

	private byte[] convertToJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(object);
	}

}