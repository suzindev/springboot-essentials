package com.suzintech.springboot.service;

import com.suzintech.springboot.domain.Anime;
import com.suzintech.springboot.exception.BadRequestException;
import com.suzintech.springboot.mapper.AnimeMapper;
import com.suzintech.springboot.repository.AnimeRepository;
import com.suzintech.springboot.requests.AnimePostRequestBody;
import com.suzintech.springboot.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    private static final AnimeMapper mapper = AnimeMapper.INSTANCE;

    public List<Anime> listAll() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(mapper.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());

        Anime anime = mapper.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());

        animeRepository.save(anime);
    }
}
