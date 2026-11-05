package juego.pantallas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import motor.Entrada;
import motor.Escena;
import ui.Texto;

public class EscenaFin extends Escena {

    // Atributos
    private final int puntaje;
    private final boolean gano;

    // Constructor
    public EscenaFin(int puntaje, boolean gano) {
        if (puntaje < 0) {
            throw new IllegalArgumentException("el puntaje no puede ser negativo: " + puntaje);
        }
        this.puntaje = puntaje;
        this.gano = gano;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        Entrada entrada = getEntrada();
        if (entrada.fuePresionada(KeyEvent.VK_ENTER)) {
            getContexto().irA(new EscenaMenu());
        }
        if (entrada.fuePresionada(KeyEvent.VK_ESCAPE)) {
            getContexto().salir();
        }
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        int ancho = getContexto().getAncho();
        int alto = getContexto().getAlto();
        double centro = ancho / 2.0;

        g.setColor(new Color(20, 24, 36));
        g.fillRect(0, 0, ancho, alto);

        String titulo = gano ? "¡Ganaste!" : "Perdiste";
        Color color = gano ? new Color(120, 220, 140) : new Color(240, 110, 110);
        Texto.centrado(g, titulo, centro, 220, 56, color);
        Texto.centrado(g, "Puntaje: " + puntaje, centro, 300, 28, Color.WHITE);
        Texto.centrado(g, "ENTER: volver al menú     ESC: salir", centro, 400, 18, Color.LIGHT_GRAY);
    }
}
