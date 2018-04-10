package org.forum.api.jdbc.dao;

import java.util.List;
import java.util.Optional;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;

/**
 * Persistence layer class provides methods to manage message entries in
 * database using JDBC API library.
 */
public interface MessageDAO {

	/**
	 * Connects to database and returns message headers with corresponding id mapped
	 * on {@link MessageHeader} data model.
	 *
	 * @return list of {@link MessageHeader} data model
	 */
	List<MessageHeader> getMessageHeaderList();

	/**
	 * Connects to database and returns message body based on specified {@code id}
	 * mapped on {@link MessageBody} data model.
	 *
	 * @param id
	 *            id of message body to get
	 * @return {@link MessageBody} data model
	 */
	MessageBody getMessageBodyById(Long id);

	/**
	 * Connects to database and creates message entry in database with parameters
	 * from specified {@link Message} data model which is returned after it is
	 * created.
	 *
	 * @param message
	 *            {@link Message} data model to create in database
	 * @return {@link Message} data model
	 */
	Message createMessage(Message message);

	/**
	 * Connects to database and updates message entry in database based on specified
	 * {@code id} with parameters from specified {@link Message} data model which is
	 * returned after it is created.
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
