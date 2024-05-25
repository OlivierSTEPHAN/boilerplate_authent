package com.zytoune.boilerplate_authent.repository;

import com.zytoune.boilerplate_authent.entity.User;
import com.zytoune.boilerplate_authent.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    Optional<Validation> findByCode(String code);

    Optional<Validation> findByUser(User user);

    void deleteAllByExpirationBefore(Instant now);
}
