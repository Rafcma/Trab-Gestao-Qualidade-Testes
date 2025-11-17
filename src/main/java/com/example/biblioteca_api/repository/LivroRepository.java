package com.example.biblioteca_api.repository;

import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.EditoraLivroCount;
import com.example.biblioteca_api.repository.EditoraAutorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // derived query 3
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // JPQL 1 livros por autor específico (usando ID do autor)
    @Query("SELECT l FROM Livro l WHERE l.autor.id = :autorId")
    List<Livro> findByAutorIdJPQL(@Param("autorId") Long autorId);

    // JPQL 2 estatística de livros por editora (projeção)
    @Query("SELECT e.nome AS nome, COUNT(l) AS total " +
            "FROM Livro l JOIN l.editora e " +
            "GROUP BY e.nome " +
            "ORDER BY COUNT(l) DESC")
    List<EditoraLivroCount> listarQuantidadeDeLivrosPorEditora();

    @Query("SELECT l FROM Livro l WHERE l.editora.id = :editoraId")
    List<Livro> findByEditoraIdJPQL(@Param("editoraId") Long editoraId);

    // native query 1 contagem por editora (usa nomes físicos das colunas)
    @Query(value = "SELECT COUNT(*) FROM livro WHERE editora_id = :editoraId", nativeQuery = true)
    long countByEditoraIdNative(@Param("editoraId") Long editoraId);

    @Query("SELECT e.nome AS nome, COUNT(DISTINCT l.autor.id) AS total " +
            "FROM Livro l JOIN l.editora e " +
            "GROUP BY e.id, e.nome " +
            "ORDER BY COUNT(DISTINCT l.autor.id) DESC")
    List<EditoraAutorCount> listarQuantidadeDeAutoresPorEditora();
}
