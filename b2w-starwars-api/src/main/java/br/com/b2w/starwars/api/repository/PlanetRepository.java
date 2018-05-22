package br.com.b2w.starwars.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.b2w.starwars.api.domain.Planet;

public interface PlanetRepository extends MongoRepository<Planet, String> {

	Optional<Planet> findByName(String name);

}
