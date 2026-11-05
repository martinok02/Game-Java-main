package juego.pantallas;

import contratos.Dibujable;
import java.awt.Graphics2D;
import juego.estetica.Retro;
import juego.reglas.Partida;
import ui.Texto;

public class Hud implements Dibujable {

    // Constantes
    private static final int ALTO_BARRA = 44;
    private static final int TAMANIO = 16;
    private static final int MARGEN = 16;
    private static final int Y_TEXTO = 28;
    private static final int X_RESERVAS = 214;

    // Atributos
    private final Partida partida;
    private final int ancho;
    private final int alto;

    // Constructor
    public Hud(Partida partida, int ancho, int alto) {
        if (partida == null) {
            throw new IllegalArgumentException("la partida no puede ser nula");
        }
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("dimensiones inválidas");
        }
        this.partida = partida;
        this.ancho = ancho;
        this.alto = alto;
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        Retro.bloque(g, Retro.NEGRO, 0, 0, ancho, ALTO_BARRA);
        Retro.bloque(g, Retro.CELESTE, 0, ALTO_BARRA, ancho, 2);

        Texto.izquierda(g, "Gobernabilidad: " + partida.getVidas(), MARGEN, Y_TEXTO, TAMANIO, Retro.ROSA);
        Texto.izquierda(g, "Reservas: $" + partida.getOro(), X_RESERVAS, Y_TEXTO, TAMANIO, Retro.SOL);
        Texto.centrado(g, "Crisis " + partida.getNivel() + " de " + partida.getTotalOleadas(),
                ancho * 0.62, Y_TEXTO, TAMANIO, Retro.CELESTE);
        Texto.derecha(g, "Votos: " + partida.getPuntaje(), ancho - MARGEN, Y_TEXTO, TAMANIO, Retro.BLANCO);

        Retro.bloque(g, Retro.NEGRO, 0, alto - 26, ancho, 26);
        Texto.centrado(g, partida.getMensaje(), ancho / 2.0, alto - 8, 14, Retro.BLANCO);
    }
}
