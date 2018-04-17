package org.forum.api.services;

import java.util.Arrays;
import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.commons.TestUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.exception.EmptyInputException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MessageServiceTest {
	
	protected Message message1Dummy;
	protected Message message2Dummy;
	protected Message messageEmptyInputDummy;
	protected Message messageNonExistentIdDummy;
	
	protected List<MessageHeader> messageHeaderListDummy;
	
	protected MessageBody messageBodyDummy;

	protected DataNotFoundException dataNotFoundExceptionDummy;
	protected EmptyInputException emptyInputExceptionDummy;

	@Autowired
	protected MessageService messagesService;

	@Before
	public void setup() {
		message1Dummy = TestUtility.messageBuilder(TestUtility.ID_1, TestUtility.HEADER_1, TestUtility.BODY_1);
		message2Dummy = TestUtility.messageBuilder(TestUtility.ID_2, TestUtility.HEADER_2, TestUtility.BODY_2);
		messageEmptyInputDummy = TestUtility.messageBuilder(TestUtility.ID_ANY, TestUtility.HEADER_EMPTY,
				TestUtility.BODY_EMPTY);
		messageNonExistentIdDummy = TestUtility.messageBuilder(TestUtility.ID_INVALID, TestUtility.HEADER_NON_EMPTY,
				TestUtility.BODY_NON_EMPTY);
		
		messageHeaderListDummy = Arrays.asList(
				new MessageHeader(TestUtility.ID_1, TestUtility.HEADER_1),
				new MessageHeader(TestUtility.ID_2, TestUtility.HEADER_2));
		
		messageBodyDummy = new MessageBody(TestUtility.BODY_1);
		
		dataNotFoundExceptionDummy = new DataNotFoundException(
				ErrorUtility.getDataNotFoundExceptionMessage(messageNonExistentIdDummy.getId()));
		emptyInputExceptionDummy = new EmptyInputException(
				ErrorUtility.getEmptyInputExceptionMessage(messageEmptyInputDummy));
	}
	
	@Test
	public abstract void testGetMessageHeaderList() throws Exception;
	
	@Test
	public abstract void testGetMessageBodyById() throws Exception;

	@Test
	public abstract void testGetMessageBodyById_nonExistentId() throws Exception;
	
	@Test
	public abstract void testCreateMessage() throws Exception;

	@Test
	public abstract void testCreateMessage_emptyParameter() throws Exception;
	
	@Test
	public abstract void testUpdateMessageById() throws Exception;

	@Test
	public abstract void testUpdateMessageById_nonExistentId() throws Exception;

	@Test
	public abstract void testUpdateMessageById_emptyParameters() throws Exception;

	@Test
	public abstract void testDeleteMessageById() throws Exception;
	
	@Test
	public abstract void testDeleteMessageById_nonExistentId() throws Exception;

}
