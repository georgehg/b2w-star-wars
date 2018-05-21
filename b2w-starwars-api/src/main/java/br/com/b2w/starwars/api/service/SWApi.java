package br.com.b2w.starwars.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.com.b2w.starwars.api.dto.FilmDto;

@Service
public class SWApi {
	
	private final static String SWAPI_URL = "https://swapi.co/api";
	
	private final static String PLANETS_RESOURCE = "/planets/?search={name}";
	
	private HttpEntity<String> entity;

    private final RestTemplate restTemplate;
    
    private final ObjectMapper mapper;

    public SWApi(RestTemplate restTemplate, ObjectMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        
        HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    	headers.set("User-Agent", "GeorgeSilva");
    	entity = new HttpEntity<>("parameters", headers);
    }

    public List<String> getFilmsListforPlanet(String planetName) {
    	ResponseEntity<String> response = restTemplate.exchange(SWAPI_URL + PLANETS_RESOURCE, HttpMethod.GET, entity, String.class, planetName);
    			
    	if  (HttpStatus.valueOf(response.getStatusCodeValue()).is2xxSuccessful()) {
    		return extractFilms(response.getBody());
    	}
    	
    	return null;
    }
    
    public FilmDto getFilm(String filmUrl) {
    	ResponseEntity<FilmDto> response = restTemplate.exchange(filmUrl, HttpMethod.GET, entity, FilmDto.class);
    	
    	if  (HttpStatus.valueOf(response.getStatusCodeValue()).is2xxSuccessful()) {
    		return response.getBody();
    	}
    	
    	return null;
    }
    
    private List<String> extractFilms(String json) {
        try {
        	return mapper.readValue(
        				mapper.readTree(
        						mapper.readValue(
        								mapper.readTree(json)
        										.get("results")
        											.toString(), ArrayNode.class)
        								.get(0).toString())
        						.get("films").toString(),
        				mapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

}
