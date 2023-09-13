package com.padroes.projetos.carteira.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.padroes.projetos.carteira.model.entidades.caixinha.CaixinhaBuilder;
import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.grupo.GrupoFachada;
import com.padroes.projetos.carteira.model.entidades.grupo.Participante;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;
import com.padroes.projetos.carteira.model.excecoes.DataAnteriorException;
import com.padroes.projetos.carteira.model.excecoes.NomeNuloException;
import com.padroes.projetos.carteira.service.AplicacaoFachada;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GrupoController {

    @Autowired
    private AplicacaoFachada fachada;
    @Autowired
    private ControllerService service;
    @Autowired
    private GrupoFachada gFachada;

    @GetMapping("/grupo/{id}")
    public String grupo(HttpServletRequest request, Model model, @PathVariable("id") Long id) {

        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        if (user == null) {
            return "redirect:/";
        }

        Grupo grupoAtual = fachada.buscarGrupo(id);

        Participante userLogparticipante = null;

        for (Participante p : grupoAtual.getParticipantes()) {

            if (p.getParticipante().equals(user)) {
                userLogparticipante = p;
                break;
            }
        }

        if (userLogparticipante == null) {
            return "redirect:/usuario";

        }

        BigDecimal valorTotal = service.calcularValorTotal(
                grupoAtual.getCaixinha().getLancamentos().stream().map(Lancamento::getValor).toList());

        model.addAttribute("grupo", grupoAtual);
        model.addAttribute("saldo", valorTotal);
        model.addAttribute("userLogParticipante", userLogparticipante);
        model.addAttribute("participantes", grupoAtual.getParticipantes());
        model.addAttribute("lancamentos", grupoAtual.getCaixinha().getLancamentos());

        return "grupo";

    }

    @GetMapping("/grupo/{id}/remover_grupo")
    public String deletarGrupo(HttpServletRequest request, @PathVariable("id") Long id) {
        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        if (user == null) {
            return "redirect:/";
        }
        Grupo grupo = fachada.buscarGrupo(id);

        List<Participante> participantes = fachada.participantes(grupo);
        List<Lancamento> lancamentos = fachada.lancamentos(grupo);

        fachada.deletarParticipantes(participantes);
        fachada.deletarLancamentos(lancamentos);

        fachada.deletarGrupo(grupo);

        return "redirect:/usuario";
    }

    @GetMapping("/grupo/{id}/sair")
    public String sairDoGrupo(HttpServletRequest request, @PathVariable("id") Long id) {
        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        Grupo grupoUser = (Grupo) request.getSession().getAttribute("grupoUser");
        if (user == null) {
            return "redirect:/";
        }

        Grupo grupo = fachada.buscarGrupo(id);

        if (user.equals(grupo.getDono())) {
            deletarGrupo(request, id);
            return "redirect:/usuario";
        }

        Participante participante = fachada.participante(user, grupo);
        Participante gParticipante = fachada.participante(grupo, grupoUser);

        fachada.deletarParticipante(participante);
        fachada.deletarParticipante(gParticipante);

        request.getSession().setAttribute("grupoUser", fachada.buscarGrupo(grupoUser.getId()));

        return "redirect:/usuario";

    }

    @GetMapping("/novo_grupo")
    public String nGrupo(HttpServletRequest request) {
        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        if (user == null) {
            return "redirect:/";
        }
        return "novo_grupo";
    }

    @PostMapping("/novo_grupo")
    public String novoGrupo(HttpServletRequest request, @RequestParam("nomeGrupo") String nomeGrupo,
            @RequestParam("data") String data, @RequestParam("eMensal") Boolean eMensal) {
        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        if (user == null) {
            return "redirect:/";
        }

        if (nomeGrupo.equals("") || nomeGrupo == null) {
            throw new NomeNuloException("O nome não pode ser nulo");

        }

        Grupo grupo = gFachada.criarGrupo(nomeGrupo, user);
        CaixinhaBuilder builder = new CaixinhaBuilder();

        builder.eMensal(eMensal);

        if (!data.toLowerCase().equals("null")) {
            LocalDate lData = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (lData.isBefore(LocalDate.now())) {
                throw new DataAnteriorException("A data tem que ser numa data posterior");
            }
            builder.fechamento(lData);

        }

        grupo.setCaixinha(builder.build());

        Grupo grupo2 = fachada.cadastrarGrupo(grupo);
        fachada.cadastrarParticipante(grupo2, (Grupo) user.getParente());

        return "redirect:/usuario";
    }

}
