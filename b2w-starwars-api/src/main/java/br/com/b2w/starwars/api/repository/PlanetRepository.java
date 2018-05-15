package br.com.b2w.starwars.api.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.b2w.starwars.api.model.Planet;

public interface PlanetRepository extends MongoRepository<Planet, Serializable> {

}
