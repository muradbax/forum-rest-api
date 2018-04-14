package org.forum.api.controller;

import java.util.List;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class defines REST API for manipulating messages entries.
 */
@CrossOrigin(origins = { "${spring.origin.cors}" })
@RestController
@RequestMapping("/api/message")
public class MessageController {

	/**
	 * {@ MessageService} service layer object. Correct service layer implementation
	 * injected is based on profile used defined in properties file.
	 */
	@Autowired
	private MessageService messageService;

	/**
	 * Maps all GET requests to base url and returns list of {@link MessageHeader}
	 * data models representing message header with corresponding id.
	 *
	 * @return list of {@link MessageHeader} data models
	 */
	@GetMapping
	public List<MessageHeader> getHeaderList() {
		return messageService.getMessageHeaderList();
	}

	/**
	 * Maps all GET requests to base url/id and returns {@link MessageBody} data
	 * model representing body of message based on specified {@code id} from
	 * database.
	 *
	 * @param id
	 *            id of message body to get
	 * @return {@link MessageBody} data model
	 */
	@GetMapping("/{id}")
	public MessageBody getBodyById(@PathVariable(value = "id") Long id) {
		return messageService.getMessageBodyById(id);
	}

	/**
	 * Maps all POST requests to base url and creates message entry in database with
	 * parameters from specified {@link Message} data model which is returned after
	 * it is created.
	 *
	 * @param message
	 *            {@link Message} data model to create in database
	 * @return {@link Message} data model
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Message create(@RequestBody Message message) {
		return messageService.createMessage(message);
	}

	/**
	 * Maps all PUT requests to base url/id and updates message entry in database
	 * based on specified {@code id} with parameters from specified {@link Message}
	 * data model which is returned after it is created.
	 *
	 * @param id
	 *            id of message to update
	 * @param message
	 *            {@link Message} data model to update in database
	 * @return {@link Message} data model
	 */
	@PutMapping("/{id}")
	public Message updateById(@PathVariable(value = "id") Long id, @RequestBody Message message) {
		return messageService.updateMessageById(id, message);
	}

	/**
	 * Maps all DELETE requests to base url/id and deletes message entry in database
	 * based on specified {@code id}.
	 *
	 * @param id
	 *            id of message to delete
	 */
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(value = "id") Long id) {
		messageService.deleteMessageById(new Long(id));
	}

}
