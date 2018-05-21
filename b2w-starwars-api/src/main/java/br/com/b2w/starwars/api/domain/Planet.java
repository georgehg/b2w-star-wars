package br.com.b2w.starwars.api.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.ToString;

@ToString
@Document
public class Planet extends AbstractAggregateRoot {
	
	@Id
	private String id;

	@Indexed(unique = true)
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
		return Planet.of(name, climate, terrain, new HashSet<>());
	}
	
	public static Planet of(String name, Climate climate, Terrain terrain, Set<Film> films) {
		if (name == null) {
			throw new NullPointerException("Name can note be null");
		}

		if (climate == null) {
			throw new NullPointerException("Climate can note be null");
		}

		if (terrain == null) {
			throw new NullPointerException("Terrain can note be null");
		}

		return new Planet(name, climate, terrain, films);
	}

	public Planet complete() {
		registerEvent(PlanetSavedEvent.of(this.name));
		return this;
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
	
	public void addFilms(Set<Film> films) {
		this.films.addAll(films);
	}

	public Set<Film> getFilms() {
		return Collections.unmodifiableSet(films);
	}
	
}