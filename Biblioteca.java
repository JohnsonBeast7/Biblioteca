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
            throw new Exception("Título não pode ser em branco");
        }
    }

    private void validarAutor(String autor) throws Exception {
        if (autor == null || autor.trim().isEmpty()) {
            throw new Exception("Autor não pode ser em branco");
        }
    }

    private void validarAnoPublicacao(int ano) throws Exception {
        int anoAtual = LocalDate.now().getYear();
        if (ano < 1900 || ano > anoAtual) {
            throw new Exception("Ano de publicação deve estar entre 1900 e o ano atual");
        }
    }

    private void validarNumeroPaginas(int paginas) throws Exception {
        if (paginas <= 0) {
            throw new Exception("Número de páginas deve ser maior que zero");
        }
    }


    public Livro adicionar(Livro livro) throws Exception{
        if (livro == null)
            throw new Exception("Livro não pode ser nulo");

        validarTitulo(livro.getTitulo());
        validarAutor(livro.getAutor());
        validarAnoPublicacao(livro.getAnoPublicacao());
        validarNumeroPaginas(livro.getNumeroPaginas());

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
        acervo.get(indice).setTitulo(titulo);
    }

    public void atualizarAutor(int indice, String autor)  throws Exception {
        validarIndice(indice);
        validarTitulo(autor);
        acervo.get(indice).setTitulo(autor);
    }

    public void atualizarAnoPublicacao(int indice, int anoPublicacao)  throws Exception {
        validarIndice(indice);
        validarAnoPublicacao(anoPublicacao);
        acervo.get(indice).setAnoPublicacao(anoPublicacao);
    }

    public void atualizarNumeroPaginas(int indice, int numeroPaginas)  throws Exception {
        validarIndice(indice);
        validarNumeroPaginas(numeroPaginas);
        acervo.get(indice).setNumeroPaginas(numeroPaginas);
    }

}