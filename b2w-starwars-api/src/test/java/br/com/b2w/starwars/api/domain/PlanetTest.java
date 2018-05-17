package br.com.b2w.starwars.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.starwars.api.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PlanetTest {
	
	@Autowired
	private PlanetRepository planetRepo;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void shouldCreateNewPlanet() throws ParseException {
		
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
		
		planetRepo.save(planet);
		
		Planet newPlanet = planetRepo.findOne(planet.getId());
		
		assertThat(newPlanet.getName()).isEqualTo("Tatooine");
		assertThat(newPlanet.getClimate().getTemperatures()).contains("arid");
		assertThat(newPlanet.getTerrain().getVegetations()).contains("desert");
		assertThat(newPlanet.getFilms()).contains(film1, film2);
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldIssueErrorForPlanetNullName() throws ParseException {
		Planet.of(null, Climate.init(), Terrain.init());
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldIssueErrorForPlanetNullClimate() throws ParseException {
		Planet.of("Tatooine", null, Terrain.init());
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldIssueErrorForPlanetNullTerrain() throws ParseException {
		Planet.of("Tatooine", Climate.init(), null);
	}
		

}
