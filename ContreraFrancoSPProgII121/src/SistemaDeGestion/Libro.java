package SistemaDeGestion;

import java.util.Objects;

public class Libro implements CSVSerializable, Comparable<Libro> {

    private int id;
    private String titulo;
    private String autor;
    private Categoria categoria;

    public Libro(int id, String titulo, String autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toCSV() {
        return id + "," + titulo + "," + autor + "," + categoria;
    }

    public static Libro fromCSV(String csv) {
        String[] partes = csv.split(",");
        int id = Integer.parseInt(partes[0]);
        String titulo = partes[1];
        String autor = partes[2];
        Categoria categoria = Categoria.valueOf(partes[3]);
        return new Libro(id, titulo, autor, categoria);
    }

    @Override
    public int compareTo(Libro o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return id == libro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Libro{id=" + id + ", titulo='" + titulo + "', autor='" + autor + "', categoria=" + categoria + "}";
    }
}