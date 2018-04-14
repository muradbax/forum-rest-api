package org.forum.api.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Persistence layer class provides methods to manage message entries in
 * database using JPA library.
 */
@Repository
public class MessageDAOImpl implements MessageDAO {

	/** JDBC template object for providing data access. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @see org.forum.api.jdbc.dao.MessageDAO#getMessageHeaderList()
	 */
	@Override
	public List<MessageHeader> getMessageHeaderList() {
		String sql = "SELECT message_id AS id, header FROM message";
		List<MessageHeader> messageList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MessageHeader>(MessageHeader.class));
		return messageList;
	}
	
	/**
	 * @see org.forum.api.jdbc.dao.MessageDAO#getMessageBodyById(Long)
	 */
	@Override
	public MessageBody getMessageBodyById(Long id) {
		String sql = "SELECT body FROM message WHERE message_id=?";
		
		MessageBody messageBody = null;
		try {
			messageBody = jdbcTemplate.queryForObject(sql, MessageBody.class, id);
		} catch (DataAccessException dataAccessException) {
			throw new DataNotFoundException(ErrorUtility.getDataNotFoundExceptionMessage(id));
		}
		
		return messageBody;
	}
	
	/**
	 * @see org.forum.api.jdbc.dao.MessageDAO#createMessage(Message)
	 */
	@Override
	public Message createMessage(Message message) {
		String sql = "INSERT INTO message(header, body) VALUES(?, ?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	    	
	    	@Override
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement preparedStatement =
	                connection.prepareStatement(sql, new String[] {"message_id"});
	            preparedStatement.setString(1, message.getHeader());
	            preparedStatement.setString(2, message.getBody());
	            return preparedStatement;
	        }

	    }, keyHolder);
		long generatedId = keyHolder.getKey().longValue();
		
		message.setId(generatedId);
		
		return message;
	}
	
	/**
	 * @see org.forum.api.jdbc.dao.MessageDAO#updateMessageById(Long, Message)
	 */
	@Override
	public Message updateMessageById(Long id, Message message) {
		String sql = "UPDATE message SET header=?,body=? WHERE message_id=?";
		int rowsAffected = jdbcTemplate.update(sql, message.getHeader(), message.getBody(), id);
		
		if (rowsAffected<=0) {
			throw new DataNotFoundException(ErrorUtility.getDataNotFoundExceptionMessage(id));
		}
		
		message.setId(id);

		return message;
	}

	/**
	 * @see org.forum.api.jdbc.dao.MessageDAO#deleteMessageById(Long)
	 */
	@Override
	public void deleteMessageById(Long id) {
		String sql = "DELETE FROM message WHERE message_id=?";
		int rowsAffected = jdbcTemplate.update(sql, id);
		
		if (rowsAffected<=0) {
			throw new DataNotFoundException(ErrorUtility.getDataNotFoundExceptionMessage(id));
		}
	}
	
}
