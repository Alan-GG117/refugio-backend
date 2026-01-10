package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepository extends JpaRepository<HistorialMedico, Long> {
    List<HistorialMedico> findByMascotaId(Long mascotaId);
}
