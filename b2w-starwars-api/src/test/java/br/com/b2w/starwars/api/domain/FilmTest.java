package br.com.b2w.starwars.api.domain;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

public class FilmTest {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void shouldInstanceNewFilm() throws ParseException {
        Film film = Film.of("A New Hope",
                        "George Lucas",
                        "Gary Kurtz, Rick McCallum",
                        formatter.parse("1977-05-25"),
                        "https://swapi.co/api/films/1/");

        assertThat(film.getTitle()).isEqualTo("A New Hope");
        assertThat(film.getDirector()).isEqualTo("George Lucas");
        assertThat(film.getProducer()).isEqualTo("Gary Kurtz, Rick McCallum");
        assertThat(formatter.format(film.getReleaseDate())).isEqualTo("1977-05-25");
        assertThat(film.getUrl()).isEqualTo("https://swapi.co/api/films/1/");
    }

    @Test(expected = NullPointerException.class)
    public void shouldIssueErrorForPlanetNullClimate() throws ParseException {
        Film.of(null,
                "George Lucas",
                "Gary Kurtz, Rick McCallum",
                formatter.parse("1977-05-25"),
                "https://swapi.co/api/films/1/");
    }

    @Test(expected = NullPointerException.class)
    public void shouldIssueErrorForPlanetNullTerrain() throws ParseException {
        Film.of("A New Hope",
                "George Lucas",
                "Gary Kurtz, Rick McCallum",
                formatter.parse("1977-05-25"),
                null);
    }

}