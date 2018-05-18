package br.com.b2w.starwars.api.controller;

import br.com.b2w.starwars.api.dto.PlanetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public PlanetMapper mapper() {
        return new PlanetMapper();
    }

}
