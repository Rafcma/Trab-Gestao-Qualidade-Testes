package com.example.biblioteca_api.config;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class GeradorInicial {

    // popula o banco de dados com dados fictícios para testes
    @Bean
    CommandLineRunner initDatabase(AutorRepository autorRepository,
                                   EditoraRepository editoraRepository,
                                   LivroRepository livroRepository) {
        return args -> {
            // verifica se já existem dados para não duplicar
            if (autorRepository.count() > 0) {
                System.out.println("Banco de dados já possui dados. Pulando inicialização.");
                return;
            }

            System.out.println("Iniciando população do banco de dados com dados fictícios...");

            // criando autores
            Autor machado = new Autor();
            machado.setNome("Machado de Assis");
            machado.setPais("Brasil");
            autorRepository.save(machado);

            Autor saramago = new Autor();
            saramago.setNome("José Saramago");
            saramago.setPais("Portugal");
            autorRepository.save(saramago);

            Autor clarice = new Autor();
            clarice.setNome("Clarice Lispector");
            clarice.setPais("Brasil");
            autorRepository.save(clarice);

            Autor pessoa = new Autor();
            pessoa.setNome("Fernando Pessoa");
            pessoa.setPais("Portugal");
            autorRepository.save(pessoa);

            Autor jorge = new Autor();
            jorge.setNome("Jorge Amado");
            jorge.setPais("Brasil");
            autorRepository.save(jorge);

            // criando editoras
            Editora companhia = new Editora();
            companhia.setNome("Companhia das Letras");
            companhia.setCidade("São Paulo");
            editoraRepository.save(companhia);

            Editora porto = new Editora();
            porto.setNome("Porto Editora");
            porto.setCidade("Porto");
            editoraRepository.save(porto);

            Editora record = new Editora();
            record.setNome("Record");
            record.setCidade("Rio de Janeiro");
            editoraRepository.save(record);

            Editora globo = new Editora();
            globo.setNome("Globo Livros");
            globo.setCidade("Rio de Janeiro");
            editoraRepository.save(globo);

            // criando livros
            Livro domCasmurro = new Livro();
            domCasmurro.setTitulo("Dom Casmurro");
            domCasmurro.setAnoPublicacao(1899);
            domCasmurro.setIsbn("ISBN-001");
            domCasmurro.setAutor(machado);
            domCasmurro.setEditora(companhia);
            livroRepository.save(domCasmurro);

            Livro memoriasPostumas = new Livro();
            memoriasPostumas.setTitulo("Memórias Póstumas de Brás Cubas");
            memoriasPostumas.setAnoPublicacao(1881);
            memoriasPostumas.setIsbn("ISBN-002");
            memoriasPostumas.setAutor(machado);
            memoriasPostumas.setEditora(companhia);
            livroRepository.save(memoriasPostumas);

            Livro quincas = new Livro();
            quincas.setTitulo("Quincas Borba");
            quincas.setAnoPublicacao(1891);
            quincas.setIsbn("ISBN-003");
            quincas.setAutor(machado);
            quincas.setEditora(record);
            livroRepository.save(quincas);

            Livro memorial = new Livro();
            memorial.setTitulo("Memorial do Convento");
            memorial.setAnoPublicacao(1982);
            memorial.setIsbn("ISBN-004");
            memorial.setAutor(saramago);
            memorial.setEditora(porto);
            livroRepository.save(memorial);

            Livro ensaio = new Livro();
            ensaio.setTitulo("Ensaio sobre a Cegueira");
            ensaio.setAnoPublicacao(1995);
            ensaio.setIsbn("ISBN-005");
            ensaio.setAutor(saramago);
            ensaio.setEditora(companhia);
            livroRepository.save(ensaio);

            Livro horaEstrela = new Livro();
            horaEstrela.setTitulo("A Hora da Estrela");
            horaEstrela.setAnoPublicacao(1977);
            horaEstrela.setIsbn("ISBN-006");
            horaEstrela.setAutor(clarice);
            horaEstrela.setEditora(record);
            livroRepository.save(horaEstrela);

            Livro paixao = new Livro();
            paixao.setTitulo("A Paixão Segundo G.H.");
            paixao.setAnoPublicacao(1964);
            paixao.setIsbn("ISBN-007");
            paixao.setAutor(clarice);
            paixao.setEditora(globo);
            livroRepository.save(paixao);

            Livro desassossego = new Livro();
            desassossego.setTitulo("Livro do Desassossego");
            desassossego.setAnoPublicacao(1982);
            desassossego.setIsbn("ISBN-008");
            desassossego.setAutor(pessoa);
            desassossego.setEditora(porto);
            livroRepository.save(desassossego);

            Livro mensagem = new Livro();
            mensagem.setTitulo("Mensagem");
            mensagem.setAnoPublicacao(1934);
            mensagem.setIsbn("ISBN-009");
            mensagem.setAutor(pessoa);
            mensagem.setEditora(porto);
            livroRepository.save(mensagem);

            Livro capitaes = new Livro();
            capitaes.setTitulo("Capitães da Areia");
            capitaes.setAnoPublicacao(1937);
            capitaes.setIsbn("ISBN-010");
            capitaes.setAutor(jorge);
            capitaes.setEditora(companhia);
            livroRepository.save(capitaes);

            Livro gabriela = new Livro();
            gabriela.setTitulo("Gabriela, Cravo e Canela");
            gabriela.setAnoPublicacao(1958);
            gabriela.setIsbn("ISBN-011");
            gabriela.setAutor(jorge);
            gabriela.setEditora(record);
            livroRepository.save(gabriela);

            Livro tieta = new Livro();
            tieta.setTitulo("Tieta do Agreste");
            tieta.setAnoPublicacao(1977);
            tieta.setIsbn("ISBN-012");
            tieta.setAutor(jorge);
            tieta.setEditora(record);
            livroRepository.save(tieta);

            System.out.println("Banco de dados populado com sucesso!");
            System.out.println("- " + autorRepository.count() + " autores criados");
            System.out.println("- " + editoraRepository.count() + " editoras criadas");
            System.out.println("- " + livroRepository.count() + " livros criados");
        };
    }
}
