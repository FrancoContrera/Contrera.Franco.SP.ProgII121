package SistemaDeGestion;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Inventario<T extends CSVSerializable & Comparable<T>> {

    private List<T> elementos;

    public Inventario() {
        elementos = new ArrayList<>();
    }

    public void agregar(T elemento) {
        if (elementos.contains(elemento)) {
            throw new IllegalArgumentException("El elemento ya existe en el inventario.");
        }
        elementos.add(elemento);
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= elementos.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }
        return elementos.get(indice);
    }

    public void eliminar(int indice) {
        if (indice < 0 || indice >= elementos.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }
        elementos.remove(indice);
    }

    public List<T> filtrar(Predicate<T> predicado) {
        List<T> resultados = new ArrayList<>();
        for (T elemento : elementos) {
            if (predicado.test(elemento)) {
                resultados.add(elemento);
            }
        }
        return resultados;
    }

    public void ordenar() {
        Collections.sort(elementos);
    }

    public void ordenar(Comparator<T> comparador) {
        if (comparador == null) {
            throw new IllegalArgumentException("El comparador no puede ser nulo.");
        }
        elementos.sort(comparador);
    }

    public void paraCadaElemento(Consumer<T> accion) {
        for (T elemento : elementos) {
            accion.accept(elemento);
        }
    }

    public void guardarEnArchivo(String ruta) throws IOException {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))) {
            salida.writeObject(elementos);
        }
    }

    public void cargarDesdeArchivo(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
            elementos = (List<T>) entrada.readObject();
        }
    }

    public void guardarEnCSV(String ruta) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (T elemento : elementos) {
                writer.write(elemento.toCSV());
                writer.newLine();
            }
        }
    }

    public void cargarDesdeCSV(String ruta, Function<String, T> creador) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                T elemento = creador.apply(linea);
                agregar(elemento);
            }
        }
    }
}