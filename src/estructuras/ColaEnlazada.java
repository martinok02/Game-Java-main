package estructuras;

public class ColaEnlazada<T> implements Cola<T> {
    private class Nodo {
        T dato;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo frenteNodo;
    private Nodo finNodo;
    private int cantidad;

    public ColaEnlazada() {
        this.frenteNodo = null;
        this.finNodo = null;
        this.cantidad = 0;
    }

    @Override
    public void encolar(T x) {
        Nodo nuevo = new Nodo(x);
        if (estaVacia()) {
            frenteNodo = nuevo;
            finNodo = nuevo;
        } else {
            finNodo.siguiente = nuevo;
            finNodo = nuevo;
        }
        cantidad++;
    }

    @Override
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T dato = frenteNodo.dato;
        frenteNodo = frenteNodo.siguiente;
        if (frenteNodo == null) {
            finNodo = null;
        }
        cantidad--;
        return dato;
    }

    @Override
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frenteNodo.dato;
    }

    @Override
    public boolean estaVacia() {
        return frenteNodo == null;
    }

    @Override
    public int tamanio() {
        return cantidad;
    }

    @Override
    public void vaciar() {
        frenteNodo = null;
        finNodo = null;
        cantidad = 0;
    }
}