package br.com.b2w.starwars.api.domain;

import java.util.Set;

import lombok.ToString;
import org.springframework.data.annotation.Id;

@ToString
public class Planet {
	
	@Id
	private String id;
	
	private final String name;

	private final Climate climate;
	
	private final Terrain terrain;
	
	private final Set<Film> films;

	private Planet(String name, Climate climate, Terrain terrain, Set<Film> films) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
	}
	
	public static Planet of(String name, Climate climate, Terrain terrain) {
		if (name == null) {
			throw new NullPointerException("Name can note be null");
		}
		
		if (climate == null) {
			throw new NullPointerException("Climate can note be null");
		}
		
		if (terrain == null) {
			throw new NullPointerException("Terrain can note be null");
		}
		
		return Planet.of(name, climate, terrain, null);
	}
	
	public static Planet of(String name, Climate climate, Terrain terrain, Set<Film> films) {
		return new Planet(name, climate, terrain, films);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Climate getClimate() {
		return climate;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public Set<Film> getFilms() {
		return films;
	}
	
}