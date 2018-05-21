package br.com.b2w.starwars.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import br.com.b2w.starwars.api.AbstractIntegrationTest;
import br.com.b2w.starwars.api.dto.FilmDto;

@Import(ServiceConfig.class)
public class SWApiIT extends AbstractIntegrationTest {
	
	@Autowired
	private SWApi swApi;
	
	@Test
	public void shouldReturnFilmsListForPlanet() {
		List<String> films = swApi.getFilmsListforPlanet("Tatooine");
		assertThat(films).isNotEmpty();
	}
	
	@Test
	public void shouldReturnFilmForUrl() {
		FilmDto film = swApi.getFilm("https://swapi.co/api/films/5/");
		assertThat(film).isNotNull();
		assertThat(film.getTitle()).isEqualTo("Attack of the Clones");
	}

}
