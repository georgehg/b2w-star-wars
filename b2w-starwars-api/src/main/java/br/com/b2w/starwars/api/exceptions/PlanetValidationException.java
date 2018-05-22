package br.com.b2w.starwars.api.exceptions;

public class PlanetValidationException extends Exception {
	
	private static final long serialVersionUID = 3551364359480389259L;

	public PlanetValidationException(String message) {
		super(message);
	}

}

