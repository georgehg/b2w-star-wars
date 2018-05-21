package br.com.b2w.starwars.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;
import br.com.b2w.starwars.api.dto.PlanetMapper;
import br.com.b2w.starwars.api.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import(ServiceConfig.class)
@AutoConfigureJsonTesters
public class PlanetServiceIT {

    private PlanetRepository planetRepo;

    private PlanetService planetService;
    
    private SWApi swApi;
    
    @Autowired
    public void setPlanetServiceTest(PlanetRepository planetRepo, SWApi swApi, PlanetMapper mapper) {
        this.planetRepo = planetRepo;
        this.swApi = swApi;
        this.planetService = new PlanetService(planetRepo, swApi, mapper);
    }

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Test
    public void shouldUpdatePlanet_WithFilmsList_AfterSaveEvent() {
    	Planet planet = Planet.of("Tatooine",
                Climate.init().addTemperature("arid"),
                Terrain.init().addVegetation("desert"));
    	
    	planetService.newPlanet(planet);
    	assertThat(planet.getFilms()).isEmpty();
    	
    	planetService.updatePlanetFilmsList(planet);
    	assertThat(planet.getFilms()).isNotEmpty();
    	
    }
}