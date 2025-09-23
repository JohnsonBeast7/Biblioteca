import java.time.LocalDate;

public abstract class Livro {
    protected String titulo;
    protected String autor;
    protected int anoPublicacao;
    protected int numeroPaginas;

    public Livro(){
    }

    public Livro(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.numeroPaginas = numeroPaginas;
    }

    public abstract String getFormato();

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public final int calcularTempoPublicacao() {
        int anoAtual = LocalDate.now().getYear();
        return anoAtual - getAnoPublicacao();
    }

    
    public String toString() {
        return "Titulo: " + titulo 
                + ", Autor: " + autor 
                + ", Ano de Publicação: " + anoPublicacao 
                + ", Número de Páginas: " + numeroPaginas;
    }
    
    

}