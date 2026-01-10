package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByAdoptanteId(Long id);
}
