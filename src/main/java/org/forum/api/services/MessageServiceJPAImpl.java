package org.forum.api.services;

import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.forum.api.jpa.dao.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jpa")
public class MessageServiceJPAImpl implements MessageService {

	@Autowired
	MessageRepository messageRepo;
	
	@Override
	public Message createMessage(Message message) {
		ErrorUtility.checkIfEmpty(message);
		return messageRepo.save(message);
	}

	@Override
	public List<MessageHeader> getMessageHeaderList() {
		return messageRepo.getMessageHeaderList();
	}

	@Override
	public MessageBody getMessageBodyById(Long id) {
		return messageRepo.getMessageBodyById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorUtility.getNoDataFoundExceptionMessage(id)));
	}

	@Override
	public Message updateMessageById(Long id, Message message) {
		ErrorUtility.checkIfEmpty(message);
		
		Message messageToUpdate = messageRepo.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorUtility.getNoDataFoundExceptionMessage(id)));
		
		messageToUpdate.setHeader(message.getHeader());
		messageToUpdate.setBody(message.getBody());
		
		return messageRepo.save(messageToUpdate);
	}
	
	@Override
	public void deleteMessageById(Long id) {
		messageRepo.deleteById(id);
	}
		
}
