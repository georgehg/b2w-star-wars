package br.com.b2w.starwars.api.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanetDto {

	@JsonIgnore
	private final String id;

	private final String name;

	private final Set<String> climate;

	private final Set<String> terrain;
	
	private final Set<FilmDto> films;

	protected PlanetDto() {
		this(null, null, null, null, null);
	}

	public static PlanetDto of(String id, String name, Set<String> climate, Set<String> terrain, Set<FilmDto> films) {
		return new PlanetDto(id, name, climate, terrain, films);
	}

}
