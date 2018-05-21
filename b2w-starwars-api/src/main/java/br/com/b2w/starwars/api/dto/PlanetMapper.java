package br.com.b2w.starwars.api.dto;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;

@Component
public class PlanetMapper {

	public PlanetDto planetToDto(Planet planet) {
		return PlanetDto.of(planet.getId(),
							planet.getName(),
							planet.getClimate().getTemperatures(),
							planet.getTerrain().getVegetations(),
							planet.getFilms().stream().map(this::filmToDto).collect(Collectors.toSet()));
	}
	
	private FilmDto filmToDto(Film film) {
		return FilmDto.of(film.getTitle(),
							film.getDirector(),
							film.getProducer(),
							film.getReleaseDate(),
							film.getUrl());
	}

	public Planet dtoToPlanet(PlanetDto dto) {
		return Planet.of(dto.getName(),
						 Climate.init(dto.getClimate()),
						 Terrain.init(dto.getTerrain()));

	}
	
	public Film dtoToFilm(FilmDto dto) {
		return Film.of(dto.getTitle(),
						dto.getDirector(),
						dto.getProducer(),
						dto.getReleaseDate(),
						dto.getUrl());
	}

}
