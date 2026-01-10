package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.EEstadoSolicitud;
import com.doggycare.refugio.backend.entity.SolicitudAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudAdopcion, Long> {
    List<SolicitudAdopcion> findByEstado(EEstadoSolicitud estado);
    boolean existsByUsuarioIdAndMascotaId(Long usuarioId, Long mascotaId);
}
