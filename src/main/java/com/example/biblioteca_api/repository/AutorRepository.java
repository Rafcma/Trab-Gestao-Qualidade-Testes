package com.example.biblioteca_api.repository;

import com.example.biblioteca_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    //derived query 1
    List<Autor> findByNomeContainingIgnoreCase(String nome);


    //derived query 2
    List<Autor> findByPaisIgnoreCase(String pais);

}
