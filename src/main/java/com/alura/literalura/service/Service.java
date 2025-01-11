package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro, Autor autor) {
        Optional<Autor> autorExistente = autorRepository.findByNome(autor.getNome());
        Autor autorCadastro = autorExistente.orElseGet(() -> autorRepository.save(autor));

        livro.setAutor(autorCadastro);

        if (livro.getId() == null) {
            return livroRepository.save(livro);
        }

        Optional<Livro> livroExistente = livroRepository.findById(livro.getId());
        return livroExistente.orElseGet(() -> livroRepository.save(livro));
    }

    public Livro buscarLivro (DadosLivro dadosLivro) {
        return livroRepository.findByTitulo(dadosLivro.titulo())
                .orElseGet(() -> new Livro(dadosLivro));
    }

    public Autor buscarAutor(DadosAutor dadosAutor) {
        return autorRepository.findByNome(dadosAutor.nome())
                .orElseGet(() -> new Autor(dadosAutor));
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosPorAno(String ano) {
        return autorRepository.findByVivosPorAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(Idioma.valueOf(idioma));
    }
}
