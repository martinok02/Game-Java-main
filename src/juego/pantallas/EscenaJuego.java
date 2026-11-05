package juego.pantallas;

import estructuras.Cola;
import estructuras.ColaEnlazada;
import estructuras.Lista;
import estructuras.ListaEnlazada;
import java.awt.Color;
import java.awt.Graphics2D;
import juego.entidades.DeudaFmi;
import juego.entidades.DolarBlue;
import juego.entidades.Enemigo;
import juego.entidades.Inflacion;
import juego.mundo.Camino;
import juego.reglas.GestorTorres;
import juego.reglas.Oleada;
import juego.reglas.Partida;
import motor.Escena;
import motor.Mundo;
import motor.Temporizador;

public class EscenaJuego extends Escena {

    // Constantes
    private static final int ORO_INICIAL = 150;
    private static final int VIDAS_INICIALES = 10;
    private static final int PUNTOS_POR_ENEMIGO = 10;
    private static final String AYUDA = "Clic: construir torre (50 de oro)     Z: deshacer     V: vender la última";

    // Atributos
    private final Temporizador aparicion = new Temporizador(1.5);
    private final Cola<Oleada> oleadas = new ColaEnlazada<>();
    private final Lista<Enemigo> enemigos = new ListaEnlazada<>();
    private Camino camino;
    private Mundo mundo;
    private Partida partida;
    private GestorTorres gestor;
    private Hud hud;
    private Oleada actual;

    // Entrada a la escena
    @Override
    public void alEntrar() {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();

        camino = new Camino();
        mundo = new Mundo(ancho, alto, new ListaEnlazada<>());

        oleadas.encolar(crearOleada(5, 0, 0));
        oleadas.encolar(crearOleada(5, 3, 0));
        oleadas.encolar(crearOleada(4, 4, 2));

        partida = new Partida(ORO_INICIAL, VIDAS_INICIALES, oleadas.tamanio());
        partida.setMensaje(AYUDA);
        gestor = new GestorTorres(mundo, enemigos, partida);
        hud = new Hud(partida, ancho, alto);
        actual = oleadas.desencolar();
    }

    // Carga de una oleada: todos los enemigos salen del inicio del camino
    private Oleada crearOleada(int inflaciones, int dolares, int deudas) {
        Oleada oleada = new Oleada();
        double x = camino.getX(0);
        double y = camino.getY(0);
        for (int i = 0; i < inflaciones; i++) {
            oleada.agregar(new Inflacion(x, y, camino));
        }
        for (int i = 0; i < dolares; i++) {
            oleada.agregar(new DolarBlue(x, y, camino));
        }
        for (int i = 0; i < deudas; i++) {
            oleada.agregar(new DeudaFmi(x, y, camino));
        }
        return oleada;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        gestor.actualizar(getEntrada());
        aparecerEnemigo(dt);
        mundo.actualizar(dt);
        revisarEnemigos();
        revisarProgreso();
    }

    // Aparición de la oleada actual
    private void aparecerEnemigo(double dt) {
        if (aparicion.avanzar(dt)) {
            Enemigo enemigo = actual.siguiente();
            if (enemigo != null) {
                enemigos.agregar(enemigo);
                mundo.agregar(enemigo);
            }
        }
    }

    // Enemigos muertos o que completaron la vuelta
    private void revisarEnemigos() {
        for (int i = enemigos.tamanio() - 1; i >= 0; i--) {
            Enemigo enemigo = enemigos.obtener(i);
            if (!enemigo.estaViva()) {
                partida.cobrar(enemigo.getRecompensa());
                partida.sumarPuntaje(PUNTOS_POR_ENEMIGO);
                enemigos.eliminar(i);
            } else if (enemigo.llegoAlFinal()) {
                partida.perderVida();
                enemigo.destruir();
                enemigos.eliminar(i);
            }
        }
    }

    // Siguiente oleada o fin de la partida
    private void revisarProgreso() {
        if (partida.perdida()) {
            getContexto().irA(new EscenaFin(partida.getPuntaje(), false));
            return;
        }
        if (!actual.terminada() || !enemigos.estaVacia()) {
            return;
        }
        if (oleadas.estaVacia()) {
            getContexto().irA(new EscenaFin(partida.getPuntaje(), true));
        } else {
            actual = oleadas.desencolar();
            partida.avanzarOleada();
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(new Color(20, 24, 36));
        g.fillRect(0, 0, getContexto().getAncho(), getContexto().getAlto());
        camino.dibujar(g);
        mundo.dibujar(g);
        hud.dibujar(g);
    }
}
