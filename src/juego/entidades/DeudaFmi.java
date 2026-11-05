package juego.entidades;

import java.awt.Graphics2D;
import juego.estetica.Retro;
import juego.mundo.Camino;
import ui.Texto;

public class DeudaFmi extends Enemigo {

    // Constructor
    public DeudaFmi(double x, double y, Camino camino) {
        super(x, y, camino, 55, 90, 30);
    }

    // Consultas
    @Override
    public String getNombre() {
        return "Deuda con el FMI";
    }

    @Override
    public String getDescripcion() {
        return "Lenta y difícil de bajar";
    }

    // Blindaje: solo recibe la mitad del daño
    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(Math.max(1, danio / 2));
    }

    // Representación: un expediente pesado
    @Override
    protected void dibujarSprite(Graphics2D g, int x, int y) {
        Retro.bloque(g, Retro.NEGRO, x - 14, y - 12, 28, 26);
        Retro.bloque(g, Retro.GRIS, x - 12, y - 10, 24, 22);
        Retro.bloque(g, Retro.GRIS_OSCURO, x - 12, y + 6, 24, 6);
        Texto.centrado(g, "FMI", x, y + 3, 10, Retro.BLANCO);
    }
}
