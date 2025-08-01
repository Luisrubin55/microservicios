package com.usuario.service.usuario_service.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.service.usuario_service.entidades.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {    
} 