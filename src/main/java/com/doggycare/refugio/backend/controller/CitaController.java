package com.doggycare.refugio.backend.controller;


import com.doggycare.refugio.backend.entity.Cita;
import com.doggycare.refugio.backend.entity.EEstadoCita;
import com.doggycare.refugio.backend.entity.Mascota;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.CitaRepository;
import com.doggycare.refugio.backend.repository.MascotaRepository;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    @Autowired
    private CitaRepository citaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private MascotaRepository mascotaRepository;

    @GetMapping
    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    // DTO interno para recibir los datos simples
    public static class CitaDTO {
        public Long adoptanteId;
        public Long mascotaId;
        public java.time.LocalDateTime fechaHora;
        public String comentarios;
    }

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody CitaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.adoptanteId).orElse(null);
        Mascota mascota = null;
        if(dto.mascotaId != null) {
            mascota = mascotaRepository.findById(dto.mascotaId).orElse(null);
        }

        if (usuario == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Usuario no encontrado"));
        }

        Cita cita = new Cita();
        cita.setAdoptante(usuario);
        cita.setMascota(mascota);
        cita.setFechaHora(dto.fechaHora);
        cita.setComentarios(dto.comentarios);

        citaRepository.save(cita);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Cita agendada"));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam EEstadoCita nuevoEstado) {
        Cita cita = citaRepository.findById(id).orElse(null);
        if (cita == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Cita no encontrada"));
        }

        cita.setEstado(nuevoEstado);
        citaRepository.save(cita);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Estado actualizado a " + nuevoEstado));
    }
}
