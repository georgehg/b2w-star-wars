package br.com.b2w.starwars.api.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.b2w.starwars.api.exceptions.PlanetValidationException;
import br.com.b2w.starwars.api.repository.PlanetRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PlanetTest {
	
	@Autowired
	private PlanetRepository planetRepo;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void shouldCreateNewPlanet() throws ParseException, PlanetValidationException {
		
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

	@Test(expected = DuplicateKeyException.class)
	public void shouldIssueErrorForNotUniqueName() throws ParseException, PlanetValidationException {
		planetRepo.save(Planet.of("Tatooine",
									Climate.init().addTemperature("arid"),
									Terrain.init().addVegetation("desert")));

		planetRepo.save(Planet.of("Alderaan",
									Climate.init().addTemperature("temperate"),
									Terrain.init().addVegetation("grasslands")));

		planetRepo.save(Planet.of("Tatooine",
									Climate.init().addTemperature("arid"),
									Terrain.init().addVegetation("desert")));
	}
	
	@Test
	public void shouldIssueErrorForPlanetNullName() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of(null, Climate.init().addTemperature("temperate"), Terrain.init().addVegetation("grasslands")))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field name can not be null");
	}
	
	@Test
	public void shouldIssueErrorForPlanetEmptyName() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of("", Climate.init().addTemperature("temperate"), Terrain.init().addVegetation("grasslands")))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field name can not be empty");
	}
	
	@Test
	public void shouldIssueErrorForPlanetNullClimate() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of("Tatooine", null, Terrain.init().addVegetation("desert")))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field climate can not be null");
	}
	
	@Test
	public void shouldIssueErrorForPlanetEmptyClimate() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of("Tatooine", Climate.init(), Terrain.init().addVegetation("desert")))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field climate can not be empty");
	}
	
	@Test
	public void shouldIssueErrorForPlanetNullTerrain() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of("Tatooine", Climate.init().addTemperature("arid"), null))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field terrain can not be null");
	}
	
	@Test
	public void shouldIssueErrorForPlanetEmptyTerrain() throws ParseException {
		assertThatThrownBy(() ->
				Planet.of("Tatooine", Climate.init().addTemperature("arid"), Terrain.init()))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field terrain can not be empty");
	}
	
}
