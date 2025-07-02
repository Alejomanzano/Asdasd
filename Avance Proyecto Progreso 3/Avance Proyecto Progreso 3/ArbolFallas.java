public String formatearFalla(Fallas f) {
    // Buscar el usuario que reportó esta falla
    Usuarios usuario = null;
    for (Usuarios u : listaUsuarios) {
        if (u.getCorreo().equalsIgnoreCase(f.getUsuarioReporte())) {
            usuario = u;
            break;
        }
    }
    
    return "ID: " + f.getIdUnico() + "\n" +
           "Tipo: " + f.getTipo() + "\n" +
           "Ubicación: " + f.getParroquia() + 
           " (CP: " + f.getCodigoPostal() + ")\n" +
           "Descripción: " + f.getDescripcion() + "\n" +
           "Estado: " + f.getEstado() + "\n" +
           "Reportado por:\n" +
           "  - Nombre: " + (usuario != null ? usuario.getNombre() : "N/A") + "\n" +
           "  - Cédula: " + (usuario != null ? usuario.getCedula() : "N/A") + "\n" +
           "  - Correo: " + f.getUsuarioReporte() + "\n" +
           "Técnico asignado: " + f.getTecnicoAsignado() + "\n" +
           "Gravedad: " + f.getGravedad() + "\n\n";
}

public String mostrarTodasFallas() {
    if (colaFallas.isEmpty()) {
        return "No hay fallas registradas";
    }

    StringBuilder sb = new StringBuilder();
    for (Fallas f : colaFallas) {
        sb.append(formatearFalla(f));
    }
    return sb.toString();
}

btnMostrarFallasRegistradas.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String cedula = textcedu.getText().trim();
        String codigoPostal = txtEstadisticaCP.getText().trim();

        // Determinar estados seleccionados
        boolean mostrarActivos = ButtonAActivo.isSelected();
        boolean mostrarPendientes = ButtonPendiente.isSelected();
        boolean mostrarFinalizados = ButtonFinalizado.isSelected();

        if (!mostrarActivos && !mostrarPendientes && !mostrarFinalizados) {
            mostrarActivos = true;
            mostrarPendientes = true;
            mostrarFinalizados = true;
        }

        StringBuilder resultado = new StringBuilder();
        String correoUsuario = "";

        // Si hay cédula, buscar correo del usuario
        if (!cedula.isEmpty()) {
            for (Usuarios u : registroUsuarios.getListaUsuarios()) {
                if (u.getCedula().equals(cedula)) {
                    correoUsuario = u.getCorreo();
                    break;
                }
            }

            if (correoUsuario.isEmpty()) {
                txtFallasRegistradas.setText("No se encontró usuario con cédula: " + cedula);
                return;
            }
        }

        for (Fallas f : registroFallas.getColaFallas()) {
            // Si se ingresó cédula, mostrar solo fallas de ese usuario
            if (!cedula.isEmpty() && !f.getUsuarioReporte().equalsIgnoreCase(correoUsuario)) {
                continue;
            }

            // Si se ingresó código postal
            if (!codigoPostal.isEmpty()) {
                if (!f.getCodigoPostal().equalsIgnoreCase(codigoPostal)) {
                    continue;
                }
            }

            // Filtrar por estado
            String estado = f.getEstado();
            if ((estado.equalsIgnoreCase("Activo") && !mostrarActivos) ||
                (estado.equalsIgnoreCase("Pendiente") && !mostrarPendientes) ||
                (estado.equalsIgnoreCase("Finalizado") && !mostrarFinalizados)) {
                continue;
            }

            resultado.append(registroFallas.formatearFalla(f));
        }

        if (resultado.length() == 0) {
            txtFallasRegistradas.setText(cedula.isEmpty() ?
                    "No se encontraron fallas con los criterios seleccionados" :
                    "No hay fallas registradas para este usuario");
        } else {
            txtFallasRegistradas.setText(resultado.toString());
        }
    }
});
