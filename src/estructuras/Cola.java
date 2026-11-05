package estructuras;

public interface Cola<T> {
    void encolar(T x);
    T desencolar();
    T frente();
    boolean estaVacia();
    int tamanio();
    void vaciar();
}