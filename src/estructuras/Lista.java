package estructuras;

import java.util.Iterator;

public interface Lista<T> extends Iterable<T> {
    void agregar(T x);
    void insertar(int i, T x);
    T obtener(int i);
    int indiceDe(T x);
    int tamanio();
    boolean estaVacia();
    T eliminar(int i);
    void vaciar();
}