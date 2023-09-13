package com.padroes.projetos.carteira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;

@Controller
public class Indice {

    @GetMapping("/")
    public String paginaInicial() {
        return "inicial";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        Usuario user = new Usuario();

        model.addAttribute("user", user);
        return "cadastro";
    }

    @GetMapping("/login")
    public String login() {
        return "login";

    }

}
