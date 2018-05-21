package br.com.b2w.starwars.api.service;

import br.com.b2w.starwars.api.domain.Film;
import br.com.b2w.starwars.api.domain.Planet;
import br.com.b2w.starwars.api.domain.PlanetSavedEvent;
import br.com.b2w.starwars.api.dto.FilmDto;
import br.com.b2w.starwars.api.dto.PlanetMapper;
import br.com.b2w.starwars.api.repository.PlanetRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PlanetSavedProcessor {

    private final PlanetRepository planetRepo;

    private final SWApi swApi;

    public PlanetSavedProcessor(PlanetRepository planetRepo, SWApi swApi) {
        this.planetRepo = planetRepo;
        this.swApi = swApi;
    }

    @Async
    @TransactionalEventListener
    public void updatePlanetFilmsList(PlanetSavedEvent event) {
        Set<Film> films = swApi.getFilmsListforPlanet(event.getName())
                .stream()
                .map(url -> CompletableFuture.supplyAsync(getFilmSuplier(url)))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .map(PlanetMapper::dtoToFilm)
                .collect(Collectors.toSet());

        Planet planet = planetRepo.findByName(event.getName());
        planet.addFilms(films);
        planetRepo.save(planet);
    }

    private Supplier<FilmDto> getFilmSuplier(String url) {
        return () -> swApi.getFilm(url);
    }
}
