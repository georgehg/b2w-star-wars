package br.com.b2w.starwars.api.dto;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Terrain;

import br.com.b2w.starwars.api.domain.Planet;
import org.springframework.stereotype.Component;

@Component
public class PlanetMapper {

	public PlanetDto planetToDto(Planet planet) {
		return PlanetDto.of(planet.getId(),
							planet.getName(),
							planet.getClimate().getTemperatures(),
							planet.getTerrain().getVegetations());
	}

	public Planet dtoToPlanet(PlanetDto dto) {
		return Planet.of(dto.getName(),
						 Climate.init(dto.getClimate()),
						 Terrain.init(dto.getTerrain()));

	}

}
