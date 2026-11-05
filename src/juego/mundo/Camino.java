package juego.mundo;

import contratos.Dibujable;
import estructuras.ListaCircular;
import java.awt.Color;
import java.awt.Graphics2D;
import juego.estetica.Retro;

public class Camino implements Dibujable {

    // Constantes
    private static final int ANCHO_PISTA = 36;
    private static final int ANCHO_CORDON = 44;
    private static final int LARGO_RAYA = 14;
    private static final int PASO_RAYA = 30;

    // Atributos
    private final ListaCircular<Coordenada> puntos = new ListaCircular<>();

    // Constructor
    public Camino() {
        // Circuito cerrado para una ventana de 900x600; del último punto se vuelve al primero
        agregar(80, 90);
        agregar(820, 90);
        agregar(820, 520);
        agregar(600, 520);
        agregar(600, 230);
        agregar(300, 230);
        agregar(300, 520);
        agregar(80, 520);
    }

    // Consultas: el índice da la vuelta, así que un enemigo solo tiene que sumarle 1
    public double getX(int indice) {
        return puntos.obtenerCircular(indice).getX();
    }

    public double getY(int indice) {
        return puntos.obtenerCircular(indice).getY();
    }

    public int cantidadPuntos() {
        return puntos.tamanio();
    }

    // Dibujo: cordón, asfalto y rayas de la avenida
    @Override
    public void dibujar(Graphics2D g) {
        int n = cantidadPuntos();
        for (int i = 0; i < n; i++) {
            dibujarTramo(g, i, ANCHO_CORDON, Retro.BLANCO);
        }
        for (int i = 0; i < n; i++) {
            dibujarTramo(g, i, ANCHO_PISTA, Retro.GRIS_OSCURO);
        }
        for (int i = 0; i < n; i++) {
            dibujarRayas(g, i);
        }
    }

    // Rectángulo que cubre un tramo recto
    private void dibujarTramo(Graphics2D g, int i, int ancho, Color color) {
        int x1 = (int) getX(i);
        int y1 = (int) getY(i);
        int x2 = (int) getX(i + 1);
        int y2 = (int) getY(i + 1);
        Retro.bloque(g, color, Math.min(x1, x2) - ancho / 2, Math.min(y1, y2) - ancho / 2,
                Math.abs(x2 - x1) + ancho, Math.abs(y2 - y1) + ancho);
    }

    // Línea punteada en el centro del tramo
    private void dibujarRayas(Graphics2D g, int i) {
        int x1 = (int) getX(i);
        int y1 = (int) getY(i);
        int x2 = (int) getX(i + 1);
        int y2 = (int) getY(i + 1);
        int largo = Math.abs(x2 - x1) + Math.abs(y2 - y1);
        int dirX = Integer.signum(x2 - x1);
        int dirY = Integer.signum(y2 - y1);

        for (int d = PASO_RAYA; d < largo - LARGO_RAYA; d += PASO_RAYA) {
            int x = x1 + dirX * d;
            int y = y1 + dirY * d;
            if (dirX != 0) {
                Retro.bloque(g, Retro.SOL, Math.min(x, x + dirX * LARGO_RAYA), y - 1, LARGO_RAYA, 3);
            } else {
                Retro.bloque(g, Retro.SOL, x - 1, Math.min(y, y + dirY * LARGO_RAYA), 3, LARGO_RAYA);
            }
        }
    }

    // Carga de un punto del circuito
    private void agregar(int x, int y) {
        puntos.agregar(new Coordenada(x, y));
    }
}
