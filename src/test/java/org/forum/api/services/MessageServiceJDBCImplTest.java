package org.forum.api.services;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.exception.EmptyInputException;
import org.forum.api.jdbc.dao.MessageDAO;
import org.forum.api.services.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MessageServiceJDBCImplTest extends MessageServiceTest {

	@MockBean
	private MessageDAO messageDAO;

	@TestConfiguration
	static class MessageServiceTestContextConfiguration {

		@Bean
		public MessageService messageService() {
			return new MessageServiceJDBCImpl();
		}

	}

	// TODO go through all asserts

	@Test
	public void testGetMessageHeaderList() throws Exception {
		// given
		when(messageDAO.getMessageHeaderList()).thenReturn(messageHeaderListDummy);

		// when
		List<MessageHeader> messageHeaderListReturn = messagesService.getMessageHeaderList();

		// then
		assertThat(messageHeaderListReturn, hasSize(messageHeaderListDummy.size()));
	}

	@Test
	public void testGetMessageBodyById() throws Exception {
		// given
		when(messageDAO.getMessageBodyById(message1Dummy.getId())).thenReturn(messageBodyDummy);

		// when
		MessageBody messageBodyReturn = messagesService.getMessageBodyById(message1Dummy.getId());

		// then
		assertThat(messageBodyReturn.getBody(), is(messageBodyDummy.getBody()));
	}

	@Test
	public void testGetMessageBodyById_nonExistentId() throws Exception {
		// given
		when(messageDAO.getMessageBodyById(messageNonExistentIdDummy.getId())).thenThrow(dataNotFoundExceptionDummy);

		// when
		try {
			messagesService.getMessageBodyById(messageNonExistentIdDummy.getId());
		} catch (DataNotFoundException dataNotFoundExceptionReturn) {
			// then
			assertThat(dataNotFoundExceptionReturn.getMessage(), is(dataNotFoundExceptionDummy.getMessage()));
		}
	}

	@Test
	public void testCreateMessage() throws Exception {
		// given
		when(messageDAO.createMessage(message1Dummy)).thenReturn(message1Dummy);

		// when
		Message messageReturn = messagesService.createMessage(message1Dummy);

		// then
		assertThat(messageReturn.getId(), is(message1Dummy.getId()));
		assertThat(messageReturn.getHeader(), is(message1Dummy.getHeader()));
		assertThat(messageReturn.getBody(), is(message1Dummy.getBody()));
	}

	@Test
	public void testCreateMessage_emptyParameter() throws Exception {
		// when
		try {
			messagesService.createMessage(messageEmptyInputDummy);
		} catch (EmptyInputException emptyInputExceptionReturn) {
			// then
			assertThat(emptyInputExceptionReturn.getMessageMap(), is(emptyInputExceptionDummy.getMessageMap()));
		}

		verifyZeroInteractions(messageDAO);
	}

	@Test
	public void testUpdateMessageById() throws Exception {
		// given
		when(messageDAO.updateMessageById(message1Dummy.getId(), message1Dummy)).thenReturn(message1Dummy);

		// when
		Message messageReturn = messagesService.updateMessageById(message1Dummy.getId(), message1Dummy);

		// then
		assertThat(messageReturn.getId(), is(message1Dummy.getId()));
		assertThat(messageReturn.getHeader(), is(message1Dummy.getHeader()));
		assertThat(messageReturn.getBody(), is(message1Dummy.getBody()));
	}

	@Test
	public void testUpdateMessageById_nonExistentId() throws Exception {
		// given
		when(messageDAO.updateMessageById(message1Dummy.getId(), message1Dummy)).thenThrow(dataNotFoundExceptionDummy);

		// when
		try {
			messagesService.updateMessageById(messageNonExistentIdDummy.getId(), messageNonExistentIdDummy);
		} catch (DataNotFoundException dataNotFoundExceptionReturn) {
			// then
			assertThat(dataNotFoundExceptionReturn.getMessage(), is(dataNotFoundExceptionDummy.getMessage()));
		}
	}

	@Test
	public void testUpdateMessageById_emptyParameters() throws Exception {
		// when
		try {
			messagesService.updateMessageById(messageEmptyInputDummy.getId(), messageEmptyInputDummy);
		} catch (EmptyInputException emptyInputExceptionReturn) {
			// then
			assertThat(emptyInputExceptionReturn.getMessageMap(), is(emptyInputExceptionDummy.getMessageMap()));
		}

		verifyZeroInteractions(messageDAO);
	}

	@Test
	public void testDeleteMessageById() throws Exception {
		// when
		messagesService.deleteMessageById(message1Dummy.getId());

		// then
		verify(messageDAO, times(1)).deleteMessageById(message1Dummy.getId());
	}

	@Test
	public void testDeleteMessageById_nonExistentId() throws Exception {
		// given
		doThrow(dataNotFoundExceptionDummy).when(messageDAO).deleteMessageById(messageNonExistentIdDummy.getId());

		// when
		try {
			messagesService.deleteMessageById(messageNonExistentIdDummy.getId());
		} catch (DataNotFoundException dataNotFoundExceptionReturn) {
			// then
			assertThat(dataNotFoundExceptionReturn.getMessage(), is(dataNotFoundExceptionDummy.getMessage()));
		}
	}

}
