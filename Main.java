import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Dependências
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = """
                === Sistema Biblioteca ===
                Escolha uma das opções abaixo:
                1 - Adicionar Livro
                2 - Listar Acervo
                3 - Pesquisar Livro
                4 - Remover Livro
                5 - Atualizar Livro
                6 - Contagem de Livros 
                7 - Pesquisar por Período
                8 - Visualizar Livro mais Antigo e mais Novo
                0 - Sair
                """;
        int opcao;
        do {
            System.out.println(menu);
            opcao = Input.scanInt("Digite sua escolha: ", scan);
            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 2:
                    listarAcervo();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 3:
                    pesquisarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 4:
                    removerLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 5:
                    atualizarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 6:
                    contagemLivros();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 7:
                    pesquisaPeriodoTempo();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 8:
                    livrosAntigoNovo();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 0:
                    System.out.println("Volte Sempre!!!");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarLivro() {
        String formato = "";
        while (true) {
            formato = Input.scanString("Você deseja cadastrar um livro físico, ou digital? ", scan);
            try {
                biblioteca.validarFormato(formato);
                break;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        String titulo = Input.scanString("Digite o Título: ", scan);
        String autor = Input.scanString("Digite o Autor: ", scan);
        int anoPublicacao = Input.scanInt("Digite o ano de publicação: ", scan);
        int numeroPaginas = Input.scanInt("Digite o número de páginas: ", scan);
        Livro novoLivro = null;
        formato = removerAcentos(formato);
        if (formato.equalsIgnoreCase("digital")) {
            novoLivro = new LivroDigital(titulo, autor, anoPublicacao, numeroPaginas);
        } else if (formato.equalsIgnoreCase("fisico")) {
            novoLivro = new LivroFisico(titulo, autor, anoPublicacao, numeroPaginas);
        }
        try {
            biblioteca.adicionar(novoLivro);
            System.out.println("Livro adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAcervo() {
        var acervo = biblioteca.pesquisar();
        imprimirLista(acervo);
    }

    private static void pesquisarLivro() {
        String titulo = Input.scanString("Digite o título que procuras: ", scan);
        String pesquisaAutor = Input.scanString(
            "Deseja pesquiar por autor? (S/N) ", scan);
        List<Livro> livros;
        if (pesquisaAutor.toLowerCase().charAt(0) == 's'){
            String autor = Input.scanString("Digite o nome do autor: ", scan);
            livros = biblioteca.pesquisar(titulo, autor);
        } else {
            livros = biblioteca.pesquisar(titulo);
        }
        imprimirLista(livros);
    }

    private static void imprimirLista(List<Livro> acervo) {
        if (acervo == null || acervo.isEmpty())
            System.out.println("Nenhum Livro Encontrado");
        else {
            System.out.println("Livros Encontrados");
            for (int i = 0; i < acervo.size(); i++) {
                System.out.println("Livro " + (i + 1) + ": " + acervo.get(i));
            }
        }
    }

    private static void imprimirLivrosNovoVelho(List<List<Livro>> listasLivros) {
        if (listasLivros.get(0).size() == 1) {
            System.out.println("=== Livro mais antigo ===");
            System.out.println("Livro: " + listasLivros.get(0).get(0));
        } else {
            int i = 1;
            System.out.println("=== Livros mais antigos ==="); 
            for (Livro  livro : listasLivros.get(0)) {
                System.out.println("Livro " + i + ": " + livro);
                i++;
            }
        }
        if (listasLivros.get(1).size() == 1) {
            System.out.println("=== Livro mais novo ===");
            System.out.println("Livro: " + listasLivros.get(1).get(0));
        } else {
            int i = 1;
            System.out.println("=== Livros mais novos ===");
            for (Livro livro : listasLivros.get(1)) {
                System.out.println("Livro " + i + ": " + livro);
                i++;
            }
        }
    }

    private static void removerLivro() {
        List<Livro> acervo = biblioteca.pesquisar();
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
        }
        imprimirLista(acervo);
        while (true) {
            int remover = Input.scanInt("Digite o número do livro que deseja Remover: ", scan);
            try {
                biblioteca.remover(remover-1);
                System.out.println("Livro removido com sucesso!");
                break;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }   
    }

    private static void atualizarLivro() {
        List<Livro> acervo = biblioteca.pesquisar();
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
        } 
        imprimirLista(acervo);
        while (true) {
            int indiceLivro = Input.scanInt("Digite o número do livro que deseja atualizar: ", scan);
            int indiceTratado = indiceLivro -1;
            try {
                biblioteca.validarIndice(indiceTratado);
                String tipoFormatado;
                while (true) {
                    String tipoMudanca = Input.scanString("Você deseja alterar o título, autor, ano ou páginas do livro? ", scan);
                    tipoFormatado = removerAcentos(tipoMudanca);
                    if (!tipoFormatado.equals("titulo") && !tipoFormatado.equals("autor") && !tipoFormatado.equals("ano") && !tipoFormatado.equals("paginas") ) {
                        System.out.println("Opção inválida, tente novamente...");
                    } else {
                        break;
                    }
                }
                switch (tipoFormatado) {
                    case "titulo":
                        while (true) {
                            String titulo = Input.scanString("Digite o novo título: ", scan);
                            try {
                                biblioteca.atualizarTitulo(indiceTratado, titulo);
                                System.out.println("Título atualizado com sucesso!");
                                break;
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                    case "autor":
                        while (true) {
                            String autor = Input.scanString("Digite o novo autor: ", scan);
                            try {
                                biblioteca.atualizarAutor(indiceTratado, autor);
                                System.out.println("Autor atualizado com sucesso!");
                                break;
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                    case "ano":
                        while (true) {
                            int ano = Input.scanInt("Digite o novo ano de publicação: ", scan);
                            try {
                                biblioteca.atualizarAnoPublicacao(indiceTratado, ano);
                                System.out.println("Ano de publicação atualizado com sucesso!");
                                break;
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                    case "paginas":
                        while (true) {
                            int paginas = Input.scanInt("Digite o novo número de páginas: ", scan);
                            try {
                                biblioteca.atualizarNumeroPaginas(indiceTratado, paginas);
                                System.out.println("Número de páginas atualizado com sucesso!");
                                break;
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            break;
        }
    }

    private static void contagemLivros() {
        List<Livro> acervo = biblioteca.pesquisar(); 
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
        }
        else if (acervo.size() == 1) {
            System.out.println("Existe 1 livro cadastrado.");
        } else {
            System.out.println("Existem " + acervo.size() + " livros cadastrados.");
        }
    }

    private static void pesquisaPeriodoTempo() {
        List<Livro> acervo = biblioteca.pesquisar();
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
        } 
        while (true) {
            int ano1Pesquisa = Input.scanInt("Digite o primeiro ano do período: ", scan);
            int ano2Pesquisa = Input.scanInt("Digite o segundo ano do período: ", scan);
            try {
                List<Livro> livrosNoPeriodo = biblioteca.pesquisarPeriodo(ano1Pesquisa, ano2Pesquisa);
                imprimirLista(livrosNoPeriodo);
                break;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

    }

    public static void livrosAntigoNovo() {
        List<Livro> acervo = biblioteca.pesquisar();
        List<List<Livro>> listaLivrosAntigoNovo = new ArrayList<>();
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
        }
        try {
            listaLivrosAntigoNovo = biblioteca.retornarLivrosAntigoNovo();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }
        
        imprimirLivrosNovoVelho(listaLivrosAntigoNovo);
        
    }

    public static String removerAcentos(String texto) {
    return Normalizer
        .normalize(texto, Normalizer.Form.NFD)
        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
        .toLowerCase();
    }
}