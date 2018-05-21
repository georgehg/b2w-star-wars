package br.com.b2w.starwars.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.b2w.starwars.api.dto.PlanetDto;
import br.com.b2w.starwars.api.dto.PlanetMapper;
import br.com.b2w.starwars.api.service.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
	
	private final PlanetService planetService;

	public PlanetController(PlanetService planetService) {
		this.planetService = planetService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetDto> createPlanet(@RequestBody(required = true) PlanetDto planet) {
		PlanetDto newPlanet = PlanetMapper.planetToDto(
								planetService.newPlanet(
										PlanetMapper.dtoToPlanet(planet)));
		
		return ResponseEntity.created(URI.create("localhost:8080/api/v1/planets/" + String.valueOf(newPlanet.getId())))
              			.body(newPlanet);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlanetDto>> getPlanets() {
		return ResponseEntity.ok(planetService.getPlanetsList().stream()
												.map(PlanetMapper::planetToDto)
												.collect(Collectors.toList()));
	}
	
	@GetMapping(value = "/{planetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetDto> getPlanetById(@PathVariable String planetId) {
		if (planetService.notExists(planetId)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(PlanetMapper.planetToDto(planetService.getPlanet(planetId)));
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlanetDto> searchPlanet(@RequestParam(value = "name", required=true) String name) {
		return ResponseEntity.ok(PlanetMapper.planetToDto(planetService.searchPlanet(name)));
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
