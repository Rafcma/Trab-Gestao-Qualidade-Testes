package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.exception.NotFoundException;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.repository.EditoraRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraRepository repo;

    public EditoraController(EditoraRepository repo) {
        this.repo = repo;
    }

    // cria uma nova editora no banco de dados
    @PostMapping
    public Editora criar(@Valid @RequestBody Editora e) {
        return repo.save(e);
    }

    // retorna todas as editoras cadastradas
    @GetMapping
    public List<Editora> listar() {
        return repo.findAll();
    }

    // busca uma editora específica por id
    @GetMapping("/{id}")
    public Editora buscar(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Editora não encontrada"));
    }

    // atualiza os dados de uma editora existente
    @PutMapping("/{id}")
    public Editora atualizar(@PathVariable Long id, @Valid @RequestBody Editora dados) {
        Editora e = buscar(id);
        e.setNome(dados.getNome());
        e.setCidade(dados.getCidade());
        return repo.save(e);
    }

    // remove uma editora do banco de dados
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Editora e = buscar(id);
        repo.delete(e);
    }
}
