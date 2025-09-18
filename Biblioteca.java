import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> acervo;
    public Biblioteca() {
        this.acervo = new ArrayList<>();
    }

    private void validarTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("Título não pode ser em branco.");
        }
    }

    private void validarAutor(String autor) throws Exception {
        if (autor == null || autor.trim().isEmpty()) {
            throw new Exception("Autor não pode ser em branco.");
        }
    }

    private void validarAnoPublicacao(int ano) throws Exception {
        int anoAtual = LocalDate.now().getYear();
        if (ano < 1900 || ano > anoAtual) {
            throw new Exception("Ano de publicação deve estar entre 1900 e o ano atual.");
        }
    }

    private void validarAnoPublicacao(int... anos) throws Exception {
        int anoAtual = LocalDate.now().getYear();
        for (int ano : anos) {
            if (ano < 1900 || ano > anoAtual) {
                throw new Exception("Ano inválido: " + ano + ". O ano de publicação deve estar entre 1900 e o ano atual");
            }        
        }
    }

    private void validarNumeroPaginas(int paginas) throws Exception {
        if (paginas <= 0) {
            throw new Exception("Número de páginas deve ser maior que zero.");
        }
    }

    private void validarDuplicidade(String titulo, String autor, int ano) throws Exception {
        for (Livro livro : acervo) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getAutor().equalsIgnoreCase(autor) && livro.getAnoPublicacao() == ano) {
                throw new Exception("Esse livro já existe, tente novamente...");
            }
        }
    }


    public Livro adicionar(Livro livro) throws Exception{
        if (livro == null)
            throw new Exception("Livro não pode ser nulo.");

        validarTitulo(livro.getTitulo());
        validarAutor(livro.getAutor());
        validarAnoPublicacao(livro.getAnoPublicacao());
        validarNumeroPaginas(livro.getNumeroPaginas());
        validarDuplicidade(livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao());

        livro.setTitulo(livro.getTitulo().trim());      
        livro.setAutor(livro.getAutor().trim());
        
        acervo.add(livro);
        return livro;
    }

    public List<Livro> pesquisar() {
        return acervo;
    }

    public List<Livro> pesquisar(String titulo){
        return pesquisar(titulo, null);
    }

    public List<Livro> pesquisar(String titulo, String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())){
                if (autor == null || 
                        livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                    livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public void validarIndice(int indice) throws Exception {
        if (indice < 0 || indice >= acervo.size()) {
            throw new Exception("Índice inválido!");
        }  
    }

    public void remover(int indice) throws Exception {
        if (indice < 0 || indice >= acervo.size())
            throw new Exception("Índice inválido!");
        acervo.remove(indice);
    }

    public void atualizarTitulo(int indice, String titulo)  throws Exception {
        validarIndice(indice);
        validarTitulo(titulo);
        if (titulo.equalsIgnoreCase(acervo.get(indice).getTitulo())) {
            throw new Exception("O título digitado é exatamente igual o atual. Tente novamente...");
        }
        validarDuplicidade(titulo, acervo.get(indice).getAutor(), acervo.get(indice).getAnoPublicacao());
        acervo.get(indice).setTitulo(titulo);
    }

    public void atualizarAutor(int indice, String autor)  throws Exception {
        validarIndice(indice);
        validarAutor(autor);
        if (autor.equalsIgnoreCase(acervo.get(indice).getAutor())) {
            throw new Exception("O autor digitado é exatamente igual o atual. Tente novamente...");
        }
        validarDuplicidade(acervo.get(indice).getTitulo(), autor, acervo.get(indice).getAnoPublicacao());
        acervo.get(indice).setAutor(autor);
    }

    public void atualizarAnoPublicacao(int indice, int anoPublicacao)  throws Exception {
        validarIndice(indice);
        validarAnoPublicacao(anoPublicacao);
        if (anoPublicacao == (acervo.get(indice).getAnoPublicacao())) {
            throw new Exception("O ano digitado é exatamente igual o atual. Tente novamente...");
        }
        validarDuplicidade(acervo.get(indice).getTitulo(), acervo.get(indice).getAutor(), anoPublicacao);
        acervo.get(indice).setAnoPublicacao(anoPublicacao);
    }

    public void atualizarNumeroPaginas(int indice, int numeroPaginas)  throws Exception {
        validarIndice(indice);
        validarNumeroPaginas(numeroPaginas);
        acervo.get(indice).setNumeroPaginas(numeroPaginas);
    }

    public List<Livro> pesquisarPeriodo(int ano1, int ano2) throws Exception {
        validarAnoPublicacao(ano1, ano2);
        if (ano1 == ano2) {
            throw new Exception("Os anos não podem ser idênticos.");
        }     
        
        int anoInicial = Math.min(ano1, ano2);
        int anoFinal = Math.max(ano1, ano2);

        List<Livro> livrosPeriodo = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() >= anoInicial && livro.getAnoPublicacao() <= anoFinal) {
                livrosPeriodo.add(livro);
            }
        }
        return livrosPeriodo; 
    }

    public List<Livro> retornarLivrosAntigoNovo()  {
        int menorAno = LocalDate.now().getYear();
        int maiorAno = 1900;
        Livro livroMaisAntigo = null;
        Livro livroMaisNovo = null;
        for (Livro livro : acervo ) {
            if (livro.getAnoPublicacao() < menorAno) {
                menorAno = livro.getAnoPublicacao();
                livroMaisAntigo = livro;
            }
            if (livro.getAnoPublicacao() > maiorAno) {
                maiorAno = livro.getAnoPublicacao();
                livroMaisNovo = livro;
            }
        }
        List<Livro> livrosVelhoNovo = new ArrayList<>();
        livrosVelhoNovo.add(livroMaisAntigo);
        livrosVelhoNovo.add(livroMaisNovo);
        return livrosVelhoNovo;

    

    }

}