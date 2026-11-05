package entidades;

import estructuras.Lista;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import juego.contratos.Danable;
import juego.estetica.Retro;

public class TorreBasica extends Torre {

    // Constantes
    public static final int COSTO = 50;
    private static final double RANGO = 130;
    private static final int DANIO = 10;
    private static final double CADENCIA = 0.6;
    private static final double DURACION_RAYO = 0.08;

    // Atributos
    private Danable ultimoObjetivo;
    private double rayo;

    // Constructor
    public TorreBasica(double x, double y, Lista<? extends Danable> enemigos) {
        super(x, y, RANGO, DANIO, CADENCIA, COSTO, enemigos);
    }

    // Ataque directo
    @Override
    protected void disparar(Danable objetivo) {
        objetivo.recibirDanio(getDanio());
        ultimoObjetivo = objetivo;
        rayo = DURACION_RAYO;
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        super.actualizar(dt);
        rayo = Math.max(0, rayo - dt);
    }

    // Dibujo: el Cepo, una tabla de madera con candado
    @Override
    public void dibujar(Graphics2D g) {
        int x = (int) getX();
        int y = (int) getY();
        int rango = (int) getRango();

        g.setColor(new Color(255, 255, 255, 36));
        g.setStroke(new BasicStroke(1f));
        g.drawOval(x - rango, y - rango, 2 * rango, 2 * rango);

        Retro.bloque(g, Retro.GRIS_OSCURO, x - 15, y + 10, 30, 6);
        Retro.bloque(g, Retro.MADERA_OSCURA, x - 13, y - 16, 5, 26);
        Retro.bloque(g, Retro.MADERA_OSCURA, x + 8, y - 16, 5, 26);
        Retro.bloque(g, Retro.MADERA, x - 16, y - 10, 32, 12);
        Retro.bloque(g, Retro.NEGRO, x - 12, y - 6, 5, 5);
        Retro.bloque(g, Retro.NEGRO, x - 2, y - 6, 5, 5);
        Retro.bloque(g, Retro.NEGRO, x + 7, y - 6, 5, 5);
        Retro.bloque(g, Retro.SOL, x - 3, y + 1, 7, 6);

        if (rayo > 0) {
            g.setColor(Retro.SOL);
            g.setStroke(new BasicStroke(2f));
            g.drawLine(x, y, (int) ultimoObjetivo.getX(), (int) ultimoObjetivo.getY());
        }
    }
}
