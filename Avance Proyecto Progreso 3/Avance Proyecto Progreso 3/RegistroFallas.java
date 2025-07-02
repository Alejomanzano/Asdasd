import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RegistroFallas {
    private static Queue<Fallas> colaFallas;
    private List<Usuarios> listaUsuarios;


    public RegistroFallas(List<Usuarios> listaUsuarios) {
        colaFallas = new LinkedList<>();
        this.listaUsuarios = listaUsuarios;

    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void RegistrarFalla(Fallas falla, String correoUsuario) {
        if (falla == null ||
                falla.getTipo() == null || falla.getTipo().trim().isEmpty() ||
                falla.getParroquia() == null || falla.getParroquia().trim().isEmpty() ||
                falla.getCodigoPostal() == null || falla.getCodigoPostal().trim().isEmpty() ||
                falla.getDescripcion() == null || falla.getDescripcion().trim().isEmpty() ||
                falla.getEstado() == null || falla.getEstado().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Error: Todos los campos de la falla deben estar completos");
            return;
        }

        // 👉 CONTAR cuántas fallas ya tiene el correo
        int contadorFallas = 0;
        for (Fallas f : colaFallas) {
            if (f.getUsuarioReporte().equalsIgnoreCase(correoUsuario)) {
                contadorFallas++;
            }
        }


        if (contadorFallas >= 5) {
            JOptionPane.showMessageDialog(null, "Este usuario ya ha registrado el máximo de 5 fallas");
            return;
        }

        // Verificar si el usuario existe
        Usuarios usuarioEncontrado = null;
        for (Usuarios u : listaUsuarios) {
            if (u.getCorreo().equalsIgnoreCase(correoUsuario)) {
                usuarioEncontrado = u;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Error: El usuario que intenta registrar la falla no existe.");
            return;
        }

        // Crear y registrar nueva falla
        Fallas nuevaFalla = new Fallas(
                falla.getTipo(), falla.getParroquia(), falla.getCodigoPostal(), falla.getDescripcion(),
                "Pendiente", correoUsuario, "Pendiente",falla.getGravedad()
        );

        colaFallas.add(nuevaFalla);
        usuarioEncontrado.incrementarFallas();

        JOptionPane.showMessageDialog(null, "Falla registrada correctamente por: " + usuarioEncontrado.getNombre());
    }

    public String mostrarTodasFallas() {

        if (colaFallas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay fallas registradas");
            return "No hay fallas";
        }

        StringBuilder sb = new StringBuilder();
        for (Fallas f : colaFallas) {
            sb.append(f.toString()).append("\n");
        }
        return sb.toString();

    }


    public int cantidadFallasPendientes() {
        return colaFallas.size();
    }

    public String buscarFallasPorCedula(String cedula, List<Usuarios> usuarios) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrado = false;

        // Buscar el usuario por cédula para obtener su correo
        String correoUsuario = "";
        for (Usuarios u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                correoUsuario = u.getCorreo();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return "No se encontró usuario con cédula: " + cedula;
        }

        // Buscar fallas asociadas a ese correo
        for (Fallas f : colaFallas) {
            if (f.getUsuarioReporte().equalsIgnoreCase(correoUsuario)) {
                resultado.append(f.toString()).append("\n");
            }
        }

        return resultado.length() > 0 ? resultado.toString() :
                "No hay fallas registradas para este usuario";
    }

    private String obtenerCorreoPorCedula(String cedula) {
        for (Usuarios u : listaUsuarios) {
            if (u.getCedula().equals(cedula)) {
                return u.getCorreo();
            }
        }
        return "";
    }


    public boolean asignarTecnicoAFalla(String cedulaUsuario, String correoTecnico) {
        String correoUsuario = obtenerCorreoPorCedula(cedulaUsuario);

        if (correoUsuario.isEmpty()) {
            return false; // No se encontró el usuario
        }

        boolean asignado = false;
        for (Fallas f : colaFallas) {
            if (f.getUsuarioReporte().equalsIgnoreCase(correoUsuario)) {
                // Eliminamos la condición de "Pendiente" para permitir reasignación
                f.setTecnicoAsignado(correoTecnico);
                asignado = true;
            }
        }
        return asignado;
    }

    public static Queue<Fallas> getColaFallas() {
        return colaFallas;
    }



    public boolean cambiarEstadoFalla(String cedulaUsuario, String nuevoEstado) {
        // Primero obtenemos el correo del usuario a partir de la cédula
        String correoUsuario = "";
        for (Usuarios u : listaUsuarios) {
            if (u.getCedula().equals(cedulaUsuario)) {
                correoUsuario = u.getCorreo();
                break;
            }
        }

        if (correoUsuario.isEmpty()) {
            return false; // No se encontró el usuario
        }

        boolean cambiado = false;
        for (Fallas f : colaFallas) {
            if (f.getUsuarioReporte().equalsIgnoreCase(correoUsuario)) {
                f.setEstado(nuevoEstado);
                cambiado = true;
            }
        }
        return cambiado;
    }


}
