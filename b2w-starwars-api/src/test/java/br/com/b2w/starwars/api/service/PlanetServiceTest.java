package br.com.b2w.starwars.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;
import br.com.b2w.starwars.api.exceptions.PlanetNotFoundException;
import br.com.b2w.starwars.api.exceptions.PlanetValidationException;
import br.com.b2w.starwars.api.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
@Import(ServiceConfig.class)
public class PlanetServiceTest {
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private PlanetRepository planetRepo;

    private PlanetService planetService;
    
    @Autowired
    public void setPlanetServiceTest(PlanetRepository planetRepo) {
        this.planetRepo = planetRepo;
        this.planetService = new PlanetService(planetRepo);
    }

    private Planet getPlanet() throws ParseException, PlanetValidationException {
        Film film1 = Film.of("A New Hope",
                "George Lucas",
                "Gary Kurtz, Rick McCallum",
                formatter.parse("1977-05-25"),
                "https://swapi.co/api/films/1/");

        Film film2 = Film.of("Return of the Jedi",
                "Richard Marquand",
                "Howard G. Kazanjian, George Lucas, Rick McCallum",
                formatter.parse("1983-05-25"),
                "https://swapi.co/api/films/3/");

        Planet planet = Planet.of("Tatooine",
                Climate.init().addTemperature("arid"),
                Terrain.init().addVegetation("desert"),
                Sets.newSet(film1, film2));

        return planet;
    }

    @After
    public void cleanDataBase() {
        planetRepo.deleteAll();
    }

    @Test
    public void shouldCreateNewPlanet() throws Exception {
        Planet planet = getPlanet();
        planetService.newPlanet(planet);
        assertThat(planet.getId()).isNotNull();
    }

    @Test
    public void shouldGetPlanetsList() throws Exception {
        Planet planet = getPlanet();
        planetService.newPlanet(planet);
        assertThat(planetService.getPlanetsList()).isNotEmpty();
    }

    @Test
    public void shouldReturnTrueForNotExistsPlanet() throws Exception {
        Planet planet = getPlanet();
        planetService.newPlanet(planet);
        assertThat(planetService.notExists("12345")).isTrue();
    }

    @Test
    public void shouldReturnPlanetById() throws Exception {
        Planet newPlanet = getPlanet();
        String planetId = planetService.newPlanet(newPlanet).getId();

        Planet planet = planetService.getPlanet(planetId);
        assertThat(planet).isNotNull();
    }

    @Test
    public void shouldFindPlanetByName() throws Exception {
        Planet newPlanet = getPlanet();
        String planetName = planetRepo.save(newPlanet).getName();

        Planet planet = planetService.searchPlanet(planetName);
        assertThat(planet).isNotNull();
    }

    @Test
    public void shouldRemoveOnePlanet() throws Exception {
        Planet newPlanet = getPlanet();
        String planetId = planetRepo.save(newPlanet).getId();

        planetService.remove(planetId);

        assertThatThrownBy(() -> planetService.getPlanet(planetId))
		.isInstanceOf(PlanetNotFoundException.class)
		.hasMessageContaining("Planet do not exists with ID:");
    }
    
    @Test
    public void shouldIssueErrorForNotExistantId() throws Exception {
        assertThatThrownBy(() -> planetService.getPlanet("9999999999"))
		.isInstanceOf(PlanetNotFoundException.class)
		.hasMessageContaining("Planet do not exists with ID:");
    }
    
    
    @Test
    public void shouldIssueErrorForNameNotFound() throws Exception {
        assertThatThrownBy(() -> planetService.searchPlanet("Terra"))
		.isInstanceOf(PlanetNotFoundException.class)
		.hasMessageContaining("Planet not found with name:");
    }
}