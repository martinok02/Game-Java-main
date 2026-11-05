package entidades;

import estructuras.Lista;
import juego.contratos.Danable;

public abstract class Torre extends Entidad {

    // Constantes
    private static final double RADIO = 18;

    // Atributos
    private final double rango;
    private final int danio;
    private final double cadencia;
    private final int costo;
    private final Lista<? extends Danable> enemigos;
    private double recarga;
    private boolean disparo;

    // Constructor
    protected Torre(double x, double y, double rango, int danio, double cadencia, int costo,
            Lista<? extends Danable> enemigos) {
        super(x, y);
        if (!(rango > 0)) {
            throw new IllegalArgumentException("el rango debe ser positivo: " + rango);
        }
        if (danio < 0) {
            throw new IllegalArgumentException("el daño no puede ser negativo: " + danio);
        }
        if (!(cadencia > 0)) {
            throw new IllegalArgumentException("la cadencia debe ser positiva: " + cadencia);
        }
        if (costo < 0) {
            throw new IllegalArgumentException("el costo no puede ser negativo: " + costo);
        }
        if (enemigos == null) {
            throw new IllegalArgumentException("la lista de enemigos no puede ser nula");
        }
        this.rango = rango;
        this.danio = danio;
        this.cadencia = cadencia;
        this.costo = costo;
        this.enemigos = enemigos;
    }

    // Consultas
    public double getRango() {
        return rango;
    }

    public int getDanio() {
        return danio;
    }

    public double getCadencia() {
        return cadencia;
    }

    public int getCosto() {
        return costo;
    }

    public double getRadio() {
        return RADIO;
    }

    public boolean yaDisparo() {
        return disparo;
    }

    // Ciclo de vida: espera la recarga y ataca al primer enemigo en rango
    @Override
    public void actualizar(double dt) {
        recarga = Math.max(0, recarga - dt);
        if (recarga > 0) {
            return;
        }
        Danable objetivo = buscarObjetivo();
        if (objetivo != null) {
            disparar(objetivo);
            disparo = true;
            recarga = cadencia;
        }
    }

    // Primer enemigo vivo dentro del rango, o null si no hay ninguno
    private Danable buscarObjetivo() {
        for (int i = 0; i < enemigos.tamanio(); i++) {
            Danable e = enemigos.obtener(i);
            if (e.estaViva() && Math.hypot(getX() - e.getX(), getY() - e.getY()) <= rango) {
                return e;
            }
        }
        return null;
    }

    // Cada torre ataca a su manera
    protected abstract void disparar(Danable objetivo);
}
