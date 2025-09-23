public final class LivroFisico extends Livro {

    public LivroFisico(String titulo, String autor, int anoPublicacao, int numeroPaginas) {
        super(titulo, autor, anoPublicacao, numeroPaginas);
    }

    public String getFormato() {
        return "Livro Físico";
    }

    @Override
    public String toString() {
        return super.toString() + ", Formato: " + getFormato();
    }
}
