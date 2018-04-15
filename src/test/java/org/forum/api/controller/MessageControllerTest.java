package org.forum.api.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.forum.api.commons.DummyData;
import org.forum.api.commons.ErrorUtility;
import org.forum.api.commons.TestUtility;
import org.forum.api.controller.MessageController;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.exception.EmptyInputException;
import org.forum.api.services.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

	private static final String BASE_URL = "/api/message/";

	private Message message1Dummy;
	private Message message2Dummy;
	private Message messageInvalidDummy;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	@Before
	public void setup() {
		message1Dummy = TestUtility.messageBuilderWithId(DummyData.ID_1_DUMMY, DummyData.HEADER_1_DUMMY,
				DummyData.BODY_1_DUMMY);
		message2Dummy = TestUtility.messageBuilderWithId(DummyData.ID_2_DUMMY, DummyData.HEADER_2_DUMMY,
				DummyData.BODY_2_DUMMY);
		messageInvalidDummy = TestUtility.messageBuilderWithId(DummyData.ID_INVALID, "", "");
	}

	@Test
	public void testGetHeaderList() throws Exception {
		MessageHeader messageHeader1 = new MessageHeader(message1Dummy.getId(), message1Dummy.getHeader());
		MessageHeader messageHeader2 = new MessageHeader(message2Dummy.getId(), message2Dummy.getHeader());

		when(messageService.getMessageHeaderList()).thenReturn(Arrays.asList(messageHeader1, messageHeader2));

		mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(message1Dummy.getId().intValue())))
				.andExpect(jsonPath("$[0].header", is(message1Dummy.getHeader())))
				.andExpect(jsonPath("$[1].id", is(message2Dummy.getId().intValue())))
				.andExpect(jsonPath("$[1].header", is(message2Dummy.getHeader())));
	}

	@Test
	public void testGetBodyById() throws Exception {
		Long messageId = message1Dummy.getId();
		MessageBody messageBody = new MessageBody(message1Dummy.getBody());

		when(messageService.getMessageBodyById(messageId)).thenReturn(messageBody);

		mockMvc.perform(get(BASE_URL + messageId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is(message1Dummy.getBody())));
	}

	@Test
	public void testGetBodyById_incorrectId() throws Exception {
		Long messageId = messageInvalidDummy.getId();
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageId));

		when(messageService.getMessageBodyById(anyLong())).thenThrow(dataNotFoundException);

		mockMvc.perform(get(BASE_URL + messageId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));
	}

	@Test
	public void testCreateMessage() throws Exception {
		when(messageService.createMessage(message1Dummy)).thenReturn(message1Dummy);

		mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtility.convertToJson(message1Dummy)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(message1Dummy.getId().intValue())))
				.andExpect(jsonPath("$.header", is(message1Dummy.getHeader())))
				.andExpect(jsonPath("$.body", is(message1Dummy.getBody())));
	}

	@Test
	public void testCreateMessage_emptyParameter() throws Exception {		
		EmptyInputException emptyInputException = null;
		try {
			ErrorUtility.isEmptyFields(messageInvalidDummy);
		} catch (EmptyInputException exception) {
			emptyInputException = exception;
		}

		when(messageService.createMessage(messageInvalidDummy)).thenThrow(emptyInputException);

		mockMvc.perform(
				post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtility.convertToJson(messageInvalidDummy)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputException.getMessageMap())));
	}

	@Test
	public void testUpdateMessageById() throws Exception {
		when(messageService.updateMessageById(message1Dummy.getId(), message1Dummy)).thenReturn(message1Dummy);

		mockMvc.perform(put(BASE_URL + message1Dummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(message1Dummy))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(message1Dummy.getId().intValue())))
				.andExpect(jsonPath("$.header", is(message1Dummy.getHeader())))
				.andExpect(jsonPath("$.body", is(message1Dummy.getBody())));
	}

	@Test
	public void testUpdateMessageById_incorrectId() throws Exception {
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageInvalidDummy.getId()));

		when(messageService.updateMessageById(messageInvalidDummy.getId(), messageInvalidDummy)).thenThrow(dataNotFoundException);

		mockMvc.perform(put(BASE_URL + messageInvalidDummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(messageInvalidDummy)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));
	}

	@Test
	public void testUpdateMessageById_emptyParameters() throws Exception {		
		EmptyInputException emptyInputException = null;
		
		try {
			ErrorUtility.isEmptyFields(messageInvalidDummy);
		} catch (EmptyInputException exception) {
			emptyInputException = exception;
		}

		when(messageService.updateMessageById(messageInvalidDummy.getId(), messageInvalidDummy)).thenThrow(emptyInputException);

		mockMvc.perform(put(BASE_URL + messageInvalidDummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(messageInvalidDummy)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputException.getMessageMap())));
	}

	@Test
	public void testDeleteMessageById() throws Exception {
		Long messageId = message1Dummy.getId();

		mockMvc.perform(delete(BASE_URL + messageId))
				.andExpect(status().isOk());

		verify(messageService).deleteMessageById(messageId);
	}

	@Test
	public void testDeleteMessageById_incorrectId() throws Exception {
		Long messageId = messageInvalidDummy.getId();
		DataNotFoundException dataNotFoundException = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageId));

		doThrow(dataNotFoundException).when(messageService).deleteMessageById(messageId);

		mockMvc.perform(delete(BASE_URL + messageId)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundException.getMessage())));

		verify(messageService).deleteMessageById(messageId);
	}

}
