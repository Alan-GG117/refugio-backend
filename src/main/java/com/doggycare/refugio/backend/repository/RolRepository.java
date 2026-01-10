package com.doggycare.refugio.backend.repository;

import com.doggycare.refugio.backend.entity.ERol;
import com.doggycare.refugio.backend.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
     Optional<Rol> findByNombre(ERol eRol);
}
