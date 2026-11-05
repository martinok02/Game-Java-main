package estructuras;

public interface Pila<T> {
    void apilar(T x);
    T desapilar();
    T tope();
    boolean estaVacia();
    int tamanio();
    void vaciar();
}