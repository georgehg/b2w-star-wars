package br.com.b2w.starwars.api.domain;

import lombok.ToString;

import java.util.Date;

@ToString
public class Film {
	
	private final String title;
	
	private final String director;
	
	private final String producer;
	
	private final Date releaseDate;
	
	private final String url;

	private Film(String title, String director, String producer, Date releaseDate, String url) {
		this.title = title;
		this.director = director;
		this.producer = producer;
		this.releaseDate = releaseDate;
		this.url = url;
	}
	
	public static Film of(String title, String director, String producer, Date releaseDate, String url) {
		if (title == null) {
			throw new NullPointerException("Movie Title can not be null");
		}

		if (url == null) {
			throw new NullPointerException("Movie url can not be null");
		}

		return new Film(title, director, producer, releaseDate, url);
	}

	public String getTitle() {
		return title;
	}

	public String getDirector() {
		return director;
	}

	public String getProducer() {
		return producer;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((producer == null) ? 0 : producer.hashCode());
		result = prime * result + ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
