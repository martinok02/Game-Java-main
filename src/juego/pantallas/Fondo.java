package juego.pantallas;

import contratos.Dibujable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import juego.estetica.Retro;
import ui.Texto;

public class Fondo implements Dibujable {

    // Constantes
    private static final int BALDOSA = 32;

    // Atributos: se pinta una sola vez y se reutiliza en cada cuadro
    private final BufferedImage imagen;

    // Constructor
    public Fondo(int ancho, int alto) {
        imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();
        Retro.preparar(g);
        pintarPasto(g, ancho, alto);
        pintarCasaRosada(g, 112, 118);
        pintarObelisco(g, 450, 490);
        g.dispose();
    }

    // Dibujo
    @Override
    public void dibujar(Graphics2D g) {
        g.drawImage(imagen, 0, 0, null);
    }

    // Damero de pasto con matas sueltas
    private void pintarPasto(Graphics2D g, int ancho, int alto) {
        for (int fila = 0; fila * BALDOSA < alto; fila++) {
            for (int columna = 0; columna * BALDOSA < ancho; columna++) {
                boolean par = (fila + columna) % 2 == 0;
                Retro.bloque(g, par ? Retro.PASTO : Retro.PASTO_OSCURO,
                        columna * BALDOSA, fila * BALDOSA, BALDOSA, BALDOSA);
                if ((fila * 7 + columna * 13) % 11 == 0) {
                    Retro.bloque(g, Retro.VERDE, columna * BALDOSA + 12, fila * BALDOSA + 14, 2, 6);
                    Retro.bloque(g, Retro.VERDE, columna * BALDOSA + 16, fila * BALDOSA + 12, 2, 8);
                }
            }
        }
    }

    // La base que hay que defender
    private void pintarCasaRosada(Graphics2D g, int x, int y) {
        Retro.bloque(g, Retro.ROSA_OSCURO, x, y + 10, 140, 62);
        Retro.bloque(g, Retro.ROSA, x + 3, y + 13, 134, 56);
        Retro.bloque(g, Retro.ROSA_OSCURO, x + 50, y - 2, 40, 14);
        Retro.bloque(g, Retro.ROSA, x + 53, y + 1, 34, 14);
        for (int i = 0; i < 6; i++) {
            int ventana = x + 12 + i * 21;
            Retro.bloque(g, Retro.NOCHE, ventana, y + 22, 8, 12);
            if (i != 2 && i != 3) {
                Retro.bloque(g, Retro.NOCHE, ventana, y + 44, 8, 12);
            }
        }
        Retro.bloque(g, Retro.NOCHE, x + 60, y + 44, 20, 25);

        Retro.bloque(g, Retro.BLANCO, x + 69, y - 22, 2, 22);
        Retro.bloque(g, Retro.CELESTE, x + 71, y - 22, 18, 4);
        Retro.bloque(g, Retro.BLANCO, x + 71, y - 18, 18, 4);
        Retro.bloque(g, Retro.CELESTE, x + 71, y - 14, 18, 4);

        Texto.centrado(g, "CASA ROSADA", x + 70, y + 85, 12, Retro.BLANCO);
    }

    // El Obelisco en el centro del mapa
    private void pintarObelisco(Graphics2D g, int cx, int base) {
        Retro.bloque(g, Retro.GRIS_OSCURO, cx - 30, base, 60, 8);
        for (int i = 0; i < 18; i++) {
            int mitad = 14 - i / 3;
            int y = base - 10 * (i + 1);
            Retro.bloque(g, Retro.GRIS, cx - mitad, y, 2 * mitad, 10);
            Retro.bloque(g, Retro.BLANCO, cx - mitad, y, mitad, 10);
        }
        for (int i = 0; i < 4; i++) {
            int mitad = 8 - 2 * i;
            Retro.bloque(g, Retro.BLANCO, cx - mitad, base - 186 - 4 * i, 2 * mitad, 4);
        }
    }
}
