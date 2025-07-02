import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroUsuarios {
    private List<Usuarios> listaUsuarios;


    public RegistroUsuarios() {
        listaUsuarios = new ArrayList<>();
    }

    public void registrarUsuario(Usuarios usuario, boolean mostrarMensaje) {
        if (usuario == null ||
                usuario.getNombre() == null || usuario.getNombre().trim().isEmpty() ||
                usuario.getCedula() == null || usuario.getCedula().trim().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty() ||
                usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {

            if (mostrarMensaje) {
                JOptionPane.showMessageDialog(null, "Error: Todos los campos del usuario deben estar completos.");
            }
            return;
        }

        for (Usuarios u : listaUsuarios) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                if (mostrarMensaje) {
                    JOptionPane.showMessageDialog(null, "Error: Ya existe un usuario registrado con este correo.", "Usuario duplicado", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }

        listaUsuarios.add(usuario);
        if (mostrarMensaje) {
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
        }
    }

    public int cantidadUsuariosRegistrados() {
        return listaUsuarios.size();
    }

    public String mostrarUsuarios() {
        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados.");
            return "No hay usuarios";

        }
        else {

            StringBuilder sb = new StringBuilder();
            for (Usuarios u : listaUsuarios) {
                sb.append(u.toString()).append("\n");
            }
            return sb.toString();
        }}

    public String buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Ingrese un nombre para buscar";
        }

        StringBuilder resultado = new StringBuilder();
        for (Usuarios u : listaUsuarios) {
            if (u.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.append(u.toString()).append("\n");
            }
        }
        return resultado.length() > 0 ? resultado.toString() :
                "No se encontraron usuarios con ese nombre";
    }

    public String buscarPorCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return "Ingrese una cédula para buscar";
        }

        for (Usuarios u : listaUsuarios) {
            if (u.getCedula().equals(cedula)) {
                return u.toString();
            }
        }
        return "No se encontró usuario con cédula: " + cedula;
    }


    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }
    public String obtenerTecnicoDisponible() {
        Usuarios tecnicoDisponible = null;

        for (Usuarios u : listaUsuarios) {
            if (u.getRol().equalsIgnoreCase("tecnico")) {
                if (tecnicoDisponible == null || u.getFallasRegistradas() < tecnicoDisponible.getFallasRegistradas()) {
                    tecnicoDisponible = u;
                }
            }
        }

        if (tecnicoDisponible != null) {
            tecnicoDisponible.incrementarFallas();
            return tecnicoDisponible.getCorreo();
        } else {
            JOptionPane.showMessageDialog(null, "No hay técnicos disponibles.");
            return "";
        }
    }
    public void registroinicial (Usuarios usuario){
        listaUsuarios.add(usuario);

    }
}