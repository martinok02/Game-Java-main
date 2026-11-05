package juego.reglas;

import contratos.Estadisticas;

public class Partida implements Estadisticas {

    // Atributos
    private final int totalOleadas;
    private int oro;
    private int vidas;
    private int oleada;
    private int puntaje;
    private String mensaje;

    // Constructor
    public Partida(int oroInicial, int vidasIniciales, int totalOleadas) {
        if (oroInicial < 0) {
            throw new IllegalArgumentException("el oro inicial no puede ser negativo: " + oroInicial);
        }
        if (vidasIniciales <= 0) {
            throw new IllegalArgumentException("las vidas deben ser positivas: " + vidasIniciales);
        }
        if (totalOleadas <= 0) {
            throw new IllegalArgumentException("debe haber al menos una oleada: " + totalOleadas);
        }
        this.oro = oroInicial;
        this.vidas = vidasIniciales;
        this.totalOleadas = totalOleadas;
        this.oleada = 1;
        this.mensaje = "";
    }

    // Economía
    public int getOro() {
        return oro;
    }

    public boolean puedeComprar(int costo) {
        return costo >= 0 && costo <= oro;
    }

    public void comprar(int costo) {
        if (!puedeComprar(costo)) {
            throw new IllegalArgumentException("no se puede pagar el costo: " + costo);
        }
        oro -= costo;
    }

    public void cobrar(int monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("el monto no puede ser negativo: " + monto);
        }
        oro += monto;
    }

    // Daño al jugador
    public void perderVida() {
        vidas = Math.max(0, vidas - 1);
    }

    public boolean perdida() {
        return vidas == 0;
    }

    // Progreso
    public int getTotalOleadas() {
        return totalOleadas;
    }

    public void avanzarOleada() {
        if (oleada < totalOleadas) {
            oleada++;
        }
    }

    public void sumarPuntaje(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("los puntos no pueden ser negativos: " + puntos);
        }
        puntaje += puntos;
    }

    public void setMensaje(String mensaje) {
        if (mensaje == null) {
            throw new IllegalArgumentException("el mensaje no puede ser nulo");
        }
        this.mensaje = mensaje;
    }

    // Estadisticas (para el HUD)
    @Override
    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public int getVidas() {
        return vidas;
    }

    @Override
    public int getNivel() {
        return oleada;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
