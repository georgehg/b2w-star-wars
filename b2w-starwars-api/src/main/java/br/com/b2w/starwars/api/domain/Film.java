package br.com.b2w.starwars.api.domain;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Film {
	
	private final String title;
	
	private final String director;
	
	private final String producer;
	
	private final Date releaseDate;
	
	private final String url;
	
	public static Film of(String title, String director, String producer, Date releaseDate, String url) {
		if (title == null) {
			throw new NullPointerException("Movie Title can not be null");
		}

		if (url == null) {
			throw new NullPointerException("Movie url can not be null");
		}

		return new Film(title, director, producer, releaseDate, url);
	}
	
}