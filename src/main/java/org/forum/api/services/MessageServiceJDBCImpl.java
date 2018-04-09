package org.forum.api.services;

import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.jdbc.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jdbc")
public class MessageServiceJDBCImpl implements MessageService {
	
	@Autowired
	MessageDAO messageDAO;

	@Override
	public Message createMessage(Message message) {
		ErrorUtility.checkIfEmpty(message);
		return messageDAO.createMessage(message);		
	}

	@Override
	public List<MessageHeader> getMessageHeaderList() {
		return messageDAO.getMessageHeaderList();
	}

	@Override
	public MessageBody getMessageBodyById(Long id) {
		return messageDAO.getMessageBodyById(id);
	}

	@Override
	public Message updateMessageById(Long id, Message message) {
		ErrorUtility.checkIfEmpty(message);
		return messageDAO.updateMessageById(id, message);
	}

	@Override
	public void deleteMessageById(Long id) {
		messageDAO.deleteMessageById(id);
	}
	
}
