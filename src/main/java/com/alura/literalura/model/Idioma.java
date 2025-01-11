package com.alura.literalura.model;

public enum Idioma {

    es("Espanhol"),
    en("Inglês"),
    fr("Francês"),
    pt("Português");

    private final String idiomaSigla;

    Idioma(String idiomaSigla) {
        this.idiomaSigla = idiomaSigla;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaSigla.equalsIgnoreCase(text) || idioma.toString().equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para: " + text);
    }
}
