package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.repository.EditoraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // adicionado perfil de teste
@Transactional
@DisplayName("Testes do EditoraController")
class EditoraControllerTest {

    @Autowired
    private EditoraController editoraController;

    @Autowired
    private EditoraRepository editoraRepository;

    private Editora editoraTeste;

    @BeforeEach
    void setUp() {
        editoraTeste = new Editora();
        editoraTeste.setNome("Companhia das Letras");
        editoraTeste.setCidade("São Paulo");
    }

    @Test
    @DisplayName("Teste 4: Criar editora com sucesso")
    void testCriarEditoraComSucesso() {
        // Act
        Editora resultado = editoraController.criar(editoraTeste);

        // Assert
        assertNotNull(resultado.getId(), "O ID da editora deve ser gerado");
        assertEquals("Companhia das Letras", resultado.getNome(), "O nome deve ser salvo corretamente");
        assertEquals("São Paulo", resultado.getCidade(), "A cidade deve ser salva corretamente");

        // Verificar se foi realmente salvo no banco
        assertTrue(editoraRepository.existsById(resultado.getId()), "Editora deve existir no banco de dados");
    }

    @Test
    @DisplayName("Teste 5: Listar todas as editoras")
    void testListarEditoras() {
        // Arrange - criar algumas editoras
        editoraRepository.save(editoraTeste);

        Editora editora2 = new Editora();
        editora2.setNome("Globo Livros");
        editora2.setCidade("Rio de Janeiro");
        editoraRepository.save(editora2);

        // Act
        var editoras = editoraController.listar();

        // Assert
        assertNotNull(editoras, "A lista não deve ser nula");
        assertTrue(editoras.size() >= 2, "Deve haver pelo menos 2 editoras");
    }
}
