package com.example.biblioteca_api.repository;

import com.example.biblioteca_api.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    List<Editora> findByNomeContainingIgnoreCase(String nome);
}
