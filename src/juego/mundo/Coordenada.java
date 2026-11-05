
package juego.mundo;

public class Coordenada {

    private final int x;
    private final int y;

    public Coordenada(int x, int y) {

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(
                "Las coordenadas no pueden ser negativas"
            );
        }

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Coordenada)) {
            return false;
        }

        Coordenada otra = (Coordenada) obj;

        return x == otra.x && y == otra.y;
    }
}
