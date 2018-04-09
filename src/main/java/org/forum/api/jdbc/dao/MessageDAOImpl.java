package org.forum.api.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	@Override
	public List<MessageHeader> getMessageHeaderList() {
		String sql = "SELECT message_id AS id, header FROM message";
		List<MessageHeader> messageList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<MessageHeader>(MessageHeader.class));
		return messageList;
	}

	@Override
	public MessageBody getMessageBodyById(Long id) {
		String sql = "SELECT body FROM message WHERE message_id=?";
		MessageBody messageBody = jdbcTemplate.queryForObject(sql, MessageBody.class, id);
		return messageBody;
	}

	@Override
	public Message updateMessageById(Long id, Message message) {
		String sql = "UPDATE message SET header=?,body=? WHERE message_id=?";
		jdbcTemplate.update(sql, message.getHeader(), message.getBody(), id);
				
		message.setId(id);

		return message;
	}

	@Override
	public void deleteMessageById(Long id) {
		String sql = "DELETE FROM message WHERE message_id=?";
		jdbcTemplate.update(sql, id);
	}
	
}
