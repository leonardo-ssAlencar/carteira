package com.padroes.projetos.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;

public interface RepositorioCaixinha extends JpaRepository<Caixinha, Long> {

    @Query("from Caixinha as c where c.fechamento = CURRENT_DATE")
    public List<Caixinha> caixinhasFechadas();
}
