package juego.mundo;

import estructuras.ListaCircular;

public class PruebaCamino {

    public static void main(String[] args) {

        ListaCircular<Coordenada> camino =
            new ListaCircular<>();

        // Crear los puntos del circuito
        camino.agregar(new Coordenada(50, 100));
        camino.agregar(new Coordenada(150, 100));
        camino.agregar(new Coordenada(250, 100));
        camino.agregar(new Coordenada(250, 200));
        camino.agregar(new Coordenada(250, 300));
        camino.agregar(new Coordenada(150, 300));
        camino.agregar(new Coordenada(50, 300));

        // Mostrar el recorrido
        System.out.println("CAMINO DEL JUEGO");

        for (int i = 0; i < camino.tamanio(); i++) {

            System.out.println(
                "Punto " + i + ": " + camino.obtener(i)
            );
        }

        // Comprobar la circularidad
        System.out.println("\nCOMPROBANDO RECORRIDO CIRCULAR");

        int siguiente;

        for (int i = 0; i < camino.tamanio(); i++) {

            siguiente = (i + 1) % camino.tamanio();

            System.out.println(
                camino.obtener(i) + " -> "
                + camino.obtener(siguiente)
            );
        }

        // Comprobar eliminación
        System.out.println("\nELIMINANDO UN PUNTO");

        Coordenada eliminada = camino.eliminar(2);

        System.out.println("Eliminado: " + eliminada);
        System.out.println("Tamaño actual: " + camino.tamanio());

        // Comprobar búsqueda
        Coordenada buscada = new Coordenada(250, 200);

        System.out.println(
            "Índice de " + buscada + ": "
            + camino.indiceDe(buscada)
        );
    }
}
