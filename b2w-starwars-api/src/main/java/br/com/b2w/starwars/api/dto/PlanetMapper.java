package br.com.b2w.starwars.api.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.b2w.starwars.api.domain.Planet;

@Mapper(componentModel = "spring")	
public interface PlanetMapper {
	@Mappings({
		@Mapping(target = "name", source = "entity.name"),
		@Mapping(target = "climate", source = "entity.climate.temperatures"),
		@Mapping(target = "terrain", source = "entity.terrain.vegetations")
	})
	PlanetDto planetToPlanetDto(Planet planet);

	@Mappings({
		@Mapping(target = "name", source = "dto.employeeName"),
		@Mapping(target = "climate", expression = "java(br.com.b2w.starwars.api.domain.Climate.init(planet.getClimate())"),
		@Mapping(target = "terrain", expression = "java(br.com.b2w.starwars.api.domain.Terrain.init(planet.getTerrain())")
	})
	Planet planetDtoToPlanet(PlanetDto dto);

}
