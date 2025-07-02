import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroTecnicos {
    private List<Tecnico> listaTecnicos;

    public RegistroTecnicos() {
        listaTecnicos = new ArrayList<>();
    }

    public void registrarTecnico(Tecnico tecnico) {
        // Validación básica
        if (tecnico.getNombre().trim().isEmpty() ||
                tecnico.getCedula().trim().isEmpty() ||
                tecnico.getCorreo().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // Verificar si el técnico ya existe (por cédula o correo)
        if (existeTecnico(tecnico.getCedula(), tecnico.getCorreo())) {
            JOptionPane.showMessageDialog(null, "El técnico ya está registrado");
            return;
        }

        listaTecnicos.add(tecnico);
        JOptionPane.showMessageDialog(null, "Técnico registrado exitosamente");
    }

    private boolean existeTecnico(String cedula, String correo) {
        return listaTecnicos.stream()
                .anyMatch(t -> t.getCedula().equals(cedula) || t.getCorreo().equalsIgnoreCase(correo));
    }

    public String mostrarTodos() {
        if (listaTecnicos.isEmpty()) {
            return "No hay técnicos registrados";
        }

        StringBuilder sb = new StringBuilder();
        for (Tecnico t : listaTecnicos) {
            sb.append(t.toString()).append("\n\n");
        }
        return sb.toString();
    }

    public String buscarPorNombre(String nombre) {
        StringBuilder resultado = new StringBuilder();
        for (Tecnico t : listaTecnicos) {
            if (t.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.append(t.toString()).append("\n\n");
            }
        }
        return resultado.length() > 0 ? resultado.toString() : "No se encontraron técnicos";
    }

    public String buscarPorCedula(String cedula) {
        for (Tecnico t : listaTecnicos) {
            if (t.getCedula().equals(cedula)) {
                return t.toString();
            }
        }
        return "No se encontró técnico con cédula: " + cedula;
    }

    public boolean existeCorreoTecnico(String correo) {
        return listaTecnicos.stream()
                .anyMatch(t -> t.getCorreo().equalsIgnoreCase(correo));
    }

    public List<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }
}