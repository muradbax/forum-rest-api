package org.forum.api.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Data model class representing Message entry.
 */
@Data
@Entity
@Table(name="message")
public class Message {
	
	/** Id.*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
	private Long id;
	
	/** Header. */
	@NotNull
	private String header;
	
	/** Body. */
	@NotNull
	private String body;
	
}
