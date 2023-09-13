package com.padroes.projetos.carteira.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.padroes.projetos.carteira.model.excecoes.UsuarioNaoExisteException;

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(UsuarioNaoExisteException.class)
    public String login(Model model, UsuarioNaoExisteException err) {

        model.addAttribute("erro", err.getMessage());
        return "login";
    }

}
