package estructuras;

public class PilaEnlazada<T> implements Pila<T> {
    private class Nodo {
        T dato;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo topeNodo;
    private int cantidad;

    public PilaEnlazada() {
        this.topeNodo = null;
        this.cantidad = 0;
    }

    @Override
    public void apilar(T x) {
        Nodo nuevo = new Nodo(x);
        nuevo.siguiente = topeNodo;
        topeNodo = nuevo;
        cantidad++;
    }

    @Override
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T dato = topeNodo.dato;
        topeNodo = topeNodo.siguiente;
        cantidad--;
        return dato;
    }

    @Override
    public T tope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return topeNodo.dato;
    }

    @Override
    public boolean estaVacia() {
        return topeNodo == null;
    }

    @Override
    public int tamanio() {
        return cantidad;
    }

    @Override
    public void vaciar() {
        topeNodo = null;
        cantidad = 0;
    }
}