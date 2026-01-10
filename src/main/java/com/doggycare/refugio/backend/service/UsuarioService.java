package com.doggycare.refugio.backend.service;

import com.doggycare.refugio.backend.dto.RegistroCompletoDTO;
import com.doggycare.refugio.backend.entity.Direccion;
import com.doggycare.refugio.backend.entity.ERol;
import com.doggycare.refugio.backend.entity.Rol;
import com.doggycare.refugio.backend.entity.Usuario;
import com.doggycare.refugio.backend.repository.RolRepository;
import com.doggycare.refugio.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método principal para registrar
    @Transactional // Si algo falla a la mitad, no guarda nada (evita datos basura)
    public void registrarUsuario(RegistroCompletoDTO dto) {

        // 1. Validar si el email ya existe
        if (usuarioRepository.existsByEmail(dto.getUsuario().getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Preparar la DIRECCIÓN (Convertir DTO a Entidad)
        Direccion direccion = new Direccion();
        direccion.setCodigoPostal(dto.getDireccion().getCp());
        direccion.setEstado(dto.getDireccion().getEstado());
        direccion.setMunicipioAlcaldia(dto.getDireccion().getMunicipioAlcaldia()); // Ojo aquí con el nombre
        direccion.setColonia(dto.getDireccion().getColonia());
        direccion.setTipoCalle(dto.getDireccion().getTipoCalle());
        direccion.setCalle(dto.getDireccion().getCalle());
        direccion.setNumExterior(dto.getDireccion().getNumExterior());
        direccion.setNumInterior(dto.getDireccion().getNumInterior());
        direccion.setReferencias(dto.getDireccion().getReferencias());

        // 3. Preparar el USUARIO
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getUsuario().getNombre());
        usuario.setPaterno(dto.getUsuario().getApellidoPaterno());
        usuario.setMaterno(dto.getUsuario().getApellidoMaterno());
        usuario.setEmail(dto.getUsuario().getEmail());
        usuario.setTelefono(dto.getUsuario().getTelefono());
        usuario.setOcupacion(dto.getUsuario().getOcupacion());

        // Encriptar la contraseña antes de guardarla
        usuario.setPassword(passwordEncoder.encode(dto.getUsuario().getPassword()));

        // 4. Relacionar Usuario con Dirección
        usuario.setDireccion(direccion);

        // 5. Asignar Rol por defecto (Usuario/Adoptante)
        Rol rolUsuario = rolRepository.findByNombre(ERol.ROLE_ADOPTANTE) // O ROLE_ADOPTANTE según tu Enum
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolUsuario);
        usuario.setRoles(roles);

        // 6. Guardar todo (La dirección se guarda sola gracias al Cascade)
        usuarioRepository.save(usuario);
    }
}