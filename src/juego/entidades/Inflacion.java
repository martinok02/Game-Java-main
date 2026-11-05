package juego.entidades;

import java.awt.Graphics2D;
import juego.estetica.Retro;
import juego.mundo.Camino;

public class Inflacion extends Enemigo {

    // Constantes
    private static final double AUMENTO_POR_TRAMO = 0.12;

    // Constructor
    public Inflacion(double x, double y, Camino camino) {
        super(x, y, camino, 70, 30, 10);
    }

    // Consultas
    @Override
    public String getNombre() {
        return "Inflación";
    }

    @Override
    public String getDescripcion() {
        return "Sube en cada tramo";
    }

    // Cuanto más avanza, más rápido va
    @Override
    protected double getRapidez() {
        return super.getRapidez() * (1 + AUMENTO_POR_TRAMO * getTramo());
    }

    // Representación: un globo que se infla
    @Override
    protected void dibujarSprite(Graphics2D g, int x, int y) {
        Retro.bloque(g, Retro.ROJO, x - 8, y - 12, 16, 20);
        Retro.bloque(g, Retro.ROJO, x - 11, y - 8, 22, 12);
        Retro.bloque(g, Retro.NARANJA, x - 8, y - 10, 4, 4);
        Retro.bloque(g, Retro.BLANCO, x - 1, y + 8, 2, 6);

        Retro.bloque(g, Retro.BLANCO, x - 6, y - 7, 3, 3);
        Retro.bloque(g, Retro.BLANCO, x + 3, y, 3, 3);
        Retro.bloque(g, Retro.BLANCO, x + 2, y - 7, 2, 2);
        Retro.bloque(g, Retro.BLANCO, x, y - 5, 2, 2);
        Retro.bloque(g, Retro.BLANCO, x - 2, y - 3, 2, 2);
        Retro.bloque(g, Retro.BLANCO, x - 4, y - 1, 2, 2);
    }
}
