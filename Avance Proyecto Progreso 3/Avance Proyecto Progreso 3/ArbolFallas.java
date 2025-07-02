public class ArbolFallas {
    private NodoFalla raiz;

    public void insertar(Fallas falla) {
        raiz = insertarRec(raiz, falla);
    }

    private NodoFalla insertarRec(NodoFalla nodo, Fallas falla) {
        if (nodo == null) {
            return new NodoFalla(falla);
        }

        // Cambio aquí: invertimos la comparación para ordenar de mayor a menor
        if (falla.getGravedad() > nodo.falla.getGravedad()) {
            nodo.izquierda = insertarRec(nodo.izquierda, falla);
        } else {
            nodo.derecha = insertarRec(nodo.derecha, falla);
        }

        return nodo;
    }

    // El resto de la clase permanece igual
    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        inOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void inOrdenRec(NodoFalla nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrdenRec(nodo.izquierda, sb);
            sb.append(nodo.falla.toString()).append("\n");
            inOrdenRec(nodo.derecha, sb);
        }
    }
}
