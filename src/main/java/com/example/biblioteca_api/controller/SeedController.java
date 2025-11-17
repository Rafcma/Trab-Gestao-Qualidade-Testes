package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping
    public String mostrarPaginaSeed(Model model) {
        model.addAttribute("totalAutores", autorRepository.count());
        model.addAttribute("totalEditoras", editoraRepository.count());
        model.addAttribute("totalLivros", livroRepository.count());
        return "seed";
    }

    @PostMapping("/clear")
    public String limparBancoDeDados(RedirectAttributes redirectAttributes) {
        try {
            // Deletar na ordem correta para respeitar foreign keys
            long livrosRemovidos = livroRepository.count();
            livroRepository.deleteAll();

            long autoresRemovidos = autorRepository.count();
            autorRepository.deleteAll();

            long editorasRemovidas = editoraRepository.count();
            editoraRepository.deleteAll();

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Banco de dados limpo com sucesso! " +
                            "Removidos: " + livrosRemovidos + " livros, " +
                            autoresRemovidos + " autores, " +
                            editorasRemovidas + " editoras.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Erro ao limpar banco de dados: " + e.getMessage());
        }

        return "redirect:/seed";
    }

    @PostMapping("/populate")
    public String popularBancoDeDados(RedirectAttributes redirectAttributes) {
        try {
            // Verificar se já existem dados
            if (autorRepository.count() > 0) {
                redirectAttributes.addFlashAttribute("mensagemAviso",
                        "Banco de dados já possui dados. Limpe antes de popular novamente.");
                return "redirect:/seed";
            }

            // Criar autores
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

            // Criar editoras
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

            // Criar livros
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

            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Banco de dados populado com sucesso! " +
                            "Criados: " + livroRepository.count() + " livros, " +
                            autorRepository.count() + " autores, " +
                            editoraRepository.count() + " editoras.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Erro ao popular banco de dados: " + e.getMessage());
        }

        return "redirect:/seed";
    }
}
