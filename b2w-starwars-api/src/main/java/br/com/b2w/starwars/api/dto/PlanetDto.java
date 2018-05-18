package br.com.b2w.starwars.api.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PlanetDto {
	
	private final String id;
	
	private final String name;

	private final Set<String> climate;
	
	private final Set<String> terrain;
	
	public PlanetDto(String name, Set<String> climate, Set<String> terrain) {
		this(null, name, climate, terrain);
	}
	
}
