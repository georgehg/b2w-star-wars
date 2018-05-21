package br.com.b2w.starwars.api.service;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetServiceIT {

    @Autowired
    private PlanetService planetService;

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @Test
    public void shouldUpdatePlanet_WithFilmsList_AfterSaveEvent() {
    	Planet planet = Planet.of("Tatooine",
                Climate.init().addTemperature("arid"),
                Terrain.init().addVegetation("desert"));

        String planetId = planetService.newPlanet(planet).getId();
    	assertThat(planet.getFilms()).isEmpty();
    	
        await().atMost(30, SECONDS).untilAsserted(() -> {
            Planet updatedPlanet = planetService.getPlanet(planetId);
            assertThat(updatedPlanet.getFilms()).isNotEmpty();
        });

    	
    }
}