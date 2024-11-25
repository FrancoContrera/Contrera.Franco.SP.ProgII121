package SistemaDeGestion;

import java.io.IOException;
import java.util.Comparator;

public class BibliotecaInventario {
    public static void main(String[] args) {
        try {
            // Crear un inventario de libros
            Inventario<Libro> inventarioLibros = new Inventario<>();

            // Agregar libros al inventario
            try {
                inventarioLibros.agregar(new Libro(1, "1984", "George Orwell", Categoria.ENTRETENIMIENTO));
                inventarioLibros.agregar(new Libro(2, "El senor de los anillos", "J.R.R. Tolkien", Categoria.LITERATURA));
                inventarioLibros.agregar(new Libro(3, "Cien anios de soledad", "Gabriel Garcia Marquez", Categoria.LITERATURA));
                inventarioLibros.agregar(new Libro(4, "El origen de las especies", "Charles Darwin", Categoria.CIENCIA));
                inventarioLibros.agregar(new Libro(5, "La guerra de los mundos", "H.G. Wells", Categoria.ENTRETENIMIENTO));
            } catch (IllegalArgumentException e) {
                System.err.println("Error al agregar libro: " + e.getMessage());
            }

            // Mostrar todos los libros en el inventario
            System.out.println("Inventario de libros:");
            inventarioLibros.paraCadaElemento(System.out::println);

            // Filtrar libros por categoria LITERATURA
            System.out.println("\nLibros de la categoria LITERATURA:");
            inventarioLibros.filtrar(libro -> libro.getCategoria() == Categoria.LITERATURA)
                .forEach(System.out::println);

            // Filtrar libros cuyo titulo contiene "1984"
            System.out.println("\nLibros cuyo titulo contiene '1984':");
            inventarioLibros.filtrar(libro -> libro.getTitulo().contains("1984"))
                .forEach(System.out::println);

            // Ordenar libros de manera natural (por id)
            System.out.println("\nLibros ordenados de manera natural (por id):");
            inventarioLibros.ordenar();
            inventarioLibros.paraCadaElemento(System.out::println);

            // Ordenar libros por titulo utilizando un Comparator
            System.out.println("\nLibros ordenados por titulo:");
            inventarioLibros.ordenar(Comparator.comparing(Libro::getTitulo));
            inventarioLibros.paraCadaElemento(System.out::println);

            // Guardar el inventario en un archivo CSV
            try {
                inventarioLibros.guardarEnCSV("data/libros.csv");
            } catch (IOException e) {
                System.err.println("Error al guardar en CSV: " + e.getMessage());
            }

            // Cargar el inventario desde el archivo CSV
            Inventario<Libro> inventarioCargado = new Inventario<>();
            try {
                inventarioCargado.cargarDesdeCSV("data/libros.csv", line -> Libro.fromCSV(line));
                System.out.println("\nLibros cargados desde archivo CSV:");
                inventarioCargado.paraCadaElemento(System.out::println);
            } catch (IOException e) {
                System.err.println("Error al cargar desde CSV: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}