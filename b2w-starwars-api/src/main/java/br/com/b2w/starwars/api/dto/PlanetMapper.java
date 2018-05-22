package br.com.b2w.starwars.api.dto;

import java.util.stream.Collectors;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;
import br.com.b2w.starwars.api.exceptions.PlanetValidationException;

public class PlanetMapper {

	public static PlanetDto planetToDto(Planet planet) {
		return PlanetDto.of(planet.getId(),
							planet.getName(),
							planet.getClimate().getTemperatures(),
							planet.getTerrain().getVegetations(),
							planet.getFilms().stream().map(PlanetMapper::filmToDto).collect(Collectors.toSet()));
	}
	
	private static FilmDto filmToDto(Film film) {
		return FilmDto.of(film.getTitle(),
							film.getDirector(),
							film.getProducer(),
							film.getReleaseDate(),
							film.getUrl());
	}

	public static Planet dtoToPlanet(PlanetDto dto) throws PlanetValidationException {
		return Planet.of(dto.getName(),
						 Climate.init(dto.getClimate()),
						 Terrain.init(dto.getTerrain()));

	}
	
	public static Film dtoToFilm(FilmDto dto) {
		return Film.of(dto.getTitle(),
						dto.getDirector(),
						dto.getProducer(),
						dto.getReleaseDate(),
						dto.getUrl());
	}
}
