package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.dto.LivroDTO;
import com.example.biblioteca_api.exception.NotFoundException;
import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraLivroCount;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository livros;
    private final AutorRepository autores;
    private final EditoraRepository editoras;

    public LivroController(LivroRepository livros, AutorRepository autores, EditoraRepository editoras) {
        this.livros = livros;
        this.autores = autores;
        this.editoras = editoras;
    }

    // cria um novo livro vinculando autor e editora
    @PostMapping
    public Livro criar(@Valid @RequestBody LivroDTO dto) {
        Autor autor = autores.findById(dto.getAutorId())
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));
        Editora editora = editoras.findById(dto.getEditoraId())
                .orElseThrow(() -> new NotFoundException("Editora não encontrada"));

        Livro l = new Livro();
        l.setTitulo(dto.getTitulo());
        l.setAnoPublicacao(dto.getAnoPublicacao());
        l.setIsbn(dto.getIsbn());
        l.setAutor(autor);
        l.setEditora(editora);

        return livros.save(l);
    }

    // retorna todos os livros cadastrados
    @GetMapping
    public List<Livro> listar() {
        return livros.findAll();
    }

    // busca um livro específico por id
    @GetMapping("/{id}")
    public Livro buscar(@PathVariable Long id) {
        return livros.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado"));
    }

    // atualiza os dados de um livro existente
    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @Valid @RequestBody LivroDTO dto) {
        Livro l = buscar(id);
        Autor autor = autores.findById(dto.getAutorId())
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));
        Editora editora = editoras.findById(dto.getEditoraId())
                .orElseThrow(() -> new NotFoundException("Editora não encontrada"));

        l.setTitulo(dto.getTitulo());
        l.setAnoPublicacao(dto.getAnoPublicacao());
        l.setIsbn(dto.getIsbn());
        l.setAutor(autor);
        l.setEditora(editora);

        return livros.save(l);
    }

    // remove um livro do banco de dados
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Livro l = buscar(id);
        livros.delete(l);
    }

}
