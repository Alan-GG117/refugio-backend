package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.entity.Acreedor;
import com.doggycare.refugio.backend.repository.AcreedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/acreedores")
@CrossOrigin(origins = "*")
public class AcreedorController {

    @Autowired
    private AcreedorRepository  acreedorRepository;

    @GetMapping
    public List<Acreedor> listar() {
        return acreedorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Acreedor acreedor) {
        acreedorRepository.save(acreedor);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Acreedor guardado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        acreedorRepository.deleteById(id);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Acreedor eliminado"));
    }
}
