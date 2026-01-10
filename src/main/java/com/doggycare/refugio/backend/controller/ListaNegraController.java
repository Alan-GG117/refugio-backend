package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.dto.ListaNegraDTO;
import com.doggycare.refugio.backend.entity.ListaNegra;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.ListaNegraRepository;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/lista-negra")
@CrossOrigin(origins = "*")
public class ListaNegraController {
    @Autowired
    private ListaNegraRepository listaNegraRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<ListaNegra> listar() {
        return listaNegraRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<?> bloquear(@RequestBody ListaNegraDTO dto) {
        ListaNegra item = new ListaNegra();
        item.setNombre(dto.getNombre());
        item.setApellidoPaterno(dto.getApellidoPaterno());
        item.setApellidoMaterno(dto.getApellidoMaterno());
        item.setEmail(dto.getEmail());
        item.setMotivo(dto.getMotivo());

        if (dto.getAdminId() != null) {
            Usuario admin = usuarioRepository.findById(dto.getAdminId()).orElse(null);
            item.setRegistradoPor(admin);
        }

        listaNegraRepository.save(item);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Usuario añadido a lista negra"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> desbloquear(@PathVariable Long id) {
        listaNegraRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Usuario desbloqueado"));
    }
}
