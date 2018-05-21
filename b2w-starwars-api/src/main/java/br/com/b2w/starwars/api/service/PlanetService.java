package br.com.b2w.starwars.api.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.dto.FilmDto;
import br.com.b2w.starwars.api.dto.PlanetMapper;
import br.com.b2w.starwars.api.repository.PlanetRepository;

@Service
public class PlanetService {
	
	private final PlanetRepository planetRepo;
	
	private final SWApi swApi;
	
	private final PlanetMapper mapper;
	
	public PlanetService(PlanetRepository planetRepo, SWApi swApi, PlanetMapper mapper) {
		this.planetRepo = planetRepo;
		this.swApi = swApi; 
		this.mapper = mapper;
	}

	public Planet newPlanet(Planet planet) {
		return planetRepo.save(planet);
	}

	public List<Planet> getPlanetsList() {
		return planetRepo.findAll();
	}

	public boolean notExists(String planetId) {
		return ! planetRepo.exists(planetId);
	}

	public Planet getPlanet(String planetId) {
		return planetRepo.findOne(planetId);
	}

	public Planet searchPlanet(String name) {
		return planetRepo.findByName(name);
	}

	public void remove(String planetId) {
		planetRepo.delete(planetId);		
	}
	
	@Async
	public void updatePlanetFilmsList(Planet planet) {
		swApi.getFilmsListforPlanet(planet.getName())
			.parallelStream()
			.map(url -> CompletableFuture.supplyAsync(getFilmSuplier(url)))
			.collect(Collectors.toList())
			.stream()
			.map(mapper::dtoToFilm)
			.collect(Collectors.toSet());
	}
	
	public Supplier<FilmDto> getFilmSuplier(String url) {
		return () -> swApi.getFilm(url);
	}

}

