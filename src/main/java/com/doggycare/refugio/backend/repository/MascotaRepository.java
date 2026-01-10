package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.EEstadoAdopcion;
import com.doggycare.refugio.backend.entity.ETamano;
import com.doggycare.refugio.backend.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByEstadoAdopcion(EEstadoAdopcion estado);

    List<Mascota> findByEspecieAndTamano(String especie, ETamano tamano);
}
