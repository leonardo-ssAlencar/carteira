package com.padroes.projetos.carteira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.GrupoComponent;
import com.padroes.projetos.carteira.model.entidades.grupo.Participante;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

public interface RepositorioParticipante extends JpaRepository<Participante, Long> {

    @Query("FROM Participante where grupo = :grupo")
    public List<Participante> participantes(@Param("grupo") Grupo grupo);

    @Query("FROM Participante where componente = :componente")
    public Optional<Participante> participante(@Param("componente") GrupoComponent component);

    @Query("FROM Participante WHERE grupo = :grupo AND componente = :usuario")
    public Optional<Participante> participantesGrupo(@Param("grupo") Grupo grupo, @Param("usuario") Usuario user);

    @Query("FROM Participante WHERE grupo = :grupo AND componente = :usuario")
    public Optional<Participante> participantesGrupo(@Param("grupo") Grupo grupo, @Param("usuario") Grupo user);

}
