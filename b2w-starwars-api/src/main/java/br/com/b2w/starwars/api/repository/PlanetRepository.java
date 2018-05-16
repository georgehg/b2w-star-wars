package br.com.b2w.starwars.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.b2w.starwars.api.domain.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	Planet findByName(String name);

}
