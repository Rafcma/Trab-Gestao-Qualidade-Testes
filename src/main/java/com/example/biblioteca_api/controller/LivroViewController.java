package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroViewController {

    private final LivroRepository livros;
    private final AutorRepository autores;
    private final EditoraRepository editoras;

    public LivroViewController(LivroRepository livros,
                               AutorRepository autores,
                               EditoraRepository editoras) {
        this.livros = livros;
        this.autores = autores;
        this.editoras = editoras;
    }

    // exibe a lista de todos os livros
    @GetMapping("/view")
    public String listar(Model model) {
        model.addAttribute("livros", livros.findAll());
        return "livros-lista";
    }

    // exibe o formulário para criar novo livro
    @GetMapping("/formulario")
    public String formulario(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("autores", autores.findAll());
        model.addAttribute("editoras", editoras.findAll());
        return "livros-form";
    }

    // exibe o formulário para editar livro existente
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Livro livro = livros.findById(id).orElse(new Livro());
        model.addAttribute("livro", livro);
        model.addAttribute("autores", autores.findAll());
        model.addAttribute("editoras", editoras.findAll());
        return "livros-form";
    }

    // salva ou atualiza um livro vinculando autor e editora
    @PostMapping("/salvar")
    public String salvar(@RequestParam(required = false) Long id,
                         @RequestParam String titulo,
                         @RequestParam(required = false) Integer anoPublicacao,
                         @RequestParam(required = false) String isbn,
                         @RequestParam Long autorId,
                         @RequestParam Long editoraId) {

        Livro livro = (id != null) ? livros.findById(id).orElse(new Livro()) : new Livro();

        livro.setTitulo(titulo);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setIsbn(isbn);

        Autor autor = autores.findById(autorId).orElse(null);
        Editora editora = editoras.findById(editoraId).orElse(null);
        livro.setAutor(autor);
        livro.setEditora(editora);

        livros.save(livro);
        return "redirect:/livros/view";
    }

    // deleta um livro e redireciona para a lista
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        livros.deleteById(id);
        return "redirect:/livros/view";
    }
}
