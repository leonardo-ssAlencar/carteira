package com.padroes.projetos.carteira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.service.AplicacaoFachada;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NotificacaoController {

    @Autowired
    AplicacaoFachada fachada;

    @GetMapping("/notificacao")
    public String notificacaoPag(HttpServletRequest request, Model model) {

        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");

        if (user == null) {

            return "redirect:/";

        }

        List<Notificacoes> notificacoes = fachada.notificacoes(user);

        model.addAttribute("notificacoes", notificacoes);

        return "notificacoes";

    }

}
