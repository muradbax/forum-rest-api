package org.forum.api.services;

import java.util.List;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;

public interface MessageService {

	Message createMessage(Message message);
	
	List<MessageHeader> getMessageHeaderList();
	
	MessageBody getMessageBodyById(Long id);
	
	Message updateMessageById(Long id, Message message);

	void deleteMessageById(Long id);
	
}
