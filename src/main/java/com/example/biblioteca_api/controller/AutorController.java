package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.exception.DataIntegrityException;
import com.example.biblioteca_api.exception.NotFoundException;
import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorRepository repo;
    private final LivroRepository livroRepository;

    public AutorController(AutorRepository repo, LivroRepository livroRepository) {
        this.repo = repo;
        this.livroRepository = livroRepository;
    }

    // cria um novo autor no banco de dados
    @PostMapping
    public Autor criar(@Valid @RequestBody Autor autor) {
        return repo.save(autor);
    }

    // retorna todos os autores cadastrados
    @GetMapping
    public List<Autor> listar() {
        return repo.findAll();
    }

    // busca um autor específico por id
    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado"));
    }

    // atualiza os dados de um autor existente
    @PutMapping("/{id}")
    public Autor atualizar(@PathVariable Long id, @Valid @RequestBody Autor dados) {
        Autor a = buscar(id);
        a.setNome(dados.getNome());
        a.setPais(dados.getPais());
        return repo.save(a);
    }

    // remove um autor do banco de dados
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Autor a = buscar(id);

        List<com.example.biblioteca_api.model.Livro> livros = livroRepository.findByAutorIdJPQL(id);
        if (!livros.isEmpty()) {
            throw new DataIntegrityException(
                    "Não é possível deletar o autor pois existem " + livros.size() +
                            " livro(s) associado(s) a ele. Remova os livros primeiro."
            );
        }

        repo.delete(a);
    }
}
