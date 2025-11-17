package com.example.biblioteca_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String pais;

    //get e set
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getPais() {return pais;}
    public void setPais(String pais) {this.pais = pais;}

}
