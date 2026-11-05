package juego;

import juego.pantallas.EscenaMenu;
import motor.Ventana;

public class Main {

    // Punto de entrada
    public static void main(String[] args) {
        Ventana.abrir("Defensa en el Circuito", 900, 600, new EscenaMenu());
    }
}
