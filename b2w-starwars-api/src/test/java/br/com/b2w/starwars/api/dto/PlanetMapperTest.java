package br.com.b2w.starwars.api.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import br.com.b2w.starwars.api.domain.Climate;
import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.Terrain;

public class PlanetMapperTest {
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void shouldConvertPlanetDtoToPlanet() {
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
	public void shouldConvertPlanetToDtoNoFilms() {
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
	public void shouldConvertPlanetToDtoWithFilms() throws ParseException {
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
	public void shouldConvertFilmDtoToFilm() throws ParseException {
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

}
