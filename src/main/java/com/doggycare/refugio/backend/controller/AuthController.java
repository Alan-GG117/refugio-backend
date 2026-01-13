package com.doggycare.refugio.backend.controller;

import com.doggycare.refugio.backend.dto.LoginRequestDTO;
import com.doggycare.refugio.backend.dto.RegistroCompletoDTO;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import com.doggycare.refugio.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Endpoint: Post /api/auth/resgistro
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroCompletoDTO dto) {
        try {
            usuarioService.registrarUsuario(dto);
            return ResponseEntity.ok(Collections.singletonMap("message", "Usuario registrado exitosamente"));
        } catch (RuntimeException e) {
            //Error 400
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            //Error 500
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //Endpoint 2: Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()).orElse(null);

        //Validacion de credenciales
        if(usuario == null || !passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            return  ResponseEntity.status(401)
                    .body(Collections.singletonMap("error", "Credenciales invalidas (Email o Password incorrectos)"));
        }

        Map<String,Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Login exitoso");
        respuesta.put("usuarioId", usuario.getId());
        respuesta.put("nombre", usuario.getNombre());
        respuesta.put("email", usuario.getEmail());

        // --- CORRECCIÓN ---
        // Verificamos si la lista de roles tiene elementos antes de intentar acceder
        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            respuesta.put("rol", usuario.getRoles().iterator().next().getNombre());
        } else {
            // Rol por defecto si el usuario no tiene ninguno asignado en BD
            respuesta.put("rol", "ROLE_ADOPTANTE");
        }
        // ------------------

        return ResponseEntity.ok(respuesta);
    }
}