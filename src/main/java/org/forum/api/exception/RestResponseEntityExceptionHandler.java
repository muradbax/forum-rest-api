package org.forum.api.exception;

import org.forum.api.commons.ErrorUtility;
import org.forum.api.dto.ErrorMessageMultiple;
import org.forum.api.dto.ErrorMessageSingle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
	@ExceptionHandler({ DataNotFoundException.class })
	public ResponseEntity<ErrorMessageSingle> handleDataNotFoundException(DataNotFoundException exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({ EmptyInputException.class })
	public ResponseEntity<ErrorMessageMultiple> handleEmptyInputException(EmptyInputException exception) {
		return ErrorUtility.generateErrorMessageMap(exception.getMessageMap(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ErrorMessageSingle> handleMethodArgumentTypeMismatchExceptionException(MethodArgumentTypeMismatchException exception) {
		String errorMessage = ErrorUtility.getMismatchExceptionMessage(exception.getName(), exception.getRequiredType().getName());
		return ErrorUtility.generateErrorMessage(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ NoHandlerFoundException.class })
	public ResponseEntity<ErrorMessageSingle> handleNoHandlerFoundException(NoHandlerFoundException exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorMessageSingle> handleTypeMismatchException(Exception exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
