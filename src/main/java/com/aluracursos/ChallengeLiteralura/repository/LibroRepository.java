package com.aluracursos.ChallengeLiteralura.repository;

import com.aluracursos.ChallengeLiteralura.model.Idioma;
import com.aluracursos.ChallengeLiteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findLibroByTitulo(String nombre);
    List<Libro> findLibroByIdioma(Idioma idioma);
}
