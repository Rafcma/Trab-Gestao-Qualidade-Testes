package com.example.biblioteca_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LivroDTO {
    @NotBlank
    private String titulo;
    private Integer anoPublicacao;
    private String isbn;

    @NotNull
    private Long autorId;

    @NotNull
    private Long editoraId;

    // getters e setters
    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Integer getAnoPublicacao() { return anoPublicacao;}
    public void setAnoPublicacao(Integer anoPublicacao) {this.anoPublicacao = anoPublicacao; }

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}


    public Long getAutorId() {return autorId;}
    public void setAutorId(Long autorId) {this.autorId = autorId;}

    public Long getEditoraId() {return editoraId;}
    public void setEditoraId(Long editoraId) {this.editoraId = editoraId; }
}
