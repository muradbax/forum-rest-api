package org.forum.api.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

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
	private Message messageEmptyInputDummy;
	private Message messageNonExistentIdDummy;

	private DataNotFoundException dataNotFoundExceptionDummy;
	private EmptyInputException emptyInputExceptionDummy;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	@Before
	public void setup() {
		message1Dummy = TestUtility.messageBuilder(TestUtility.ID_1, TestUtility.HEADER_1, TestUtility.BODY_1);
		message2Dummy = TestUtility.messageBuilder(TestUtility.ID_2, TestUtility.HEADER_2, TestUtility.BODY_2);
		messageEmptyInputDummy = TestUtility.messageBuilder(TestUtility.ID_ANY, TestUtility.HEADER_EMPTY,
				TestUtility.BODY_EMPTY);
		messageNonExistentIdDummy = TestUtility.messageBuilder(TestUtility.ID_INVALID, TestUtility.HEADER_NON_EMPTY,
				TestUtility.BODY_NON_EMPTY);

		dataNotFoundExceptionDummy = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageNonExistentIdDummy.getId()));
		emptyInputExceptionDummy = new EmptyInputException(
				ErrorUtility.getEmptyInputExceptionMessage(messageEmptyInputDummy));
	}

	@Test
	public void testGetHeaderList() throws Exception {
		List<MessageHeader> messageHeaderListDummy = Arrays.asList(
				new MessageHeader(message1Dummy.getId(), message1Dummy.getHeader()),
				new MessageHeader(message2Dummy.getId(), message2Dummy.getHeader()));

		when(messageService.getMessageHeaderList()).thenReturn(messageHeaderListDummy);

		mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(messageHeaderListDummy.size())))
				.andExpect(jsonPath("$[0].id", is(messageHeaderListDummy.get(0).getId().intValue())))
				.andExpect(jsonPath("$[0].header", is(messageHeaderListDummy.get(0).getHeader())))
				.andExpect(jsonPath("$[1].id", is(messageHeaderListDummy.get(1).getId().intValue())))
				.andExpect(jsonPath("$[1].header", is(messageHeaderListDummy.get(1).getHeader())));
	}

	@Test
	public void testGetBodyById() throws Exception {
		MessageBody messageBodyDummy = new MessageBody(message1Dummy.getBody());

		when(messageService.getMessageBodyById(message1Dummy.getId())).thenReturn(messageBodyDummy);

		mockMvc.perform(get(BASE_URL + message1Dummy.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.body", is(messageBodyDummy.getBody())));
	}

	@Test
	public void testGetBodyById_incorrectId() throws Exception {
		when(messageService.getMessageBodyById(messageNonExistentIdDummy.getId()))
				.thenThrow(dataNotFoundExceptionDummy);

		mockMvc.perform(get(BASE_URL + messageNonExistentIdDummy.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundExceptionDummy.getMessage())));
	}

	@Test
	public void testCreate() throws Exception {
		when(messageService.createMessage(message1Dummy)).thenReturn(message1Dummy);

		mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(message1Dummy))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(message1Dummy.getId().intValue())))
				.andExpect(jsonPath("$.header", is(message1Dummy.getHeader())))
				.andExpect(jsonPath("$.body", is(message1Dummy.getBody())));
	}

	@Test
	public void testCreate_emptyParameter() throws Exception {
		when(messageService.createMessage(messageEmptyInputDummy)).thenThrow(emptyInputExceptionDummy);

		mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(messageEmptyInputDummy))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputExceptionDummy.getMessageMap())));
	}

	@Test
	public void testUpdateById() throws Exception {
		when(messageService.updateMessageById(message1Dummy.getId(), message1Dummy)).thenReturn(message1Dummy);

		mockMvc.perform(put(BASE_URL + message1Dummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(message1Dummy))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(message1Dummy.getId().intValue())))
				.andExpect(jsonPath("$.header", is(message1Dummy.getHeader())))
				.andExpect(jsonPath("$.body", is(message1Dummy.getBody())));
	}

	@Test
	public void testUpdateById_incorrectId() throws Exception {
		when(messageService.updateMessageById(messageNonExistentIdDummy.getId(), messageNonExistentIdDummy))
				.thenThrow(dataNotFoundExceptionDummy);

		mockMvc.perform(put(BASE_URL + messageNonExistentIdDummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(messageNonExistentIdDummy))).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundExceptionDummy.getMessage())));
	}

	@Test
	public void testUpdateById_emptyParameters() throws Exception {
		when(messageService.updateMessageById(messageEmptyInputDummy.getId(), messageEmptyInputDummy))
				.thenThrow(emptyInputExceptionDummy);

		mockMvc.perform(put(BASE_URL + messageEmptyInputDummy.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtility.convertToJson(messageEmptyInputDummy))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessageMap", is(emptyInputExceptionDummy.getMessageMap())));
	}

	@Test
	public void testDeleteById() throws Exception {
		mockMvc.perform(delete(BASE_URL + message1Dummy.getId())).andExpect(status().isOk());

		verify(messageService, times(1)).deleteMessageById(message1Dummy.getId());
	}

	@Test
	public void testDeleteById_incorrectId() throws Exception {
		doThrow(dataNotFoundExceptionDummy).when(messageService).deleteMessageById(messageNonExistentIdDummy.getId());

		mockMvc.perform(delete(BASE_URL + messageNonExistentIdDummy.getId())).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorMessage", is(dataNotFoundExceptionDummy.getMessage())));
	}

}
