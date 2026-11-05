package juego.contratos;

public interface Danable {

    // Posición en el mundo
    double getX();

    double getY();

    // Ciclo de vida
    boolean estaViva();

    // Reacción al recibir un ataque
    void recibirDanio(int danio);
}
