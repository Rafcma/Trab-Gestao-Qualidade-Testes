package com.example.biblioteca_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    private Integer anoPublicacao;

    private String isbn;


    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    @NotNull
    private Autor autor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "editora_id")
    @NotNull
    private Editora editora;

    // get e set
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Integer getAnoPublicacao() {return anoPublicacao;}
    public void setAnoPublicacao(Integer anoPublicacao) {this.anoPublicacao = anoPublicacao;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}


    public Autor getAutor() {return autor;}
    public void setAutor(Autor autor) {this.autor = autor;}

    public Editora getEditora() {return editora;}
    public void setEditora(Editora editora) {this.editora = editora;}

}
