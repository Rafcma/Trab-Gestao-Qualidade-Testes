package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.repository.EditoraRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editoras")
public class EditoraViewController {

    private final EditoraRepository repo;

    public EditoraViewController(EditoraRepository repo) {
        this.repo = repo;
    }

    // exibe a lista de todas as editoras
    @GetMapping("/view")
    public String listar(Model model) {
        model.addAttribute("editoras", repo.findAll());
        return "editoras-lista";
    }

    // exibe o formulário para criar nova editora
    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("editora", new Editora());
        return "editoras-form";
    }

    // exibe o formulário para editar editora existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Editora editora = repo.findById(id).orElse(new Editora());
        model.addAttribute("editora", editora);
        return "editoras-form";
    }

    // salva ou atualiza uma editora
    @PostMapping("/salvar")
    public String salvar(Editora editora) {
        repo.save(editora);
        return "redirect:/editoras/view";
    }

    // deleta uma editora e redireciona para a lista
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/editoras/view";
    }
}
