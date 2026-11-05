package juego.entidades;

import java.awt.Graphics2D;
import juego.estetica.Retro;
import juego.mundo.Camino;
import ui.Texto;

public class DolarBlue extends Enemigo {

    // Constantes
    private static final double FACTOR_CORRIDA = 1.5;
    private static final double VIDA_CORRIDA = 0.5;

    // Constructor
    public DolarBlue(double x, double y, Camino camino) {
        super(x, y, camino, 110, 25, 15);
    }

    // Consultas
    @Override
    public String getNombre() {
        return "Dólar Blue";
    }

    @Override
    public String getDescripcion() {
        return "Herido, se dispara";
    }

    // Corrida: con poca vida acelera
    @Override
    protected double getRapidez() {
        if (getVidaPorcentaje() < VIDA_CORRIDA) {
            return super.getRapidez() * FACTOR_CORRIDA;
        }
        return super.getRapidez();
    }

    // Representación: un billete
    @Override
    protected void dibujarSprite(Graphics2D g, int x, int y) {
        Retro.bloque(g, Retro.VERDE_OSCURO, x - 15, y - 9, 30, 18);
        Retro.bloque(g, Retro.VERDE, x - 13, y - 7, 26, 14);
        Retro.bloque(g, Retro.VERDE_OSCURO, x - 7, y - 6, 14, 12);
        Texto.centrado(g, "$", x, y + 5, 14, Retro.CELESTE);
    }
}
