public class NodoFalla {
    public Fallas falla;
    public NodoFalla izquierda;
    public NodoFalla derecha;

    public NodoFalla(Fallas falla) {
        this.falla = falla;
        this.izquierda = null;
        this.derecha = null;
    }
}
