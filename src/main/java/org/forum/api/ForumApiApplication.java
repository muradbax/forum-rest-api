package org.forum.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launch class to start web service application using main method and deploy it
 * on embedded application server
 */
@SpringBootApplication
public class ForumApiApplication {

	/**
	 * Main method.
	 *
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ForumApiApplication.class, args);
	}

}
