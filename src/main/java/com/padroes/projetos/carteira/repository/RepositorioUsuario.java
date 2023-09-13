package com.padroes.projetos.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findOneByEmail(String email);

}
