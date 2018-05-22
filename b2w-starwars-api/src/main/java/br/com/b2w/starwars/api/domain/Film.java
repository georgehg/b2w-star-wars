package br.com.b2w.starwars.api.domain;

import lombok.*;

import static com.google.common.base.Preconditions.checkNotNull;

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
		checkNotNull(title, "Film title name can not be null");
		checkNotNull(url, "Field url can not be null");
		return new Film(title, director, producer, releaseDate, url);
	}
	
}