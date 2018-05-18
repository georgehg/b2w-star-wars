package br.com.b2w.starwars.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    public RestTemplate restTemplate(ObjectMapper mapper) {
        //MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        //halConverter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json,application/json"));
        //halConverter.setObjectMapper(mapper);

        RestTemplate template = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        template.setRequestFactory(requestFactory);

        return template;
    }

    /*@Bean
    public ObjectMapper halMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());
        return mapper;
    }*/

}