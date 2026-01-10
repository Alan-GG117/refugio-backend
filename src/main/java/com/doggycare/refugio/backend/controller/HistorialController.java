package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.dto.HistorialDTO;
import com.doggycare.refugio.backend.entity.HistorialMedico;
import com.doggycare.refugio.backend.entity.Mascota;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.HistorialRepository;
import com.doggycare.refugio.backend.repository.MascotaRepository;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialController {
    @Autowired
    private HistorialRepository historialRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. OBTENER HISTORIAL DE UNA MASCOTA
    @GetMapping("/mascota/{id}")
    public List<HistorialMedico> obtenerPorMascota(@PathVariable Long id) {
        return historialRepository.findByMascotaId(id);
    }

    // 2. REGISTRAR UN EVENTO MÉDICO
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody HistorialDTO dto) {
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId()).orElse(null);
        Usuario vet = usuarioRepository.findById(dto.getVeterinarioId()).orElse(null);

        if (mascota == null || vet == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Mascota o Veterinario no encontrados"));
        }

        HistorialMedico historial = new HistorialMedico();
        historial.setTipo(dto.getTipo());
        historial.setDescripcion(dto.getDescripcion());
        historial.setFecha(dto.getFecha() != null ? dto.getFecha() : java.time.LocalDate.now());
        historial.setMascota(mascota);
        historial.setVeterinario(vet);

        historialRepository.save(historial);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Historial actualizado"));
    }
}
