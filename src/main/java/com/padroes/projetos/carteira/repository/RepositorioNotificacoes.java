
package com.padroes.projetos.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

public interface RepositorioNotificacoes extends JpaRepository<Notificacoes, Long> {

    @Query("FROM Notificacoes WHERE usuario = :user")
    public List<Notificacoes> notificacaoUsuario(@Param("user") Usuario user);

}
