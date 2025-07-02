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

BtnMostrarEstadisticas.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String parroquia = (String) cboBarriosEstadistica.getSelectedItem();
        String codigoPostal = txtEstadisticaCP.getText().trim();

        boolean mostrarActivos = BtaActivoEstadistica.isSelected();
        boolean mostrarPendientes = BtaPendienteEstadistica.isSelected();
        boolean mostrarFinalizados = BtaFinalizadoEstadistica.isSelected();

        if (!mostrarActivos && !mostrarPendientes && !mostrarFinalizados) {
            mostrarActivos = true;
            mostrarPendientes = true;
            mostrarFinalizados = true;
        }

        StringBuilder resultado = new StringBuilder();
        resultado.append("=== ESTADÍSTICAS DE FALLAS ===\n\n");

        int totalFallas = 0;
        int fallasActivas = 0;
        int fallasPendientes = 0;
        int fallasFinalizadas = 0;

        for (Fallas f : registroFallas.getColaFallas()) {
            if (!"Seleccione una parroquia".equals(parroquia)) {
                if (!f.getParroquia().equalsIgnoreCase(parroquia)) {
                    continue;
                }
            }

            if (!codigoPostal.isEmpty()) {
                if (!f.getCodigoPostal().equalsIgnoreCase(codigoPostal)) {
                    continue;
                }
            }

            String estado = f.getEstado();
            if ((estado.equalsIgnoreCase("Activo") && !mostrarActivos) ||
                (estado.equalsIgnoreCase("Pendiente") && !mostrarPendientes) ||
                (estado.equalsIgnoreCase("Finalizado") && !mostrarFinalizados)) {
                continue;
            }

            totalFallas++;
            if (estado.equalsIgnoreCase("Activo")) fallasActivas++;
            else if (estado.equalsIgnoreCase("Pendiente")) fallasPendientes++;
            else if (estado.equalsIgnoreCase("Finalizado")) fallasFinalizadas++;

            resultado.append(registroFallas.formatearFalla(f));
        }

        resultado.append("\n=== RESUMEN ESTADÍSTICO ===\n");
        resultado.append("Total de fallas que coinciden: ").append(totalFallas).append("\n");
        if (mostrarActivos) {
            resultado.append("Fallas Activas: ").append(fallasActivas).append("\n");
        }
        if (mostrarPendientes) {
            resultado.append("Fallas Pendientes: ").append(fallasPendientes).append("\n");
        }
        if (mostrarFinalizados) {
            resultado.append("Fallas Finalizadas: ").append(fallasFinalizadas).append("\n");
        }

        txtEstadistica.setText(resultado.toString());
    }
});

BtnFallasGravedad.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        ArbolFallas arbol = new ArbolFallas();
        Queue<Fallas> cola = RegistroFallas.getColaFallas();
        
        for (Fallas f : cola) {
            arbol.insertar(f);
        }

        String resultadoOrdenado = arbol.inOrden();
        
        if (resultadoOrdenado.isEmpty()) {
            textfallasgravedad.setText("No hay fallas registradas");
        } else {
            // Reemplazar el formato antiguo con el nuevo formato
            StringBuilder sb = new StringBuilder();
            String[] fallas = resultadoOrdenado.split("\n\n");
            
            for (String fallaStr : fallas) {
                // Buscar el ID en el string para encontrar la falla completa
                String id = fallaStr.split("\n")[0].replace("ID: ", "").trim();
                Fallas falla = null;
                
                for (Fallas f : cola) {
                    if (f.getIdUnico().equals(id)) {
                        falla = f;
                        break;
                    }
                }
                
                if (falla != null) {
                    sb.append(registroFallas.formatearFalla(falla));
                }
            }
            
            textfallasgravedad.setText(sb.toString());
        }
    }
});
