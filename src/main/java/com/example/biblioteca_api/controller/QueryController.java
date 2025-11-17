package com.example.biblioteca_api.controller;

import com.example.biblioteca_api.model.Autor;
import com.example.biblioteca_api.model.Editora;
import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.repository.AutorRepository;
import com.example.biblioteca_api.repository.EditoraAutorCount;
import com.example.biblioteca_api.repository.EditoraRepository;
import com.example.biblioteca_api.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/queries")
public class QueryController {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;

    public QueryController(LivroRepository livroRepository,
                           AutorRepository autorRepository,
                           EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
    }

    // carrega a página inicial de queries
    @GetMapping
    public String index(Model model) {
        prepararModel(model);
        return "queries";
    }

    // derived query 1: busca autores por nome (api rest)
    @GetMapping("/api/derived/autores-por-nome")
    @ResponseBody
    public ResponseEntity<List<Autor>> buscarAutoresPorNomeAPI(@RequestParam String nome) {
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        return ResponseEntity.ok(autores);
    }

    // derived query 2: busca autores por país
    @GetMapping("/api/derived/autores-por-pais")
    @ResponseBody
    public ResponseEntity<List<Autor>> buscarAutoresPorPaisAPI(@RequestParam String pais) {
        List<Autor> autores = autorRepository.findByPaisIgnoreCase(pais);
        return ResponseEntity.ok(autores);
    }

    // jpql 1: busca livros por autor
    @GetMapping("/api/jpql/livros-por-autor")
    @ResponseBody
    public ResponseEntity<List<Livro>> buscarLivrosPorAutorAPI(@RequestParam Long autorId) {
        List<Livro> livros = livroRepository.findByAutorIdJPQL(autorId);
        return ResponseEntity.ok(livros);
    }

    // jpql 2: estatísticas de autores por editora
    @GetMapping("/api/jpql/estatisticas-editora")
    @ResponseBody
    public ResponseEntity<List<EditoraAutorCount>> estatisticasEditoraAPI() {
        List<EditoraAutorCount> estatisticas = livroRepository.listarQuantidadeDeAutoresPorEditora();
        return ResponseEntity.ok(estatisticas);
    }

    // native query: conta livros por editora
    @GetMapping("/api/native/contar-livros-editora")
    @ResponseBody
    public ResponseEntity<Long> contarLivrosPorEditoraAPI(@RequestParam Long editoraId) {
        long total = livroRepository.countByEditoraIdNative(editoraId);
        return ResponseEntity.ok(total);
    }

    // jpql 3: busca livros por editora
    @GetMapping("/api/jpql/livros-por-editora")
    @ResponseBody
    public ResponseEntity<List<Livro>> buscarLivrosPorEditoraAPI(@RequestParam Long editoraId) {
        List<Livro> livros = livroRepository.findByEditoraIdJPQL(editoraId);
        return ResponseEntity.ok(livros);
    }

    @PostMapping("/executar/autores-por-nome")
    public String executarQuery1(@RequestParam String nomeAutor, Model model) {
        prepararModel(model);

        if (nomeAutor != null && !nomeAutor.trim().isEmpty()) {
            List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nomeAutor);
            model.addAttribute("resultados", autores);
            model.addAttribute("tipoResultado", "autores");
            model.addAttribute("queryTitulo", "Autores por Nome");
            model.addAttribute("queryTipo", "Derived Query");
            model.addAttribute("parametroBusca", nomeAutor);
            model.addAttribute("totalResultados", autores.size());
            model.addAttribute("activeQuery", "query1");
        }

