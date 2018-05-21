package br.com.b2w.starwars.api.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class ErrorDto {

    private final String code;

    private final String cause;

    private final String message;
}
