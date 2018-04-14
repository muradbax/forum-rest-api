package org.forum.api.services;

import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.jpa.dao.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Uses JPA implementation in service layer for persistent data manipulations.
 */
@Service
@Profile("jpa")
public class MessageServiceJPAImpl implements MessageService {

	/**
	 * {@ MessageRepo} persistence layer interface for JPA API.
	 */
	@Autowired
	MessageRepository messageRepo;

	/**
	 * @see org.forum.api.services.MessageService#getMessageHeaderList()
	 */
	@Override
	public List<MessageHeader> getMessageHeaderList() {
		return messageRepo.getMessageHeaderList();
	}

	/**
	 * @see org.forum.api.services.MessageService#getMessageBodyById(Long)
	 */
	@Override
	public MessageBody getMessageBodyById(Long id) {
		return messageRepo.getMessageBodyById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorUtility.getDataNotFoundExceptionMessage(id)));
	}

	/**
	 * @see org.forum.api.services.MessageService#createMessage(Message)
	 */
	@Override
	public Message createMessage(Message message) {
		ErrorUtility.isEmptyFields(message);
		return messageRepo.save(message);
	}

	/**
	 * @see org.forum.api.services.MessageService#updateMessageById(Long, Message)
	 */
	@Override
	public Message updateMessageById(Long id, Message message) {
		ErrorUtility.isEmptyFields(message);

		Message messageToUpdate = messageRepo.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorUtility.getDataNotFoundExceptionMessage(id)));

		messageToUpdate.setHeader(message.getHeader());
		messageToUpdate.setBody(message.getBody());

		return messageRepo.save(messageToUpdate);
	}

	/**
	 * @see org.forum.api.services.MessageService#deleteMessageById(Long)
	 */
	@Override
	public void deleteMessageById(Long id) {
		messageRepo.deleteById(id);
	}

}
