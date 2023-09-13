package com.padroes.projetos.carteira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.GrupoFachada;
import com.padroes.projetos.carteira.repository.RepositorioCaixinha;
import com.padroes.projetos.carteira.repository.RepositorioGrupo;

@Service
public class GrupoFachadaService {

    @Autowired
    GrupoFachada gFachada;
    @Autowired
    RepositorioCaixinha caixinhaRepo;
    @Autowired
    RepositorioGrupo grupoRepo;

    /**
     * Cadastra um grupo
     * 
     * @param grupo
     * @return
     */
    public Grupo cadastrarGrupo(Grupo grupo) {

        caixinhaRepo.save(grupo.getCaixinha());

        grupo = grupoRepo.save(grupo);

        // cadastrarParticipante(grupo, (Grupo) grupo.getDono().getParente());

        return grupo;

    }

}
