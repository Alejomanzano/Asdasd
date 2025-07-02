public boolean puedeAsignarMasFallas(String correoTecnico) {
    int fallasPendientesActivas = 0;
    
    for (Fallas f : colaFallas) {
        if (f.getTecnicoAsignado().equalsIgnoreCase(correoTecnico) && 
            !f.getEstado().equalsIgnoreCase("Finalizado")) {
            fallasPendientesActivas++;
            
            if (fallasPendientesActivas >= 2) {
                return false;
            }
        }
    }
    return true;
}

btnAsignarTrabajador.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String idFalla = textiddos.getText().trim();
        String correoTecnico = txtPonerACargo.getText().trim();
        String fallaMostrada = txtFallasRegistradas.getText().trim();

        if (idFalla.isEmpty() || correoTecnico.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el ID y el correo del técnico");
            return;
        }

        // Verificar si el técnico existe y es válido
        boolean esTecnicoValido = false;
        for (Usuarios u : registroUsuarios.getListaUsuarios()) {
            if (u.getCorreo().equalsIgnoreCase(correoTecnico) &&
                u.getRol().equalsIgnoreCase("tecnico")) {
                esTecnicoValido = true;
                break;
            }
        }

        if (!esTecnicoValido) {
            JOptionPane.showMessageDialog(null, "El correo no pertenece a un técnico válido");
            return;
        }

        // Verificar límite de fallas asignadas
        if (!registroFallas.puedeAsignarMasFallas(correoTecnico)) {
            JOptionPane.showMessageDialog(null, 
                "Este técnico ya tiene 2 fallas pendientes/activas.\n" +
                "No se pueden asignar más hasta que finalice alguna.");
            return;
        }

        // Asignar técnico a la falla
        boolean asignado = false;
        for (Fallas f : registroFallas.getColaFallas()) {
            if (f.getIdUnico().equalsIgnoreCase(idFalla)) {
                f.setTecnicoAsignado(correoTecnico);
                asignado = true;
                break;
            }
        }

        if (asignado) {
            JOptionPane.showMessageDialog(null, "Técnico asignado correctamente");
            btnMostrarFallasRegistradas.doClick();
            txtPonerACargo.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una falla con ese ID");
        }
    }
});

// En los ActionListeners de ButtonAActivo, ButtonPendiente y ButtonFinalizado:

// Para ButtonFinalizado (estado Finalizado):
ButtonFinalizado.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = txtIdUnicoFalla.getText().trim();
        String fallaMostrada = txtFallasRegistradas.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID primero");
            return;
        }

        boolean exito = false;
        for (Fallas f : registroFallas.getColaFallas()) {
            if (f.getIdUnico().equalsIgnoreCase(id)) {
                f.setEstado("Finalizado");
                exito = true;
                
                // Notificar que ahora puede asignarse otra falla
                String tecnico = f.getTecnicoAsignado();
                int fallasActivas = registroFallas.contarFallasActivasPendientes(tecnico);
                JOptionPane.showMessageDialog(null,
                    "Estado cambiado a Finalizado.\n" +
                    "El técnico " + tecnico + " ahora tiene " + fallasActivas + 
                    " fallas pendientes/activas.");
                break;
            }
        }

        if (exito) {
            btnMostrarFallasRegistradas.doClick();
        } else {
            ButtonPendiente.setSelected(true);
            JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado");
        }
    }
});

// Para ButtonAActivo y ButtonPendiente, añade validación:
ButtonAActivo.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = txtIdUnicoFalla.getText().trim();
        
        for (Fallas f : registroFallas.getColaFallas()) {
            if (f.getIdUnico().equalsIgnoreCase(id)) {
                // Verificar que el técnico no tenga ya 2 fallas activas/pendientes
                if (!registroFallas.puedeAsignarMasFallas(f.getTecnicoAsignado())) {
                    JOptionPane.showMessageDialog(null,
                        "Este técnico ya tiene 2 fallas pendientes/activas.\n" +
                        "No se puede cambiar el estado a Activo.");
                    ButtonFinalizado.setSelected(true);
                    return;
                }
                
                f.setEstado("Activo");
                btnMostrarFallasRegistradas.doClick();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No se encontró la falla");
    }
});


// En los ActionListeners de ButtonAActivo, ButtonPendiente y ButtonFinalizado:

// Para ButtonFinalizado (estado Finalizado):
ButtonFinalizado.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = txtIdUnicoFalla.getText().trim();
        String fallaMostrada = txtFallasRegistradas.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID primero");
            return;
        }

        boolean exito = false;
        for (Fallas f : registroFallas.getColaFallas()) {
            if (f.getIdUnico().equalsIgnoreCase(id)) {
                f.setEstado("Finalizado");
                exito = true;
                
                // Notificar que ahora puede asignarse otra falla
                String tecnico = f.getTecnicoAsignado();
                int fallasActivas = registroFallas.contarFallasActivasPendientes(tecnico);
                JOptionPane.showMessageDialog(null,
                    "Estado cambiado a Finalizado.\n" +
                    "El técnico " + tecnico + " ahora tiene " + fallasActivas + 
                    " fallas pendientes/activas.");
                break;
            }
        }

        if (exito) {
            btnMostrarFallasRegistradas.doClick();
        } else {
            ButtonPendiente.setSelected(true);
            JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado");
        }
    }
});

// Para ButtonAActivo y ButtonPendiente, añade validación:
ButtonAActivo.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = txtIdUnicoFalla.getText().trim();
        
        for (Fallas f : registroFallas.getColaFallas()) {
            if (f.getIdUnico().equalsIgnoreCase(id)) {
                // Verificar que el técnico no tenga ya 2 fallas activas/pendientes
                if (!registroFallas.puedeAsignarMasFallas(f.getTecnicoAsignado())) {
                    JOptionPane.showMessageDialog(null,
                        "Este técnico ya tiene 2 fallas pendientes/activas.\n" +
                        "No se puede cambiar el estado a Activo.");
                    ButtonFinalizado.setSelected(true);
                    return;
                }
                
                f.setEstado("Activo");
                btnMostrarFallasRegistradas.doClick();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No se encontró la falla");
    }
});


public int contarFallasActivasPendientes(String correoTecnico) {
    int count = 0;
    for (Fallas f : colaFallas) {
        if (f.getTecnicoAsignado().equalsIgnoreCase(correoTecnico) && 
            !f.getEstado().equalsIgnoreCase("Finalizado")) {
            count++;
        }
    }
    return count;
}


btnAggfalla.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // ... (código existente para obtener datos)
        
        // Verificar si el técnico puede recibir más fallas
        if (!registroFallas.puedeAsignarMasFallas(encargado)) {
            JOptionPane.showMessageDialog(null,
                "El técnico " + encargado + " ya tiene 2 fallas pendientes/activas.\n" +
                "No se pueden asignar más fallas hasta que finalice alguna.");
            return;
        }
        
        // ... (resto del código existente)
    }
});


