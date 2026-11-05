package juego.entidades;

import contratos.Colisionable;
import fisica.Cuerpo;
import fisica.Material;
import java.awt.Graphics2D;
import juego.contratos.Danable;
import juego.estetica.Retro;
import juego.mundo.Camino;

public abstract class Enemigo extends Cuerpo implements Danable {

    // Constantes
    private static final double RADIO = 12;
    private static final double FACTOR_RALENTIZADO = 0.5;

    // Atributos
    private final Camino camino;
    private final double rapidez;
    private final int vidaMaxima;
    private final int recompensa;
    private int vida;
    private int tramo;
    private double ralentizado;

    // Constructor
    protected Enemigo(double x, double y, Camino camino, double rapidez, int vida, int recompensa) {
        super(x, y, RADIO, Material.PLASTILINA);
        if (camino == null) {
            throw new IllegalArgumentException("el camino no puede ser nulo");
        }
        if (!(rapidez > 0)) {
            throw new IllegalArgumentException("la rapidez debe ser positiva: " + rapidez);
        }
        if (vida <= 0) {
            throw new IllegalArgumentException("la vida debe ser positiva: " + vida);
        }
        if (recompensa < 0) {
            throw new IllegalArgumentException("la recompensa no puede ser negativa: " + recompensa);
        }
        this.camino = camino;
        this.rapidez = rapidez;
        this.vidaMaxima = vida;
        this.recompensa = recompensa;
        this.vida = vida;
    }

    // Consultas
    public abstract String getNombre();

    public abstract String getDescripcion();

    public int getVida() {
        return vida;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public int getTramo() {
        return tramo;
    }

    // Una vuelta completa: recorrió todos los tramos y volvió al inicio
    public boolean llegoAlFinal() {
        return tramo >= camino.cantidadPuntos();
    }

    public double getVidaPorcentaje() {
        return (double) vida / vidaMaxima;
    }

    // Rapidez actual; cada enemigo la puede cambiar a su manera
    protected double getRapidez() {
        return rapidez;
    }

    // Daño recibido
    @Override
    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("el daño no puede ser negativo: " + danio);
        }
        vida = Math.max(0, vida - danio);
        if (vida == 0) {
            destruir();
        }
    }

    // Ralentización temporal (torre de hielo)
    public void ralentizar(double segundos) {
        if (!(segundos >= 0)) {
            throw new IllegalArgumentException("los segundos no pueden ser negativos: " + segundos);
        }
        ralentizado = Math.max(ralentizado, segundos);
    }

    // Ciclo de vida
    @Override
    public void actualizar(double dt) {
        if (llegoAlFinal()) {
            return;
        }
        double velocidad = ralentizado > 0 ? getRapidez() * FACTOR_RALENTIZADO : getRapidez();
        ralentizado = Math.max(0, ralentizado - dt);

        double destinoX = camino.getX(tramo + 1);
        double destinoY = camino.getY(tramo + 1);
        double dx = destinoX - getX();
        double dy = destinoY - getY();
        double distancia = Math.hypot(dx, dy);
        double paso = velocidad * dt;

        if (distancia <= paso) {
            setPosicion(destinoX, destinoY);
            tramo++;
        } else {
            setPosicion(getX() + dx / distancia * paso, getY() + dy / distancia * paso);
        }
    }

    // Representación: cada enemigo aporta su sprite
    @Override
    public void dibujar(Graphics2D g) {
        int x = (int) getX();
        int y = (int) getY();
        dibujarSprite(g, x, y);

        int ancho = (int) (2 * RADIO);
        int barraX = x - (int) RADIO;
        int barraY = y - (int) RADIO - 8;
        Retro.bloque(g, Retro.NEGRO, barraX, barraY, ancho, 4);
        Retro.bloque(g, Retro.VERDE, barraX, barraY, (int) (ancho * getVidaPorcentaje()), 4);
    }

    protected abstract void dibujarSprite(Graphics2D g, int x, int y);

    // El enemigo no rebota contra los bordes: el camino queda dentro del mapa
    @Override
    public void ajustarALimites(double ancho, double alto) {
    }

    // Los enemigos se pueden pisar sin empujarse ni salir de la pista
    @Override
    public void alColisionar(Colisionable otro) {
    }
}
