package br.com.b2w.starwars.api.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.b2w.starwars.api.config.RestClientConfiguration;

@Configuration
@Import(RestClientConfiguration.class)
public class ServiceConfig {
	
	@Bean
	public SWApi swApi(RestTemplate restTemplate, ObjectMapper objectMapper) {
		return new SWApi(restTemplate, objectMapper);
	}

}
