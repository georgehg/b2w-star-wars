package br.com.b2w.starwars.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value(staticConstructor = "of")
public class FilmDto {
	
	private final String title;
	
	private final String director;
	
	private final String producer;
	
	@JsonProperty("release_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final Date releaseDate;
	
	private final String url;	
	
}
