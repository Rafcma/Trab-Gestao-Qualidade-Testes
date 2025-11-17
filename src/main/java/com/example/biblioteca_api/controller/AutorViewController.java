package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.repository.AutorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autores")
public class AutorViewController {

    private final AutorRepository repo;

    public AutorViewController(AutorRepository repo) {
        this.repo = repo;
    }

    // exibe a lista de todos os autores
    @GetMapping("/view")
    public String listar(Model model) {
        model.addAttribute("autores", repo.findAll());
        return "autores-lista";
    }

    // exibe o formulário para criar novo autor
    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores-form";
    }

    // exibe o formulário para editar autor existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Autor autor = repo.findById(id).orElse(new Autor());
        model.addAttribute("autor", autor);
        return "autores-form";
    }

    // salva ou atualiza um autor
    @PostMapping("/salvar")
    public String salvar(Autor autor) {
        repo.save(autor);
        return "redirect:/autores/view";
    }

    // deleta um autor e redireciona para a lista
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/autores/view";
    }
}
