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
                5 - Contagem de Livros 
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
                    contagemLivros();
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
        String titulo = Input.scanString("Digite o Título: ", scan);
        String autor = Input.scanString("Digite o Autor: ", scan);
        int anoPublicacao = Input.scanInt("Digite o ano de publicação: ", scan);
        int numeroPaginas = Input.scanInt("Digite o número de páginas: ", scan);
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);
        try {
            biblioteca.adicionar(novoLivro);
            System.out.println("Livro adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAcervo() {
        var acervo = biblioteca.pesquisar();
        // List<Livro> acervo = biblioteca.pesquisar();
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
            System.out.println("Livros Encrontrados");
            for (int i = 0; i < acervo.size(); i++) {
                System.out.println("Livro " + (i + 1) + ": " + acervo.get(i));
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
        int remover;
        remover = Input.scanInt("Digite o número do livro que deseja Remover: ", scan);
        try {
            biblioteca.remover(remover-1);
            System.out.println("Livro removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void atualizarLivro() {
        List<Livro> acervo = biblioteca.pesquisar();
        if (acervo.isEmpty()) {
            System.out.println("Não existem livros no acervo.");
            return;
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

}