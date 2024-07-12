package com.aluracursos.ChallengeLiteralura.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer cantidadDescargas;
    @ManyToOne
    private Autor autor;

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.getIdioma(datosLibro.idiomas().get(0));
        this.cantidadDescargas = datosLibro.cantidadDescargas();
        this.autor = autor;
    }

    public Libro() {}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return
                "-----------LIBRO------------"+ "\n"+
                "Titulo: " + titulo + "\n"+
                "Autor: " + autor.getNombre() + "\n"+
                "Numero de descargas: " + cantidadDescargas + "\n"+
                "Idioma: " + idioma+ "\n"+
                "-----------------------------";
    }
}
