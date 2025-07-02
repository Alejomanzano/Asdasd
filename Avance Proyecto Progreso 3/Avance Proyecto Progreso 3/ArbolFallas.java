public class ArbolFallas {
    private NodoFalla raiz;

    public void insertar(Fallas falla) {
        raiz = insertarRec(raiz, falla);
    }

    private NodoFalla insertarRec(NodoFalla nodo, Fallas falla) {
        if (nodo == null) {
            return new NodoFalla(falla);
        }

        if (falla.getGravedad() < nodo.falla.getGravedad()) {
            nodo.izquierda = insertarRec(nodo.izquierda, falla);
        } else {
            nodo.derecha = insertarRec(nodo.derecha, falla);
        }

        return nodo;
    }

    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        inOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void inOrdenRec(NodoFalla nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrdenRec(nodo.derecha, sb);

            sb.append(nodo.falla.toString()).append("\n");
            inOrdenRec(nodo.izquierda, sb);

        }
    }
}
