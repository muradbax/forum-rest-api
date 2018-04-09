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

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT NEW org.forum.api.dto.MessageHeader(m.id, m.header) FROM Message m")
	List<MessageHeader> getMessageHeaderList();
		
	@Query("SELECT NEW org.forum.api.dto.MessageBody(m.body) FROM Message m WHERE m.id = :id")
	Optional<MessageBody> getMessageBodyById(@Param("id") Long id);
	
}
