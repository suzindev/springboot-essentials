package com.suzintech.springboot.repository;

import com.suzintech.springboot.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
