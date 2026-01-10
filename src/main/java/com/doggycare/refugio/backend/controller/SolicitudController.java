package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.dto.SolicitudDTO;
import com.doggycare.refugio.backend.entity.EEstadoSolicitud;
import com.doggycare.refugio.backend.entity.Mascota;
import com.doggycare.refugio.backend.entity.SolicitudAdopcion;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.MascotaRepository;
import com.doggycare.refugio.backend.repository.SolicitudRepository;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;

    @PostMapping
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudDTO dto) {
        // 1. Validar que no haya pedido esta mascota antes
        if (solicitudRepository.existsByUsuarioIdAndMascotaId(dto.getUsuarioId(), dto.getMascotaId())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Ya has enviado una solicitud para esta mascota."));
        }

        // 2. Buscar objetos reales en la BD
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElse(null);
        Mascota mascota = mascotaRepository.findById(dto.getMascotaId()).orElse(null);

        if (usuario == null || mascota == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Usuario o Mascota no encontrados"));
        }

        // 3. Crear y Guardar la Solicitud
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setUsuario(usuario);
        solicitud.setMascota(mascota);
        solicitud.setComentarios(dto.getComentarios());

        solicitudRepository.save(solicitud);

        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Solicitud registrada con éxito"));
    }

    // 1. LISTAR TODAS (Para el Admin)
    @GetMapping
    public List<SolicitudAdopcion> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    // 2. CAMBIAR ESTADO (Aprobar/Rechazar)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            SolicitudAdopcion solicitud = solicitudRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

            // Convertimos el texto (ej: "APROBADA") al Enum
            solicitud.setEstado(EEstadoSolicitud.valueOf(estado));
            solicitudRepository.save(solicitud);

            return ResponseEntity.ok(Collections.singletonMap("mensaje", "Estado actualizado a " + estado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
