package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.dto.LivroDTO;
import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Testes do LivroController")
class LivroControllerTest {

    @Autowired
    private LivroController livroController;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Test
    @DisplayName("Teste 6: Criar livro com autor e editora válidos")
    void testCriarLivroComAutorEEditoraValidos() {
        // Arrange - criar autor e editora primeiro
        Autor autor = new Autor();
        autor.setNome("Jorge Amado");
        autor.setPais("Brasil");
        autor = autorRepository.save(autor);

        Editora editora = new Editora();
        editora.setNome("Record");
        editora.setCidade("Rio de Janeiro");
        editora = editoraRepository.save(editora);

        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Capitães da Areia");
        livroDTO.setAnoPublicacao(1937);
        livroDTO.setIsbn("978-8501058638");
        livroDTO.setAutorId(autor.getId());
        livroDTO.setEditoraId(editora.getId());

        // Act
        Livro resultado = livroController.criar(livroDTO);

        // Assert
        assertNotNull(resultado.getId(), "O ID do livro deve ser gerado");
        assertEquals("Capitães da Areia", resultado.getTitulo(), "O título deve ser salvo corretamente");
        assertEquals(1937, resultado.getAnoPublicacao(), "O ano de publicação deve ser salvo corretamente");
        assertNotNull(resultado.getAutor(), "O autor deve estar associado");
        assertNotNull(resultado.getEditora(), "A editora deve estar associada");
        assertEquals(autor.getId(), resultado.getAutor().getId(), "O autor deve ser o correto");
        assertEquals(editora.getId(), resultado.getEditora().getId(), "A editora deve ser a correta");

        // Verificar se foi realmente salvo no banco
        assertTrue(livroRepository.existsById(resultado.getId()), "Livro deve existir no banco de dados");
    }

    @Test
    @DisplayName("Teste 7: Buscar livro por título")
    void testBuscarLivroPorTitulo() {
        // Arrange - criar autor e editora primeiro
        Autor autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setPais("Brasil");
        autor = autorRepository.save(autor);

        Editora editora = new Editora();
        editora.setNome("Garnier");
        editora.setCidade("Rio de Janeiro");
        editora = editoraRepository.save(editora);

        Livro livro = new Livro();
        livro.setTitulo("Dom Casmurro");
        livro.setAnoPublicacao(1899);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livroRepository.save(livro);

        // Act
        List<Livro> resultado = livroRepository.findByTituloContainingIgnoreCase("Dom");

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertFalse(resultado.isEmpty(), "Deve encontrar pelo menos um livro");
        assertTrue(resultado.get(0).getTitulo().contains("Dom"), "O título deve conter 'Dom'");
    }

    @Test
    @DisplayName("Teste 8: Atualizar informações do livro")
    void testAtualizarLivro() {
        // Arrange - criar autor e editora primeiro
        Autor autor = new Autor();
        autor.setNome("Clarice Lispector");
        autor.setPais("Brasil");
        autor = autorRepository.save(autor);

        Editora editora = new Editora();
        editora.setNome("Rocco");
        editora.setCidade("São Paulo");
        editora = editoraRepository.save(editora);

        LivroDTO livroDTOInicial = new LivroDTO();
        livroDTOInicial.setTitulo("A Hora da Estrela");
        livroDTOInicial.setAnoPublicacao(1977);
        livroDTOInicial.setIsbn("978-8532511010");
        livroDTOInicial.setAutorId(autor.getId());
        livroDTOInicial.setEditoraId(editora.getId());

        Livro livroSalvo = livroController.criar(livroDTOInicial);
        Long livroId = livroSalvo.getId();

        LivroDTO livroDTOAtualizado = new LivroDTO();
        livroDTOAtualizado.setTitulo("A Hora da Estrela - Edição Revisada");
        livroDTOAtualizado.setAnoPublicacao(1978);
        livroDTOAtualizado.setIsbn("978-8532511010");
        livroDTOAtualizado.setAutorId(autor.getId());
        livroDTOAtualizado.setEditoraId(editora.getId());

        // Act
        Livro livroAtualizado = livroController.atualizar(livroId, livroDTOAtualizado);

        // Assert
        assertEquals(1978, livroAtualizado.getAnoPublicacao(), "O ano de publicação deve ter sido atualizado");
        assertTrue(livroAtualizado.getTitulo().contains("Edição Revisada"), "O título deve ter sido atualizado");
        assertEquals(livroId, livroAtualizado.getId(), "O ID deve permanecer o mesmo");
    }
}
