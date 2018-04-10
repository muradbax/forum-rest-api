package org.forum.api.jpa.dao;

import java.util.List;
import java.util.Optional;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer class provides methods to manage message entries in
 * database using JDBC library.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	/**
	 * Connects to database and returns message headers with corresponding id mapped
	 * on {@link MessageHeader} data model.
	 *
	 * @return list of {@link MessageHeader} data model
	 */
	@Query("SELECT NEW org.forum.api.dto.MessageHeader(m.id, m.header) FROM Message m")
	List<MessageHeader> getMessageHeaderList();

	/**
	 * Connects to database and returns message body based on specified {@code id}
	 * mapped on {@link MessageBody} data model. {@link Optional} container is used
	 * for null checks
	 *
	 * @param id
	 *            id of message body to get
	 * 
	 * @return {@link MessageBody} data model within {@link Optional} container
	 */
	@Query("SELECT NEW org.forum.api.dto.MessageBody(m.body) FROM Message m WHERE m.id = :id")
	Optional<MessageBody> getMessageBodyById(@Param("id") Long id);

}
