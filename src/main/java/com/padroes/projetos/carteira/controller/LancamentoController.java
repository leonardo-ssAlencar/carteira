package com.padroes.projetos.carteira.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.padroes.projetos.carteira.model.entidades.commands.CreditoCommand;
import com.padroes.projetos.carteira.model.entidades.commands.DebitoCommand;
import com.padroes.projetos.carteira.model.entidades.commands.LancamentoCommand;
import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.entidades.lancamento.LancamentoFactory;
import com.padroes.projetos.carteira.model.entidades.lancamento.LancamentoSemItemsFactory;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;
import com.padroes.projetos.carteira.model.excecoes.LancamentoNaoSuportadoException;
import com.padroes.projetos.carteira.service.AplicacaoFachada;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LancamentoController {

    @Autowired
    AplicacaoFachada fachada;

    @PostMapping("grupo/{id}/realizarLancamento")
    public String registrarLancamento(HttpServletRequest request, @PathVariable("id") Long id,
            @RequestParam("mensagem") String mensagem, @RequestParam("valor") BigDecimal valor,
            @RequestParam("operacao") OperacoesEnum operacoes) {

        Usuario logado = (Usuario) request.getSession().getAttribute("userLogado");
        if (logado == null) {
            return "redirect:/";
        }

        Grupo grupo = fachada.buscarGrupo(id);
        LancamentoFactory factory;

        // if (itens) {
        // factory = new LancamentoComItemsFactory(lItens);
        // } else {
        factory = new LancamentoSemItemsFactory();

        // }

        LancamentoCommand command;

        switch (operacoes) {
            case RECEITA:
                command = new CreditoCommand(grupo, valor, logado, mensagem, factory);
                break;

            case DESPESA:
                command = new DebitoCommand(grupo, valor, logado, mensagem, factory);
                break;

            default:
                throw new LancamentoNaoSuportadoException("O lancamento não é suportado");

        }

        command.executar();
        fachada.salvarLancamento(command.getLancamento());

        return "redirect:/grupo/" + id;

    }

    @GetMapping("/grupo/{id}/novo_lancamento")
    public String novoLancamento(HttpServletRequest request, Model model, @PathVariable("id") Long id) {

        Usuario logado = (Usuario) request.getSession().getAttribute("userLogado");
        if (logado == null) {
            return "redirect:/";
        }

        Grupo grupoAtual = fachada.buscarGrupo(id);

        model.addAttribute("grupo", grupoAtual);

        model.addAttribute("operacoes", OperacoesEnum.values());

        return "novo_lancamento";

    }

}
