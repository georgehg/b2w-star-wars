package br.com.b2w.starwars.api.domain;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class PlanetSaveListener extends AbstractMongoEventListener<Planet> {

	@Override
	public void onBeforeSave(BeforeSaveEvent<Planet> event) {
		System.out.println("Planet saved: " + event.getSource());
		
	}
	
	
	
}
