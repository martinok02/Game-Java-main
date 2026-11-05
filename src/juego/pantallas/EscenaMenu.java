package juego.pantallas;

import estructuras.Lista;
import estructuras.ListaEnlazada;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import juego.entidades.DeudaFmi;
import juego.entidades.DolarBlue;
import juego.entidades.Enemigo;
import juego.entidades.Inflacion;
import juego.estetica.Retro;
import juego.mundo.Camino;
import motor.Entrada;
import motor.Escena;
import motor.Temporizador;
import ui.Texto;

public class EscenaMenu extends Escena {

    // Constantes
    private static final int Y_ENEMIGOS = 322;

    // Atributos
    private final Temporizador parpadeo = new Temporizador(0.5);
    private final Lista<Enemigo> muestra = new ListaEnlazada<>();
    private boolean visible = true;

    // Entrada a la escena
    @Override
    public void alEntrar() {
        Camino camino = new Camino();
        int ancho = getContexto().getAncho();
        muestra.agregar(new Inflacion(ancho / 5.0, Y_ENEMIGOS, camino));
        muestra.agregar(new DolarBlue(ancho / 2.0, Y_ENEMIGOS, camino));
        muestra.agregar(new DeudaFmi(ancho * 4 / 5.0, Y_ENEMIGOS, camino));
    }

    // Actualización
    @Override
    public void actualizar(double dt) {
        Entrada entrada = getEntrada();
        if (parpadeo.avanzar(dt)) {
            visible = !visible;
        }
        if (entrada.fuePresionada(KeyEvent.VK_ENTER) || entrada.huboClick()) {
            getContexto().irA(new EscenaJuego());
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

        Retro.preparar(g);
        Retro.bloque(g, Retro.NOCHE, 0, 0, ancho, alto);
        dibujarFranja(g, ancho, 0);
        dibujarFranja(g, ancho, alto - 48);

        Retro.sol(g, ancho / 2, 112, 28);
        Retro.titulo(g, "DEFENSA DE LA ROSADA", centro, 214, 46, Retro.CELESTE);
        Texto.centrado(g, "Que la crisis no llegue a la Casa Rosada antes de terminar el mandato",
                centro, 250, 16, Retro.BLANCO);

        for (Enemigo enemigo : muestra) {
            enemigo.dibujar(g);
            Texto.centrado(g, enemigo.getNombre(), enemigo.getX(), Y_ENEMIGOS + 42, 16, Retro.SOL);
            Texto.centrado(g, enemigo.getDescripcion(), enemigo.getX(), Y_ENEMIGOS + 62, 13, Retro.BLANCO);
        }

        Texto.centrado(g, "Clic: poner un Cepo ($50)     Z: marcha atrás     V: privatizar el último",
                centro, 436, 15, Retro.BLANCO);
        if (visible) {
            Retro.titulo(g, "ENTER o clic para asumir", centro, 486, 24, Retro.SOL);
        }
        Texto.centrado(g, "ESC: renunciar", centro, alto - 62, 13, Retro.GRIS);

        Retro.lineas(g, ancho, alto);
    }

    // Franja con los colores de la bandera
    private void dibujarFranja(Graphics2D g, int ancho, int y) {
        Retro.bloque(g, Retro.CELESTE, 0, y, ancho, 16);
        Retro.bloque(g, Retro.BLANCO, 0, y + 16, ancho, 16);
        Retro.bloque(g, Retro.CELESTE, 0, y + 32, ancho, 16);
    }
}