        return "queries";
    }

    @PostMapping("/executar/autores-por-pais")
    public String executarQuery2(@RequestParam String paisAutor, Model model) {
        prepararModel(model);

        if (paisAutor != null && !paisAutor.trim().isEmpty()) {
            List<Autor> autores = autorRepository.findByPaisIgnoreCase(paisAutor);
            model.addAttribute("resultados", autores);
            model.addAttribute("tipoResultado", "autores");
            model.addAttribute("queryTitulo", "Autores por País");
            model.addAttribute("queryTipo", "Derived Query");
            model.addAttribute("parametroBusca", paisAutor);
            model.addAttribute("totalResultados", autores.size());
            model.addAttribute("activeQuery", "query2");
        }

        return "queries";
    }

    @PostMapping("/executar/livros-por-autor")
    public String executarQuery3(@RequestParam Long autorId, Model model) {
        prepararModel(model);

        if (autorId != null) {
            List<Livro> livros = livroRepository.findByAutorIdJPQL(autorId);
            Autor autor = autorRepository.findById(autorId).orElse(null);

            model.addAttribute("resultados", livros);
            model.addAttribute("tipoResultado", "livros");
            model.addAttribute("queryTitulo", "Livros por Autor");
            model.addAttribute("queryTipo", "JPQL Query");
            model.addAttribute("parametroBusca", autor != null ? autor.getNome() : "ID: " + autorId);
            model.addAttribute("totalResultados", livros.size());
            model.addAttribute("activeQuery", "query3");
        }

        return "queries";
    }

    @PostMapping("/executar/estatisticas-autores-editora")
    public String executarQuery4(Model model) {
        prepararModel(model);

        List<EditoraAutorCount> estatisticas = livroRepository.listarQuantidadeDeAutoresPorEditora();
        model.addAttribute("resultados", estatisticas);
        model.addAttribute("tipoResultado", "estatisticas-autores");
        model.addAttribute("queryTitulo", "Estatísticas de Autores por Editora");
        model.addAttribute("queryTipo", "JPQL com Agregação");
        model.addAttribute("totalResultados", estatisticas.size());
        model.addAttribute("activeQuery", "query4");

        return "queries";
    }

    @PostMapping("/executar/contar-livros-editora")
    public String executarQuery5(@RequestParam Long editoraIdCount, Model model) {
        prepararModel(model);

        if (editoraIdCount != null) {
            long total = livroRepository.countByEditoraIdNative(editoraIdCount);
            Editora editora = editoraRepository.findById(editoraIdCount).orElse(null);

            model.addAttribute("resultados", total);
            model.addAttribute("tipoResultado", "contagem");
            model.addAttribute("queryTitulo", "Contar Livros por Editora");
            model.addAttribute("queryTipo", "Native Query");
            model.addAttribute("parametroBusca", editora != null ? editora.getNome() : "ID: " + editoraIdCount);
            model.addAttribute("totalResultados", total);
            model.addAttribute("activeQuery", "query5");
        }

        return "queries";
    }

    @PostMapping("/executar/livros-por-editora")
    public String executarQuery6(@RequestParam Long editoraIdList, Model model) {
        prepararModel(model);

        if (editoraIdList != null) {
            List<Livro> livros = livroRepository.findByEditoraIdJPQL(editoraIdList);
            Editora editora = editoraRepository.findById(editoraIdList).orElse(null);

            model.addAttribute("resultados", livros);
            model.addAttribute("tipoResultado", "livros");
            model.addAttribute("queryTitulo", "Livros por Editora");
            model.addAttribute("queryTipo", "JPQL Query");
            model.addAttribute("parametroBusca", editora != null ? editora.getNome() : "ID: " + editoraIdList);
            model.addAttribute("totalResultados", livros.size());
            model.addAttribute("activeQuery", "query6");
        }

        return "queries";
    }

    @GetMapping("/executar/{queryType}")
    public String executarQuery(@PathVariable String queryType,
                                @RequestParam(required = false) String nome,
                                @RequestParam(required = false) String pais,
                                @RequestParam(required = false) Long autorId,
                                @RequestParam(required = false) Long editoraId,
                                Model model) {

        model.addAttribute("queryType", queryType);

        switch (queryType) {
            case "autores-por-nome":
                if (nome != null && !nome.isEmpty()) {
                    List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
                    model.addAttribute("resultado", autores);
                    model.addAttribute("parametro", nome);
                    model.addAttribute("queryTitulo", "Autores por Nome");
                    model.addAttribute("queryDescricao", "Busca autores cujo nome contenha o texto informado (case-insensitive)");
                    model.addAttribute("queryTipo", "Derived Query");
                    model.addAttribute("queryCodigo", "List<Autor> findByNomeContainingIgnoreCase(String nome);");
                }
                break;

            case "autores-por-pais":
                if (pais != null && !pais.isEmpty()) {
                    List<Autor> autores = autorRepository.findByPaisIgnoreCase(pais);
                    model.addAttribute("resultado", autores);
                    model.addAttribute("parametro", pais);
                    model.addAttribute("queryTitulo", "Autores por País");
                    model.addAttribute("queryDescricao", "Busca autores de um país específico (case-insensitive)");
                    model.addAttribute("queryTipo", "Derived Query");
                    model.addAttribute("queryCodigo", "List<Autor> findByPaisIgnoreCase(String pais);");
                }
                break;

            case "livros-por-autor":
                if (autorId != null) {
                    List<Livro> livros = livroRepository.findByAutorIdJPQL(autorId);
                    Autor autor = autorRepository.findById(autorId).orElse(null);
                    model.addAttribute("resultado", livros);
                    model.addAttribute("parametro", autor != null ? autor.getNome() : autorId);
                    model.addAttribute("queryTitulo", "Livros por Autor");
                    model.addAttribute("queryDescricao", "Busca todos os livros de um autor específico usando JPQL");
                    model.addAttribute("queryTipo", "JPQL Query");
                    model.addAttribute("queryCodigo", "@Query(\"SELECT l FROM Livro l WHERE l.autor.id = :autorId\")\nList<Livro> findByAutorIdJPQL(@Param(\"autorId\") Long autorId);");
                }
                break;

            case "estatisticas-editora":
                List<EditoraAutorCount> estatisticas = livroRepository.listarQuantidadeDeAutoresPorEditora();
                model.addAttribute("resultado", estatisticas);
                model.addAttribute("queryTitulo", "Estatísticas de Autores por Editora");
                model.addAttribute("queryDescricao", "Lista todas as editoras com a quantidade de autores distintos que publicaram");
                model.addAttribute("queryTipo", "JPQL Query");
                model.addAttribute("queryCodigo", "@Query(\"SELECT e.nome AS nome, COUNT(DISTINCT l.autor.id) AS total FROM Livro l JOIN l.editora e GROUP BY e.id, e.nome ORDER BY COUNT(DISTINCT l.autor.id) DESC\")\nList<EditoraAutorCount> listarQuantidadeDeAutoresPorEditora();");
                break;

            case "contar-livros-editora":
                if (editoraId != null) {
                    long total = livroRepository.countByEditoraIdNative(editoraId);
                    model.addAttribute("resultado", total);
                    model.addAttribute("parametro", editoraId);
                    model.addAttribute("queryTitulo", "Contar Livros por Editora");
                    model.addAttribute("queryDescricao", "Conta o total de livros de uma editora específica usando Native Query");
                    model.addAttribute("queryTipo", "Native Query");
                    model.addAttribute("queryCodigo", "@Query(value = \"SELECT COUNT(*) FROM livro WHERE editora_id = :editoraId\", nativeQuery = true)\nlong countByEditoraIdNative(@Param(\"editoraId\") Long editoraId);");
                }
                break;

            case "livros-por-editora":
                if (editoraId != null) {
                    List<Livro> livros = livroRepository.findByEditoraIdJPQL(editoraId);
                    model.addAttribute("resultado", livros);
                    model.addAttribute("parametro", editoraId);
                    model.addAttribute("queryTitulo", "Livros por Editora");
                    model.addAttribute("queryDescricao", "Busca todos os livros de uma editora específica usando JPQL");
                    model.addAttribute("queryTipo", "JPQL Query");
                    model.addAttribute("queryCodigo", "@Query(\"SELECT l FROM Livro l WHERE l.editora.id = :editoraId\")\nList<Livro> findByEditoraIdJPQL(@Param(\"editoraId\") Long editoraId);");
                }
                break;
        }

        return "query-executor";
    }

    // prepara o model com dados necessários para os dropdowns da página
    private void prepararModel(Model model) {
        model.addAttribute("todosAutores", autorRepository.findAll());
        model.addAttribute("todasEditoras", editoraRepository.findAll());

        // lista de países únicos dos autores para o dropdown
        List<String> paises = autorRepository.findAll().stream()
                .map(Autor::getPais)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("todosPaises", paises);
    }
}
