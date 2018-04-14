package org.forum.api.services;

import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.jdbc.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Uses JDBC API implementation in service layer for persistent data
 * manipulations.
 */
@Service
@Profile("jdbc")
public class MessageServiceJDBCImpl implements MessageService {

	/**
	 * {@ MessageDAO} persistence layer object for JDBC API.
	 */
	@Autowired
	MessageDAO messageDAO;

	/**
	 * @see org.forum.api.services.MessageService#getMessageHeaderList()
	 */
	@Override
	public List<MessageHeader> getMessageHeaderList() {
		return messageDAO.getMessageHeaderList();
	}

	/**
	 * @see org.forum.api.services.MessageService#getMessageBodyById(Long)
	 */
	@Override
	public MessageBody getMessageBodyById(Long id) {
		return messageDAO.getMessageBodyById(id);
	}

	/**
	 * @see org.forum.api.services.MessageService#createMessage(Message)
	 */
	@Override
	public Message createMessage(Message message) {
		ErrorUtility.isEmptyFields(message);
		return messageDAO.createMessage(message);
	}

	/**
	 * @see org.forum.api.services.MessageService#updateMessageById(Long, Message)
	 */
	@Override
	public Message updateMessageById(Long id, Message message) {
		ErrorUtility.isEmptyFields(message);
		return messageDAO.updateMessageById(id, message);
	}

	/**
	 * @see org.forum.api.services.MessageService#deleteMessageById(Long)
	 */
	@Override
	public void deleteMessageById(Long id) {
		messageDAO.deleteMessageById(id);
	}

}
