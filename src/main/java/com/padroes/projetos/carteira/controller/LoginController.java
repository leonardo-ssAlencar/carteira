package com.padroes.projetos.carteira.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.excecoes.UsuarioNaoExisteException;
import com.padroes.projetos.carteira.service.AplicacaoFachada;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private AplicacaoFachada fachada;

    @PostMapping("/login")
    public String fazerLogin(@Param("email") String email, @Param("senha") String senha, HttpServletRequest request) {

        Optional<Usuario> user = fachada.validarUsuario(email, senha);

        if (user.isEmpty()) {
            throw new UsuarioNaoExisteException("O usuario não existe ou a senha está incorreta");
        }

        request.getSession().setAttribute("userLogado", user.get());
        request.getSession().setAttribute("grupoUser", user.get().getParente());

        return "redirect:/usuario";

    }

    @GetMapping("/encerrarlogin")
    public String encerrar(HttpServletRequest request) {
        Usuario user = (Usuario) request.getSession().getAttribute("userLogado");
        if (user == null) {
            return "redirect:/";
        }

        request.getSession().removeAttribute("userLogado");
        request.getSession().removeAttribute("grupoUser");
        return "redirect:/";
    }
}
