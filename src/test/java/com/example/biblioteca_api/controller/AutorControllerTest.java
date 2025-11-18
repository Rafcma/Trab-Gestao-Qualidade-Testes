package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Testes do AutorController")
class AutorControllerTest {

    @Autowired
    private AutorController autorController;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    private Autor autorTeste;

    @BeforeEach
    void setUp() {
        autorTeste = new Autor();
        autorTeste.setNome("Machado de Assis");
        autorTeste.setPais("Brasil");
    }

    @Test
    @DisplayName("Teste 1: Criar autor com sucesso")
    void testCriarAutorComSucesso() {
        // Act
        Autor resultado = autorController.criar(autorTeste);

        // Assert
        assertNotNull(resultado.getId(), "O ID do autor deve ser gerado");
        assertEquals("Machado de Assis", resultado.getNome(), "O nome deve ser salvo corretamente");
        assertEquals("Brasil", resultado.getPais(), "O país deve ser salvo corretamente");

        // Verificar se foi realmente salvo no banco
        assertTrue(autorRepository.existsById(resultado.getId()), "Autor deve existir no banco de dados");
    }

    @Test
    @DisplayName("Teste 2: Listar todos os autores")
    void testListarAutores() {
        // Arrange - criar alguns autores
        Autor autor1 = autorRepository.save(autorTeste);
        Autor autor2 = new Autor();
        autor2.setNome("Clarice Lispector");
        autor2.setPais("Brasil");
        autorRepository.save(autor2);

        // Act
        var autores = autorController.listar();

        // Assert
        assertNotNull(autores, "A lista não deve ser nula");
        assertTrue(autores.size() >= 2, "Deve haver pelo menos 2 autores");
    }


    @Test
    @DisplayName("Teste 3: Deletar autor sem livros associados")
    void testDeletarAutorSemLivros() {
        // Arrange
        Autor autorSalvo = autorRepository.save(autorTeste);
        Long autorId = autorSalvo.getId();

        long countAntes = autorRepository.count();
        assertEquals(1, countAntes, "Deve haver exatamente 1 autor antes da deleção");

        // Act
        autorController.deletar(autorId);

        // Assert
        assertFalse(autorRepository.existsById(autorId), "Autor deve ter sido deletado");

        long countDepois = autorRepository.count();
        assertEquals(0, countDepois, "Não deve haver autores no banco após deleção");
    }
}
