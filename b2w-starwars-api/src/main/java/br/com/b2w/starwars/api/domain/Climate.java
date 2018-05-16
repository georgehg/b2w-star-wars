package br.com.b2w.starwars.api.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Climate {
	
	private final Set<String> temperatures;
	
	private Climate() {
		this.temperatures = new HashSet<String>();
	}
	
	public static Climate init(String temperature) {
		if (temperature == null) {
			throw new NullPointerException("Temperature can not be null");
		}
		Climate climate = new Climate();
		climate.addTemperature(temperature);
		return climate;
	}
	
	public Set<String> getTemperatures() {
		return Collections.unmodifiableSet(temperatures);
	}
	
	public Boolean addTemperature(String temperature) {
		return this.temperatures.add(temperature);
	}
	
	public Boolean addAllTemperature(Set<String> temperature) {
		return this.temperatures.addAll(temperature);
	}
	
	public void clearTemperature() {
		this.temperatures.clear();
	}

}