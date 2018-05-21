package br.com.b2w.starwars.api.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanetDto {
	
	private final String id;
	
	private final String name;

	private final Set<String> climate;
	
	private final Set<String> terrain;
	
	private final Set<FilmDto> films;

	protected PlanetDto() {
		this(null, null, null, null, null);
	}
	
	public static PlanetDto of(String id, String name, Set<String> climate, Set<String> terrain) {
		return PlanetDto.of(id, name, climate, terrain, null);
	}

	public static PlanetDto of(String id, String name, Set<String> climate, Set<String> terrain, Set<FilmDto> films) {
		if (name == null) {
			throw new NullPointerException("Name can note be null");
		}

		if (climate == null || climate.isEmpty()) {
			throw new NullPointerException("Climate can note be empty");
		}

		if (terrain == null || terrain.isEmpty()) {
			throw new NullPointerException("Terrain can note be empty");
		}

		return new PlanetDto(id, name, climate, terrain, films);
	}

}
