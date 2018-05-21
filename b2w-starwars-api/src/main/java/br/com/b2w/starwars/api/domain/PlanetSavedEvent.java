package br.com.b2w.starwars.api.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class PlanetSavedEvent {

	private String name;

}
