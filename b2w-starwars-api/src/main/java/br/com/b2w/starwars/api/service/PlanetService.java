package br.com.b2w.starwars.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.exceptions.PlanetNotFoundException;
import br.com.b2w.starwars.api.repository.PlanetRepository;

@Service
public class PlanetService {
	
	private final PlanetRepository planetRepo;

	public PlanetService(PlanetRepository planetRepo) {
		this.planetRepo = planetRepo; }

	public Planet newPlanet(Planet planet) {
		return planetRepo.save(planet.complete());
	}

	public List<Planet> getPlanetsList() {
		return planetRepo.findAll();
	}

	public boolean notExists(String planetId) {
		return ! planetRepo.exists(planetId);
	}

	public Planet getPlanet(String planetId) throws PlanetNotFoundException {
		if (notExists(planetId)) {
			throw new PlanetNotFoundException("Planet do not exists with ID: " + planetId);
		}
		return planetRepo.findOne(planetId);
	}

	public Planet searchPlanet(String name) throws PlanetNotFoundException {
		return planetRepo.findByName(name)
						.orElseThrow(() -> new PlanetNotFoundException("Planet not found with name: " + name));
	}

	public void remove(String planetId) throws PlanetNotFoundException {
		if (notExists(planetId)) {
			throw new PlanetNotFoundException("Planet do not exists with ID: " + planetId);
		}
		planetRepo.delete(planetId);		
	}

}

