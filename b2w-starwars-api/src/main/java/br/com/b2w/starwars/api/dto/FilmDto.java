package br.com.b2w.starwars.api.dto;

import java.util.Date;

import br.com.b2w.starwars.api.domain.Film;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FilmDto {
	
	private final String title;
	
	private final String director;

	private final String producer;
	
	@JsonProperty("release_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private final Date releaseDate;
	
	private final String url;

	protected FilmDto() {
		this(null, null, null, null, null);
	}

	public static FilmDto of(String title, String director, String producer, Date releaseDate, String url) {
		return new FilmDto(title, director, producer, releaseDate, url);
	}

}
