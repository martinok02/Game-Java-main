package estructuras;
import java.util.Iterator;

public class ListaEnlazada<T> implements Lista<T>, Iterable<T> {
    private class Nodo {
        T dato;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    @Override
    public void agregar(T x) {
        Nodo nuevo = new Nodo(x);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        cantidad++;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
        }
        Nodo nuevo = new Nodo(x);
        if (i == 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            for (int j = 0; j < i - 1; j++) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        cantidad++;
    }

    @Override
    public T obtener(int i) {
        if (i < 0 || i >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
        }
        Nodo actual = cabeza;
        for (int j = 0; j < i; j++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    @Override
    public int indiceDe(T x) {
        Nodo actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (x == null ? actual.dato == null : x.equals(actual.dato)) {
                return indice;
            }
            actual = actual.siguiente;
            indice++;
        }
        return -1;
    }

    @Override
    public int tamanio() {
        return cantidad;
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    @Override
    public T eliminar(int i) {
        if (i < 0 || i >= cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + i);
        }
        T eliminado;
        if (i == 0) {
            eliminado = cabeza.dato;
            cabeza = cabeza.siguiente;
        } else {
            Nodo actual = cabeza;
            for (int j = 0; j < i - 1; j++) {
                actual = actual.siguiente;
            }
            eliminado = actual.siguiente.dato;
            actual.siguiente = actual.siguiente.siguiente;
        }
        cantidad--;
        return eliminado;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cantidad = 0;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }
}