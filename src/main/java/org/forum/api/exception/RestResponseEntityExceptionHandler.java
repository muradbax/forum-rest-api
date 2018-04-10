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

/**
 * Global exception handler class for all exceptions thrown in application.
 * Defines appropriate return error message and HTTP status code for different
 * type of exceptions. 
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	/**
	 * Handle {@link DataNotFoundException}.
	 *
	 * @param exception
	 *            {@link DataNotFoundException}
	 * @return {@link ErrorMessageSingle} data model response entity
	 */
	@ExceptionHandler({ DataNotFoundException.class })
	public ResponseEntity<ErrorMessageSingle> handleDataNotFoundException(DataNotFoundException exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle {@link EmptyInputException}.
	 *
	 * @param exception
	 *            {@link EmptyInputException}
	 * @return {@link ErrorMessageMultiple} data model response entity
	 */
	@ExceptionHandler({ EmptyInputException.class })
	public ResponseEntity<ErrorMessageMultiple> handleEmptyInputException(EmptyInputException exception) {
		return ErrorUtility.generateErrorMessageMap(exception.getMessageMap(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle {@link MethodArgumentTypeMismatchException}.
	 *
	 * @param exception
	 *            {@link MethodArgumentTypeMismatchException}
	 * @return {@link ErrorMessageSingle} data model response entity response entity
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<ErrorMessageSingle> handleMethodArgumentTypeMismatchExceptionException(
			MethodArgumentTypeMismatchException exception) {
		String errorMessage = ErrorUtility.getMismatchExceptionMessage(exception.getName(),
				exception.getRequiredType().getName());
		return ErrorUtility.generateErrorMessage(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle {@link NoHandlerFoundException}..
	 *
	 * @param exception
	 *            {@link NoHandlerFoundException}
	 * @return {@link ErrorMessageSingle} data model response entity response entity
	 */
	@ExceptionHandler({ NoHandlerFoundException.class })
	public ResponseEntity<ErrorMessageSingle> handleNoHandlerFoundException(NoHandlerFoundException exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND);

	}

	/**
	 * Handle {@link Exception} for all other specifically unhandled exceptions.
	 *
	 * @param exception
	 *            {@link Exception}
	 * @return {@link ErrorMessageSingle} data model response entity response entity
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorMessageSingle> handleTypeMismatchException(Exception exception) {
		return ErrorUtility.generateErrorMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
