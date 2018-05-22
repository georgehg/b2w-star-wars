package br.com.b2w.starwars.api.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;
import br.com.b2w.starwars.api.exceptions.PlanetValidationException;

public class PlanetMapperTest {
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void shouldConvertDtoToPlanet() throws PlanetValidationException {
		PlanetDto planetDto = PlanetDto.of(null,
										"Alderaan",
										Sets.newSet("temperate"),
										Sets.newSet("grasslands"),
										null);

		Planet planet = PlanetMapper.dtoToPlanet(planetDto);
		assertThat(planet.getName()).isEqualTo("Alderaan");
		assertThat(planet.getClimate().getTemperatures()).contains("temperate");
		assertThat(planet.getTerrain().getVegetations()).contains("grasslands");
	}

	@Test
	public void shouldConvertPlanetToDtoNoFilms() throws PlanetValidationException {
		Planet planet = Planet.of("Tatooine",
									Climate.init().addTemperature("arid"),
									Terrain.init().addVegetation("desert"));

		PlanetDto dto = PlanetMapper.planetToDto(planet);
		assertThat(dto.getName()).isEqualTo("Tatooine");
		assertThat(dto.getClimate()).contains("arid");
		assertThat(dto.getTerrain()).contains("desert");
		assertThat(dto.getFilms()).isEmpty();
	}
	
	@Test
	public void shouldConvertPlanetToDtoWithFilms() throws ParseException, PlanetValidationException {
		Film film = Film.of("A New Hope",
			                "George Lucas",
			                "Gary Kurtz, Rick McCallum",
			                formatter.parse("1977-05-25"),
			                "https://swapi.co/api/films/1/");
		
		Planet planet = Planet.of("Tatooine",
									Climate.init().addTemperature("arid"),
									Terrain.init().addVegetation("desert"),
									Sets.newSet(film));

		PlanetDto dto = PlanetMapper.planetToDto(planet);
		assertThat(dto.getName()).isEqualTo("Tatooine");
		assertThat(dto.getClimate()).contains("arid");
		assertThat(dto.getTerrain()).contains("desert");
		assertThat(dto.getFilms()).isNotEmpty();
		assertThat(dto.getFilms().stream().findFirst().get().getTitle()).isEqualTo("A New Hope");
		
	}
	
	@Test
	public void shouldConvertDtoToFilm() throws ParseException {
		FilmDto dto = FilmDto.of("A New Hope",
				                "George Lucas",
				                "Gary Kurtz, Rick McCallum",
				                formatter.parse("1977-05-25"),
				                "https://swapi.co/api/films/1/");
		
		Film film = PlanetMapper.dtoToFilm(dto);
		assertThat(film.getTitle()).isEqualTo("A New Hope");
        assertThat(film.getDirector()).isEqualTo("George Lucas");
        assertThat(film.getProducer()).isEqualTo("Gary Kurtz, Rick McCallum");
        assertThat(formatter.format(film.getReleaseDate())).isEqualTo("1977-05-25");
        assertThat(film.getUrl()).isEqualTo("https://swapi.co/api/films/1/");
	}
	
	@Test
	public void shouldIssueErrorDtoToPlanetNullName() {
		PlanetDto planetDto = PlanetDto.of(null,
										null,
										Sets.newSet("temperate"),
										Sets.newSet("grasslands"),
										null);
		
		assertThatThrownBy(() -> PlanetMapper.dtoToPlanet(planetDto))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field name can not be null");
	}
	
	@Test
	public void shouldIssueErrorDtoToPlanetEmptyName() {
		PlanetDto planetDto = PlanetDto.of(null,
										"",
										Sets.newSet("temperate"),
										Sets.newSet("grasslands"),
										null);

		assertThatThrownBy(() -> PlanetMapper.dtoToPlanet(planetDto))
		.isInstanceOf(PlanetValidationException.class)
		.hasMessage("Field name can not be empty");
	}

	@Test
	public void shouldIssueErrorDtoToPlanetNullClimate() {
		PlanetDto planetDto = PlanetDto.of(null,
				"Alderaan",
				null,
				Sets.newSet("grasslands"),
				null);

		assertThatThrownBy(() -> PlanetMapper.dtoToPlanet(planetDto))
				.isInstanceOf(PlanetValidationException.class)
				.hasMessage("Field climate can not be empty");
	}

	@Test
	public void shouldIssueErrorDtoToPlanetNullTerrain() {
		PlanetDto planetDto = PlanetDto.of(null,
				"Alderaan",
				Sets.newSet("temperate"),
				null,
				null);

		assertThatThrownBy(() -> PlanetMapper.dtoToPlanet(planetDto))
				.isInstanceOf(PlanetValidationException.class)
				.hasMessage("Field terrain can not be empty");
	}

}