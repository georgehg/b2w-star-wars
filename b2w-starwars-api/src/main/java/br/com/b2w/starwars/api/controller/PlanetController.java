package br.com.b2w.starwars.api.controller;

import java.net.URI;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.dto.PlanetDto;
import br.com.b2w.starwars.api.dto.PlanetMapper;
import br.com.b2w.starwars.api.service.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
	
	private PlanetMapper mapper = Mappers.getMapper(PlanetMapper.class);
	
	private final PlanetService planetService;
	
	public PlanetController(PlanetService planetService) {
		this.planetService = planetService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetDto> newPlanet(@RequestBody(required = true) PlanetDto planet) {
		planet = mapper.planetToPlanetDto(
					planetService.newPlanet(
							mapper.planetDtoToPlanet(planet)));
		
		return ResponseEntity.created(URI.create("localhost:8080/planets/" + String.valueOf(planet.getId())))
              			.body(planet);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Planet>> getPlanets(@PathVariable String planetId) {
		return ResponseEntity.ok(planetService.getPlanetsList());
	}
	
	@GetMapping(value = "/{planetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planet> getPlanetById(@PathVariable String planetId) {
		if (planetService.notExists(planetId)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(planetService.getPlanet(planetId));
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Planet> searchPlanet(@RequestParam(value = "name", required=true) String name) {
		Planet planet = planetService.searchPlanet(name);
		if (planet == null) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.ok(planet);
	}
	
	@DeleteMapping(value = "/{planetId}")
	public ResponseEntity<?> deletePlanet(@PathVariable String planetId) {
		if (planetService.notExists(planetId)) {
			return ResponseEntity.notFound().build();
		}
		
		planetService.remove(planetId);
		return ResponseEntity.noContent().build();
	}

}
