package br.com.b2w.starwars.api.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Terrain {
	
	private final Set<String> vegetations;
	
	private Terrain() {
		this.vegetations = new HashSet<String>();
	}
	
	public static Terrain init(String vegetation) {
		if (vegetation == null) {
			throw new NullPointerException("Vegetation can not be null");
		}
		Terrain terrain = new Terrain();
		terrain.addVegetation(vegetation);
		return terrain;
	}
	
	public Set<String> getVegetations() {
		return Collections.unmodifiableSet(vegetations);
	}
	
	public Boolean addVegetation(String vegetation) {
		return this.vegetations.add(vegetation);
	}
	
	public Boolean addAllVegetation(Set<String> vegetation) {
		return this.vegetations.addAll(vegetation);
	}
	
	public void clearVegetation() {
		this.vegetations.clear();
	}

}
