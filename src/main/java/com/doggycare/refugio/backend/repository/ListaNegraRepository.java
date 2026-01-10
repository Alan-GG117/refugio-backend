package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.ListaNegra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListaNegraRepository extends JpaRepository<ListaNegra, Long> {
    Optional<ListaNegra> findByEmail(String email);
}
