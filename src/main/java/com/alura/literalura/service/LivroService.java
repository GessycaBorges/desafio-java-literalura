package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosAutor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro, Autor autor) {
        Optional<Autor> autorExistente = autorRepository.findByNome(autor.getNome());
        Autor autorCadastro;
        if (autorExistente.isPresent()) {
            autorCadastro = autorExistente.get();
        } else {
            autorCadastro = autor;
            autorRepository.save(autorCadastro);
        }
        livro.setAutor(autorCadastro);

        Livro livroCadastro;
        Optional<Livro> livroExistente = livroRepository.findById(livro.getId());
        livroCadastro = livroExistente.orElseGet(() -> livroRepository.save(livro));
        return livroCadastro;
    }

    public Livro buscarLivro (DadosLivro dadosLivro) {
        return livroRepository.findByTitulo(dadosLivro.titulo())
                .orElseGet(() -> new Livro(dadosLivro));
    }

    public Autor buscarAutor(DadosAutor dadosAutor) {
        return autorRepository.findByNome(dadosAutor.nome())
                .orElseGet(() -> new Autor(dadosAutor));
    }
}
