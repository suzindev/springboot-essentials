package com.suzintech.springboot.repository;

import com.suzintech.springboot.domain.TechUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechUserRepository extends JpaRepository<TechUser, Long> {

    TechUser findByUsername(String name);
}
