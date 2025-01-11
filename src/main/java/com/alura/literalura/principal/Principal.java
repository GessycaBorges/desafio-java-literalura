package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/";

    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();

    private final Service service;

    public Principal(Service livroService) {
        this.service = livroService;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao !=0) {
            var menu = """
                    Escolha o número de sua opção:
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        DadosLivro dados = getDadosLivro();
        Autor autor = service.buscarAutor(dados.autor().get(0));
        Livro livro = service.buscarLivro(dados);
        service.salvarLivro(livro, autor);
        System.out.println(livro);
    }

    private DadosLivro getDadosLivro() {
        System.out.println("\nDigite o nome do livro para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "%20"));

        RespostaLivros resposta = conversor.obterDados(json, RespostaLivros.class);

        if (resposta.resultados() != null && !resposta.resultados().isEmpty()) {
            return resposta.resultados().get(0);
        } else {
            throw new RuntimeException("Nenhum livro encontrado para o título fornecido!");
        }
    }

    private void listarLivrosRegistrados() {
        livros = service.listarLivros();
        this.imprimirLivros(livros);
    }

    private void listarAutoresRegistrados() {
        autores = service.listarAutores();
        this.imprimirAutores(autores);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("\nDigite um ano para busca");
        var ano = leitura.next();
        autores = service.listarAutoresVivosPorAno(ano);
        this.imprimirAutores(autores);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca dentre as opções:
                    es - Espanhol
                    en - Inglês
                    fr - Francês
                    pt - Português
                """);
        var idioma = leitura.next();
        livros = service.listarLivrosPorIdioma(idioma);
        this.imprimirLivros(livros);
    }

    private void imprimirLivros(List<Livro> livros){
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void imprimirAutores(List<Autor> autores){
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

}
