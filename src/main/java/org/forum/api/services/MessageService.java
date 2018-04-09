package org.forum.api.services;

import java.util.List;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;

/**
 * Service layer provides methods to manage message entries in database.
 */
public interface MessageService {

	/**
	 * Service returns list of {@link MessageHeader} data models representing
	 * message header with corresponding id.
	 *
	 * @return list of {@link MessageHeader} data models
	 */
	List<MessageHeader> getMessageHeaderList();

	/**
	 * Service returns {@link MessageBody} data model representing body of message
	 * based on specified {@code id} from database.
	 *
	 * @param id
	 *            id of message body to get
	 * @return {@link MessageBody} data model
	 */
	MessageBody getMessageBodyById(Long id);

	/**
	 * Service creates message entry in database with parameters from specified
	 * {@link Message} data model which is returned after it is created.
	 *
	 * @param message
	 *            {@link Message} data model to create in database
	 * @return {@link Message} data model
	 */
	Message createMessage(Message message);

	/**
	 * Service updates message entry in database based on specified {@code id} with
	 * parameters from specified {@link Message} data model which is returned after
	 * it is created.
	 *
	 * @param id
	 *            id of message to update
	 * @param message
	 *            {@link Message} data model to update in database
	 * @return {@link Message} data model
	 */
	Message updateMessageById(Long id, Message message);

	/**
	 * Service deletes message entry in database based on specified {@code id}.
	 *
	 * @param id
	 *            id of message to delete
	 */
	void deleteMessageById(Long id);

}
