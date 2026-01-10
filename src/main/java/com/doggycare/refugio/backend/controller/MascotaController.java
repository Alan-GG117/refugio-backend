package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.dto.MascotaDTO;
import com.doggycare.refugio.backend.repository.MascotaRepository;
import com.doggycare.refugio.backend.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "*")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping
    public List<MascotaDTO> findAll() {
        return mascotaService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> guardarMascota(@RequestBody MascotaDTO mascotaDTO) {
        try {
            mascotaService.guardarMascota(mascotaDTO);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Mascota guardada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMascota(@PathVariable Long id) {
        try {
            mascotaRepository.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Mascota eliminada"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No se pudo eliminar"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascota(@PathVariable Long id, @RequestBody MascotaDTO mascotaDTO) {
        try {
            // Aseguramos que el ID del DTO coincida con el de la URL
            mascotaDTO.setId(id);
            mascotaService.guardarMascota(mascotaDTO);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Mascota actualizada"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No se pudo actualizar"));
        }
    }
}
