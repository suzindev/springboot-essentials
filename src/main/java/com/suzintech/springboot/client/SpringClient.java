package com.suzintech.springboot.client;

import com.suzintech.springboot.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object);

        Anime[] anime = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(anime));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        log.info(exchange.getBody());

        Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
        log.info("Saved anime {}", kingdomSaved);

        Anime samurai = Anime.builder().name("Samurai").build();
        ResponseEntity<Anime> samuraiSaved = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST,
                new HttpEntity<>(samurai, createJsonHeader()), Anime.class);
        log.info("Saved anime {}", samuraiSaved);

        Anime animeToBeUpdate = samuraiSaved.getBody();
        animeToBeUpdate.setName("Samurai 2");
        ResponseEntity<Void> samuraiUpdated = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdate, createJsonHeader()), Void.class);
        log.info(samuraiUpdated);

        ResponseEntity<Void> samuraiDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE,
                null, Void.class, animeToBeUpdate.getId());
        log.info(samuraiDelete);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
