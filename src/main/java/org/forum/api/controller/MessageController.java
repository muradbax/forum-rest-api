package org.forum.api.controller;

import java.util.List;

import javax.annotation.Resource;

import org.forum.api.dto.Message;
import org.forum.api.dto.MessageBody;
import org.forum.api.dto.MessageHeader;
import org.forum.api.services.MessageService;
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

@CrossOrigin(origins = {"${spring.origin.cors}"})
@RestController
@RequestMapping("/api/message")
public class MessageController {
	
	@Resource(name="${spring.data.persistence}")
	private MessageService messageService;
		
	@GetMapping
    public List<MessageHeader> getHeaderList() {
		return messageService.getMessageHeaderList();
    }
	
	@GetMapping("/{id}")
	public MessageBody getBodyById(@PathVariable(value="id") Long id) {
		return messageService.getMessageBodyById(id);	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Message create(@RequestBody Message message) {		
		return messageService.createMessage(message);
	}
	
	@PutMapping("/{id}")
	public Message updateById(@PathVariable(value="id") long id, @RequestBody Message message) {
		return messageService.updateMessageById(id, message);		
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable(value="id") long id) {
		messageService.deleteMessageById(new Long(id));		
	}
	
}
