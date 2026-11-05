package juego.reglas;

import estructuras.Cola;
import estructuras.ColaEnlazada;
import juego.entidades.Enemigo;

public class Oleada {

    // Atributos
    private final Cola<Enemigo> pendientes = new ColaEnlazada<>();

    // Carga de la oleada
    public void agregar(Enemigo enemigo) {
        if (enemigo == null) {
            throw new IllegalArgumentException("el enemigo no puede ser nulo");
        }
        pendientes.encolar(enemigo);
    }

    // Consultas
    public boolean terminada() {
        return pendientes.estaVacia();
    }

    public int restantes() {
        return pendientes.tamanio();
    }

    // Siguiente enemigo a aparecer, o null si no queda ninguno
    public Enemigo siguiente() {
        if (terminada()) {
            return null;
        }
        return pendientes.desencolar();
    }
}
