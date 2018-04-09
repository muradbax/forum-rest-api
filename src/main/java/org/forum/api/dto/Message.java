package org.forum.api.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="message")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
	private long id;
	@NotNull
	private String header;
	@NotNull
	private String body;
	
}
