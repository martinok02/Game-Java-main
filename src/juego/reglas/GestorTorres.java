package juego.reglas;

import entidades.Torre;
import entidades.TorreBasica;
import estructuras.Lista;
import estructuras.ListaEnlazada;
import estructuras.Pila;
import estructuras.PilaEnlazada;
import java.awt.event.KeyEvent;
import juego.contratos.Danable;
import motor.Entrada;
import motor.Mundo;

public class GestorTorres {

    // Constantes
    private static final double PORCENTAJE_VENTA = 0.5;

    // Atributos
    private final Mundo mundo;
    private final Lista<? extends Danable> enemigos;
    private final Pila<Torre> construidas = new PilaEnlazada<>();
    private final Lista<Torre> torres = new ListaEnlazada<>();
    private final Partida partida;

    // Constructor
    public GestorTorres(Mundo mundo, Lista<? extends Danable> enemigos, Partida partida) {
        if (mundo == null) {
            throw new IllegalArgumentException("el mundo no puede ser nulo");
        }
        if (enemigos == null) {
            throw new IllegalArgumentException("la lista de enemigos no puede ser nula");
        }
        if (partida == null) {
            throw new IllegalArgumentException("la partida no puede ser nula");
        }
        this.mundo = mundo;
        this.enemigos = enemigos;
        this.partida = partida;
    }

    // Consultas
    public int cantidadTorres() {
        return torres.tamanio();
    }

    // Lectura de mouse y teclado
    public void actualizar(Entrada entrada) {
        if (entrada.huboClick()) {
            construir(entrada.getMouseX(), entrada.getMouseY());
        }
        if (entrada.fuePresionada(KeyEvent.VK_Z)) {
            deshacerUltima();
        }
        if (entrada.fuePresionada(KeyEvent.VK_V)) {
            venderUltima();
        }
    }

    // Construcción
    public boolean construir(double x, double y) {
        Torre nueva = new TorreBasica(x, y, enemigos);
        if (!partida.puedeComprar(nueva.getCosto()) || !estaLibre(nueva)) {
            return false;
        }
        partida.comprar(nueva.getCosto());
        construidas.apilar(nueva);
        torres.agregar(nueva);
        mundo.agregar(nueva);
        return true;
    }

    // Deshacer: devuelve todo, solo si la torre todavía no disparó
    public boolean deshacerUltima() {
        if (construidas.estaVacia() || construidas.tope().yaDisparo()) {
            return false;
        }
        partida.cobrar(quitarUltima().getCosto());
        return true;
    }

    // Venta: devuelve una parte, aunque la torre ya haya disparado
    public boolean venderUltima() {
        if (construidas.estaVacia()) {
            return false;
        }
        partida.cobrar((int) (quitarUltima().getCosto() * PORCENTAJE_VENTA));
        return true;
    }

    // Baja de la última torre construida
    private Torre quitarUltima() {
        Torre ultima = construidas.desapilar();
        torres.eliminar(torres.indiceDe(ultima));
        ultima.destruir();
        return ultima;
    }

    // Validación del lugar elegido
    private boolean estaLibre(Torre nueva) {
        double r = nueva.getRadio();
        double x = nueva.getX();
        double y = nueva.getY();
        if (x < r || y < r || x > mundo.getAncho() - r || y > mundo.getAlto() - r) {
            return false;
        }
        for (Torre torre : torres) {
            double separacion = Math.hypot(torre.getX() - x, torre.getY() - y);
            if (separacion < torre.getRadio() + r) {
                return false;
            }
        }
        return true;
    }
}
