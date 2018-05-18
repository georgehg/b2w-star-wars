package br.com.b2w.starwars.api.domain;

import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ToString
public class Climate {
	
	private final Set<String> temperatures;
	
	private Climate() {
		this.temperatures = new HashSet<String>();
	}
	
	public static Climate init() {
		return new Climate();
	}
	
	public static Climate init(Set<String> temperatures) {
		Climate climate = new Climate();
		climate.addAllTemperature(temperatures);
		return climate;
	}
	
	public Set<String> getTemperatures() {
		return Collections.unmodifiableSet(temperatures);
	}
	
	public Climate addTemperature(String temperature) {
		if (temperature == null) {
			throw new NullPointerException("Temperature can not be null");
		}
		this.temperatures.add(temperature);
		return this;
	}
	
	public Boolean addAllTemperature(Set<String> temperatures) {
		return this.temperatures.addAll(temperatures);
	}
	
	public void clearTemperature() {
		this.temperatures.clear();
	}

}