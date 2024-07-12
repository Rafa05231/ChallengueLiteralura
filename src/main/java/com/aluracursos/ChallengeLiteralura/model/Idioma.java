package com.aluracursos.ChallengeLiteralura.model;

public enum Idioma {
    ESPAÑOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("po");

    private String idiomaGu;

    Idioma(String idiomaGu) {
        this.idiomaGu = idiomaGu;
    }

    public static Idioma getIdioma(String idiomaGu) {
        for (Idioma idioma : Idioma.values()) {
            if(idioma.idiomaGu.equals(idiomaGu)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: "+idiomaGu);
    }
}
