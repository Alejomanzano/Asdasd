public String mostrarTodasFallas() {
    if (colaFallas.isEmpty()) {
        return "No hay fallas registradas";
    }

    StringBuilder sb = new StringBuilder();
    for (Fallas f : colaFallas) {
        // Buscar el usuario que reportó esta falla
        Usuarios usuario = null;
        for (Usuarios u : listaUsuarios) {
            if (u.getCorreo().equalsIgnoreCase(f.getUsuarioReporte())) {
                usuario = u;
                break;
            }
        }
        
        sb.append("ID: ").append(f.getIdUnico()).append("\n")
          .append("Tipo: ").append(f.getTipo()).append("\n")
          .append("Ubicación: ").append(f.getParroquia())
          .append(" (CP: ").append(f.getCodigoPostal()).append(")\n")
          .append("Descripción: ").append(f.getDescripcion()).append("\n")
          .append("Estado: ").append(f.getEstado()).append("\n")
          .append("Reportado por:\n")
          .append("  - Nombre: ").append(usuario != null ? usuario.getNombre() : "N/A").append("\n")
          .append("  - Cédula: ").append(usuario != null ? usuario.getCedula() : "N/A").append("\n")
          .append("  - Correo: ").append(f.getUsuarioReporte()).append("\n")
          .append("Técnico asignado: ").append(f.getTecnicoAsignado()).append("\n")
          .append("Gravedad: ").append(f.getGravedad()).append("\n\n");
    }
    return sb.toString();
}



listarFallasButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        textinfodos.setText(registroFallas.mostrarTodasFallas());
    }
});


o
