btnAggfalla.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String tipo = txtTipo.getText();
        String parroquia = (String) cboUbiParroquia.getSelectedItem();
        String codigoPostal = txtUbicacionPorCP.getText();
        String descripcion = txtDescripcion.getText();
        String cedula = txtCorreoFalla.getText().trim(); // Ahora recibe cédula
        String encargado = registroUsuarios.obtenerTecnicoDisponible();
        int gravedad = Integer.parseInt(cboGravedad.getSelectedItem().toString());

        if (encargado.isEmpty()) {
            return;
        }

        if (tipo.isEmpty() || "Seleccione una parroquia".equals(parroquia) ||
                codigoPostal.isEmpty() || descripcion.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar el usuario por cédula para obtener su correo
        String correoUsuario = "";
        for (Usuarios u : registroUsuarios.getListaUsuarios()) {
            if (u.getCedula().equals(cedula)) {
                correoUsuario = u.getCorreo();
                break;
            }
        }

        if (correoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró un usuario con la cédula proporcionada",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fallas nuevaFalla = new Fallas(tipo, parroquia, codigoPostal, descripcion,
                "Pendiente", correoUsuario, encargado, gravedad);

        registroFallas.RegistrarFalla(nuevaFalla, correoUsuario);

        txtTipo.setText("");
        txtUbicacionPorCP.setText("");
        txtDescripcion.setText("");
        txtCorreoFalla.setText(""); // Limpiar el campo de cédula
        cboUbiParroquia.setSelectedIndex(0);
    }
});
