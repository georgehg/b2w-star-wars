package br.com.b2w.starwars.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mongodb.DuplicateKeyException;
 
import br.com.b2w.starwars.api.dto.ErrorDto;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = DuplicateKeyException.class)
	public ResponseEntity<ErrorDto> planetDuplicatedExceptionHandler(DuplicateKeyException ex) {
		ErrorDto responseBody = ErrorDto.of(HttpStatus.CONFLICT.name(), ex.getClass().getName(), ex.getMessage());
		return response(responseBody, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = PlanetValidationException.class)
	protected ResponseEntity<ErrorDto> planetValidationExceptionHandler(PlanetValidationException ex) {
		ErrorDto responseBody = ErrorDto.of(HttpStatus.BAD_REQUEST.name(), ex.getClass().getName(), ex.getMessage());
		return response(responseBody,  HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = PlanetNotFoundException.class)
	protected ResponseEntity<ErrorDto> planetNotFoundExceptionHandler(PlanetNotFoundException ex) {
		ErrorDto responseBody = ErrorDto.of(HttpStatus.NOT_FOUND.name(), ex.getClass().getName(), ex.getMessage());
		return response(responseBody,  HttpStatus.NOT_FOUND);
	}
	
	private <T> ResponseEntity<T> response(T body, HttpStatus status) {
		this.logger.debug("Responding with a status of " + status.toString());
		return new ResponseEntity<>(body, new HttpHeaders(), status);
	}
}
