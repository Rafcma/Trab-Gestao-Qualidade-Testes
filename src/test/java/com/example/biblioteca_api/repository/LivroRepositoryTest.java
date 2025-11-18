package com.example.biblioteca_api.repository;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // adicionado perfil de teste
@Transactional
@DisplayName("Testes do LivroRepository - Queries Customizadas")
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    private Autor autor1, autor2;
    private Editora editora1, editora2;

    @BeforeEach
    void setUp() {
        // Criar autores
        autor1 = new Autor();
        autor1.setNome("Autor Teste 1");
        autor1.setPais("Brasil");
        autor1 = autorRepository.save(autor1);

        autor2 = new Autor();
        autor2.setNome("Autor Teste 2");
        autor2.setPais("Portugal");
        autor2 = autorRepository.save(autor2);

        // Criar editoras
        editora1 = new Editora();
        editora1.setNome("Editora Teste 1");
        editora1.setCidade("São Paulo");
        editora1 = editoraRepository.save(editora1);

        editora2 = new Editora();
        editora2.setNome("Editora Teste 2");
        editora2.setCidade("Porto");
        editora2 = editoraRepository.save(editora2);

        // Criar livros
        Livro livro1 = new Livro();
        livro1.setTitulo("Livro 1 Editora 1");
        livro1.setAutor(autor1);
        livro1.setEditora(editora1);
        livro1.setAnoPublicacao(2020);
        livroRepository.save(livro1);

        Livro livro2 = new Livro();
        livro2.setTitulo("Livro 2 Editora 1");
        livro2.setAutor(autor2);
        livro2.setEditora(editora1);
        livro2.setAnoPublicacao(2021);
        livroRepository.save(livro2);

        Livro livro3 = new Livro();
        livro3.setTitulo("Livro 3 Editora 2");
        livro3.setAutor(autor1);
        livro3.setEditora(editora2);
        livro3.setAnoPublicacao(2022);
        livroRepository.save(livro3);
    }

    @Test
    @DisplayName("Teste 9: Query customizada - Listar livros por editora usando JPQL")
    void testListarLivrosPorEditora() {
        // Act
        List<Livro> livrosEditora1 = livroRepository.findByEditoraIdJPQL(editora1.getId());

        // Assert
        assertNotNull(livrosEditora1, "A lista não deve ser nula");
        assertEquals(2, livrosEditora1.size(), "Editora 1 deve ter 2 livros");
        assertTrue(livrosEditora1.stream()
                        .allMatch(l -> l.getEditora().getId().equals(editora1.getId())),
                "Todos os livros devem ser da Editora 1");
    }

    @Test
    @DisplayName("Teste 10: Query customizada - Contar autores por editora")
    void testContarAutoresPorEditora() {
        // Act
        List<EditoraAutorCount> resultado = livroRepository.listarQuantidadeDeAutoresPorEditora();

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertFalse(resultado.isEmpty(), "Deve haver pelo menos uma editora com autores");

        // Verificar que a Editora 1 tem 2 autores diferentes
        EditoraAutorCount editora1Stats = resultado.stream()
                .filter(e -> e.getNome().equals("Editora Teste 1"))
                .findFirst()
                .orElse(null);

        assertNotNull(editora1Stats, "Editora Teste 1 deve estar nos resultados");
        assertEquals(2L, editora1Stats.getTotal(), "Editora 1 deve ter 2 autores diferentes");
    }
}
