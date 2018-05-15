package br.com.b2w.starwars.api.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planet {
	
	String name;
	
	Climate climate;
	
	Terrain terrain;
	
	Set<Film> films;
	
}