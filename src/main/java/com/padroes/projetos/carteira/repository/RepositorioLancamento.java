package com.padroes.projetos.carteira.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;

@Repository
public interface RepositorioLancamento extends JpaRepository<Lancamento, Long> {

    @Query("SELECT l.valor FROM Lancamento as l where l.usuario = :id")
    List<BigDecimal> valoresLancamentos(@Param("id") Usuario id);

    @Query("SELECT l.valor FROM Lancamento as l where l.caixinha = :id")
    List<BigDecimal> valoresLancamentos(@Param("id") Caixinha id);

    @Query("FROM Lancamento as l WHERE l.caixinha = :id")
    List<Lancamento> lancamentos(@Param("id") Caixinha id);

    @Query("FROM Lancamento as l WHERE l.usuario = :id")
    List<Lancamento> lancamentos(@Param("id") Usuario id);

}
