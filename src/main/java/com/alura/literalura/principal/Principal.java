package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.model.RespostaLivros;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.LivroService;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/";

    private final LivroService livroService;

    public Principal(LivroService livroService) {
        this.livroService = livroService;
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
        Autor autor = livroService.buscarAutor(dados.autor().get(0));
        Livro livro = livroService.buscarLivro(dados);
        livroService.salvarLivro(livro, autor);
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
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivosPorAno() {
    }

    private void listarLivrosPorIdioma() {
    }

}
