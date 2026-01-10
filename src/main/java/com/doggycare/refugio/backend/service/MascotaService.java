package com.doggycare.refugio.backend.service;

import com.doggycare.refugio.backend.dto.MascotaDTO;
import com.doggycare.refugio.backend.entity.EEstadoAdopcion;
import com.doggycare.refugio.backend.entity.ESexo;
import com.doggycare.refugio.backend.entity.ETamano;
import com.doggycare.refugio.backend.entity.Mascota;
import com.doggycare.refugio.backend.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<MascotaDTO> findAll() {
        return mascotaRepository.findAll().stream()
                .map(mascota -> convertirADTO(mascota)) // <--- Usamos lambda explícita
                .collect(Collectors.toList());
    }

    // GUARDAR NUEVA
    @Transactional
    public void guardarMascota(MascotaDTO dto) {
        Mascota mascota = new Mascota();
        // Si viene ID, es una actualización
        if (dto.getId() != null) {
            mascota = mascotaRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
        }

        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setPesoActual(dto.getPesoActual());
        mascota.setFechaNacimientoAprox(dto.getFechaNacimientoAprox());
        mascota.setFotoUrl(dto.getFotoUrl());
        mascota.setDescripcionPerfil(dto.getDescripcionPerfil());
        mascota.setAptoParaAdopcion(dto.getAptoParaAdopcion());

        // Conversión segura de Enums (Manejo de errores si envían texto raro)
        try {
            mascota.setTamano(ETamano.valueOf(dto.getTamano()));
            mascota.setSexo(ESexo.valueOf(dto.getSexo()));
            if(dto.getEstadoAdopcion() != null)
                mascota.setEstadoAdopcion(EEstadoAdopcion.valueOf(dto.getEstadoAdopcion()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error en valores de catálogo (Tamaño, Sexo o Estado incorrectos)");
        }

        mascotaRepository.save(mascota);
    }

    // Método auxiliar para convertir Entidad -> DTO
    private MascotaDTO convertirADTO(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setEspecie(m.getEspecie());
        dto.setRaza(m.getRaza());
        dto.setTamano(m.getTamano().name());
        dto.setSexo(m.getSexo().name());
        dto.setPesoActual(m.getPesoActual());
        dto.setFotoUrl(m.getFotoUrl());
        dto.setDescripcionPerfil(m.getDescripcionPerfil());
        dto.setEstadoAdopcion(m.getEstadoAdopcion().name());
        return dto;
    }
}
