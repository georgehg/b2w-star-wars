package br.com.b2w.starwars.api.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorDto {

    private final String code;

    private final String cause;

    private final String message;
    
    private ErrorDto(String code, String cause, String message) {
		this.code = code;
		this.cause = cause;
		this.message = message;
	}
    
    public ErrorDto() {
    	this(null, null, null);
    }
	
	public static ErrorDto of(String code, String cause, String message) {
		return new ErrorDto(code, cause, message);
	}
	
	public static ErrorDto of(Throwable throwable) {
		return new ErrorDto(null, throwable.getClass().getName(), throwable.getLocalizedMessage());
	}
}
