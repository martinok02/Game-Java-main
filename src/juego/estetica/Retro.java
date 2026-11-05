package juego.estetica;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import ui.Texto;

public final class Retro {

    // Paleta
    public static final Color NOCHE = new Color(16, 20, 44);
    public static final Color NEGRO = new Color(12, 12, 20);
    public static final Color BLANCO = new Color(244, 244, 232);
    public static final Color CELESTE = new Color(116, 188, 236);
    public static final Color SOL = new Color(248, 196, 56);
    public static final Color NARANJA = new Color(228, 128, 40);
    public static final Color ROJO = new Color(216, 56, 56);
    public static final Color ROSA = new Color(232, 152, 168);
    public static final Color ROSA_OSCURO = new Color(184, 100, 124);
    public static final Color VERDE = new Color(72, 176, 88);
    public static final Color VERDE_OSCURO = new Color(32, 112, 64);
    public static final Color PASTO = new Color(64, 140, 72);
    public static final Color PASTO_OSCURO = new Color(56, 124, 64);
    public static final Color GRIS = new Color(136, 140, 152);
    public static final Color GRIS_OSCURO = new Color(72, 76, 92);
    public static final Color MADERA = new Color(164, 104, 56);
    public static final Color MADERA_OSCURA = new Color(108, 64, 36);

    // Separación de las líneas de monitor viejo
    private static final int PASO_LINEAS = 3;
    private static final Color LINEA = new Color(0, 0, 0, 40);

    // Sin instancias
    private Retro() {
    }

    // Bordes duros, sin suavizado
    public static void preparar(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }

    // Pieza básica de todos los sprites
    public static void bloque(Graphics2D g, Color color, int x, int y, int ancho, int alto) {
        g.setColor(color);
        g.fillRect(x, y, ancho, alto);
    }

    // Texto con sombra dura
    public static void titulo(Graphics2D g, String texto, double x, double y, int tamanio, Color color) {
        Texto.centrado(g, texto, x + 3, y + 3, tamanio, NEGRO);
        Texto.centrado(g, texto, x, y, tamanio, color);
    }

    // Efecto de monitor viejo
    public static void lineas(Graphics2D g, int ancho, int alto) {
        g.setColor(LINEA);
        for (int y = 0; y < alto; y += PASO_LINEAS) {
            g.drawLine(0, y, ancho, y);
        }
    }

    // Sol de Mayo hecho con bloques
    public static void sol(Graphics2D g, int cx, int cy, int radio) {
        int tam = 4;
        int rayos = 16;
        for (int i = 0; i < rayos; i++) {
            double angulo = 2 * Math.PI * i / rayos;
            for (int d = radio + 4; d <= radio + 16; d += tam) {
                bloque(g, i % 2 == 0 ? SOL : NARANJA, cx + (int) (Math.cos(angulo) * d) - 2,
                        cy + (int) (Math.sin(angulo) * d) - 2, tam, tam);
            }
        }
        for (int y = -radio; y <= radio; y += tam) {
            for (int x = -radio; x <= radio; x += tam) {
                if (x * x + y * y <= radio * radio) {
                    bloque(g, SOL, cx + x - 2, cy + y - 2, tam, tam);
                }
            }
        }
        bloque(g, NARANJA, cx - 10, cy - 6, 6, 4);
        bloque(g, NARANJA, cx + 4, cy - 6, 6, 4);
        bloque(g, NARANJA, cx - 6, cy + 8, 12, 4);
    }
}
