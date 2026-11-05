package estructuras;

public class ListaCircular<T> extends ListaBase<T> {

    // Nodo interno de la lista
    private class Nodo {

        private T elemento;
        private Nodo siguiente;

        public Nodo(T elemento) {
            this.elemento = elemento;
            this.siguiente = null;
        }
    }

    private Nodo primero;
    private Nodo ultimo;
    private int cantidad;

    // Constructor
    public ListaCircular() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    // Agregar al final
    @Override
    public void agregar(T elemento) {

        Nodo nuevo = new Nodo(elemento);

        if (estaVacia()) {

            primero = nuevo;
            ultimo = nuevo;

            // El único nodo apunta a sí mismo
            nuevo.siguiente = primero;

        } else {

            ultimo.siguiente = nuevo;
            ultimo = nuevo;

            // El último siempre apunta al primero
            ultimo.siguiente = primero;
        }

        cantidad++;
    }

    // Insertar en una posición determinada
    @Override
    public void insertar(int indice, T elemento) {

        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException(
                "Índice inválido: " + indice
            );
        }

        Nodo nuevo = new Nodo(elemento);

        if (indice == 0) {

            if (estaVacia()) {

                primero = nuevo;
                ultimo = nuevo;
                nuevo.siguiente = primero;

            } else {

                nuevo.siguiente = primero;
                primero = nuevo;
                ultimo.siguiente = primero;
            }

        } else if (indice == cantidad) {

            agregar(elemento);
            return;

        } else {

            Nodo actual = primero;

            for (int i = 0; i < indice - 1; i++) {
                actual = actual.siguiente;
            }

            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }

        cantidad++;
    }

    // Obtener un elemento por índice
    @Override
    public T obtener(int indice) {

        validarIndice(indice);

        Nodo actual = primero;

        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        return actual.elemento;
    }

    // Acceso que da la vuelta: en una lista de 8, el índice 8 es el 0
    public T obtenerCircular(int indice) {

        if (estaVacia()) {
            throw new IllegalStateException("La lista está vacía");
        }

        return obtener(Math.floorMod(indice, cantidad));
    }

    // Buscar un elemento
    @Override
    public int indiceDe(T elemento) {

        Nodo actual = primero;

        for (int i = 0; i < cantidad; i++) {

            if (elementosIguales(actual.elemento, elemento)) {
                return i;
            }

            actual = actual.siguiente;
        }

        return -1;
    }

    // Cantidad de elementos
    @Override
    public int tamanio() {
        return cantidad;
    }

    // Comprobar si está vacía
    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    // Eliminar un elemento
    @Override
    public T eliminar(int indice) {

        validarIndice(indice);

        Nodo eliminado;

        if (cantidad == 1) {

            eliminado = primero;

            primero = null;
            ultimo = null;

        } else if (indice == 0) {

            eliminado = primero;

            primero = primero.siguiente;
            ultimo.siguiente = primero;

        } else {

            Nodo anterior = primero;

            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.siguiente;
            }

            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;

            if (indice == cantidad - 1) {
                ultimo = anterior;
            }

            ultimo.siguiente = primero;
        }

        cantidad--;

        return eliminado.elemento;
    }

    // Vaciar completamente la lista
    @Override
    public void vaciar() {

        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    // Validar índices existentes
    private void validarIndice(int indice) {

        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                "Índice inválido: " + indice
            );
        }
    }

    // Comparar elementos, incluso si son null
    private boolean elementosIguales(T a, T b) {

        if (a == null) {
            return b == null;
        }

        return a.equals(b);
    }
}
