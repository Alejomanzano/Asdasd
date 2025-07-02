@Override
public String toString() {
    // Buscar el usuario en el registro para obtener todos sus datos
    Usuarios usuario = buscarUsuarioReporte();
    
    return "ID: " + idUnico + "\n" +
           "Tipo: " + tipo + "\n" +
           "Ubicación: " + parroquia + " (CP: " + codigoPostal + ")\n" +
           "Descripción: " + descripcion + "\n" +
           "Estado: " + Estado + "\n" +
           "Reportado por:\n" +
           "  - Nombre: " + (usuario != null ? usuario.getNombre() : "N/A") + "\n" +
           "  - Cédula: " + (usuario != null ? usuario.getCedula() : "N/A") + "\n" +
           "  - Correo: " + usuarioReporte + "\n" +
           "Técnico asignado: " + tecnicoAsignado + "\n" +
           "Gravedad: " + gravedad + "\n";
}

// Método auxiliar para buscar el usuario que reportó la falla
private Usuarios buscarUsuarioReporte() {
    // Necesitamos acceso al registro de usuarios
    // Esto requiere una modificación en la estructura de las clases
    // Opción 1: Pasar la lista de usuarios al constructor de Fallas
    // Opción 2: Hacer este método en RegistroFallas y pasar la falla
    // Implementaremos una solución temporal:
    
    for (Usuarios u : RegistroFallas.getListaUsuarios()) {
        if (u.getCorreo().equalsIgnoreCase(usuarioReporte)) {
            return u;
        }
    }
    return null;
}
public class RegistroFallas {
    private static Queue<Fallas> colaFallas;
    private List<Usuarios> listaUsuarios;
    
    // Añade este método:
    public static List<Usuarios> getListaUsuarios() {
        // Necesitarás modificar esto según cómo tengas la estructura
        return listaUsuarios;
    }
    
    // ... resto del código ...
}

