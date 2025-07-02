import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

public class Ventana {
    private JPanel Principal;
    private JTabbedPane txtFallasMostrar;
    private JPanel RegistroUsuario;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JButton BtnAgg;
    private JButton BtnMostrar;
    private JButton BtnBuscar;
    private JTextArea textInfo;
    private JButton btnAggfalla;
    private JButton listarFallasButton;
    private JTextField txtTipo;
    private JTextField txtUbicacionPorCP;
    private JTextField txtDescripcion;
    private JTextField txtCorreoFalla;
    private JTextArea textinfodos;
    private JPasswordField psContrasenia;
    private JTextField txtCorreoIniciar;
    private JButton btnIniciarSesion;
    private JComboBox cboUbiParroquia;
    private JTextArea txtFallasRegistradas;
    private JButton btnMostrarFallasRegistradas;
    private JTextField txtIdUnicoFalla;
    private JTextField txtPonerACargo;
    private JButton btnAsignarTrabajador;
    private JRadioButton ButtonAActivo;
    private JRadioButton ButtonPendiente;
    private JTextArea txtEstadistica;
    private JComboBox cboBarriosEstadistica;
    private JTextField txtEstadisticaCP;
    private JButton BtnMostrarEstadisticas;
    private JRadioButton BtaActivoEstadistica;
    private JRadioButton BtaPendienteEstadistica;
    private JRadioButton ButtonFinalizado;
    private JRadioButton BtaFinalizadoEstadistica;
    private JTextField txtNombreTecnico;
    private JTextField txtCedulaTecnico;
    private JTextField txtCorreoTecnico;
    private JButton BtnAgregarTecnico;
    private JButton BtnMostrarTecnico;
    private JButton BtnBuscarTecnico;
    private JTextArea TxtATecnico;
    private JButton btnBuscarPorId;
    private JTextField textcedu;
    private JTextField textiddos;
    private JButton BtnFallasGravedad;
    private JTextArea textfallasgravedad;
    private JComboBox cboGravedad;
    private RegistroUsuarios registroUsuarios =new RegistroUsuarios();
    private RegistroFallas registroFallas = new RegistroFallas(registroUsuarios.getListaUsuarios());
    private RegistroUsuarios RegistroUsuarios = new RegistroUsuarios();
    private RegistroTecnicos registroTecnicos = new RegistroTecnicos();





    public Ventana() {

        Usuarios us1= new Usuarios("Derick","1724568922","Derick.tipan@udla.edu.ec","Tecnico");
        Usuarios us2= new Usuarios("Pedro","1708532115","Pedro.velastegui@udla.edu.ec","Usuario",0);
        Usuarios us3= new Usuarios("Kimberlly","1724568922","kim.ramos@udla.edu.ec","Tecnico");
        Usuarios us4 = new Usuarios("Ainhoa", "1723728885", "ainhoa.salas@udla.edu.ec", "Usuario", 0);
        registroUsuarios.registroinicial(us1);
        registroUsuarios.registroinicial(us2);
        registroUsuarios.registroinicial(us3);
        registroUsuarios.registroinicial(us4);

        ButtonGroup estadoGroup = new ButtonGroup();
        estadoGroup.add(ButtonAActivo);
        estadoGroup.add(ButtonPendiente);
        estadoGroup.add(ButtonFinalizado);
        ButtonPendiente.setSelected(true);

        BtnAgg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String cedula = txtCedula.getText();
                String correo = txtCorreo.getText();

                Usuarios nuevoUsuario = new Usuarios(nombre, cedula, correo, "Usuario");
                registroUsuarios.registrarUsuario(nuevoUsuario, true);
                registroFallas.setListaUsuarios(registroUsuarios.getListaUsuarios());

                txtNombre.setText("");
                txtCedula.setText("");
                txtCorreo.setText("");
            }
        });
        BtnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder usuariosStr = new StringBuilder();

                for (Usuarios u : registroUsuarios.getListaUsuarios()) {
                    if (u.getRol().equalsIgnoreCase("Usuario")) {
                        usuariosStr.append(u.toString()).append("\n");
                    }
                }

                if (usuariosStr.length() == 0) {
                    textInfo.setText("No hay usuarios registrados.");
                } else {
                    textInfo.setText(usuariosStr.toString());
                }
            }
        });
        BtnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String cedula = txtCedula.getText().trim();

                if (!nombre.isEmpty() && !cedula.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor use solo un campo de búsqueda a la vez",
                            "Búsqueda inválida",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!nombre.isEmpty()) {
                    textInfo.setText(registroUsuarios.buscarPorNombre(nombre));
                    txtCedula.setText("");
                } else if (!cedula.isEmpty()) {
                    textInfo.setText(registroUsuarios.buscarPorCedula(cedula));
                    txtNombre.setText("");
                } else {
                    // 👉 Nueva lógica: no mostrar todos los usuarios
                    JOptionPane.showMessageDialog(null,
                            "Debe ingresar un nombre o una cédula para realizar la búsqueda",
                            "Búsqueda vacía",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnAggfalla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = txtTipo.getText();
                String parroquia = (String) cboUbiParroquia.getSelectedItem();
                String codigoPostal = txtUbicacionPorCP.getText();
                String descripcion = txtDescripcion.getText();
                String correo = txtCorreoFalla.getText().trim();
                String encargado = registroUsuarios.obtenerTecnicoDisponible();
                int gravedad=Integer.parseInt(cboGravedad.getSelectedItem().toString());

                if (encargado.isEmpty()) {
                    return;
                }


                if (tipo.isEmpty() || "Seleccione una parroquia".equals(parroquia) ||
                        codigoPostal.isEmpty() || descripcion.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos son obligatorios",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Fallas nuevaFalla = new Fallas(tipo, parroquia, codigoPostal, descripcion,
                        "Pendiente", correo, encargado,gravedad);

                registroFallas.RegistrarFalla(nuevaFalla, correo);


                txtTipo.setText("");
                txtUbicacionPorCP.setText("");
                txtDescripcion.setText("");
                cboUbiParroquia.setSelectedIndex(0);
            }
        });
        listarFallasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textinfodos.setText(registroFallas.mostrarTodasFallas());
                String lista = registroFallas.mostrarTodasFallas();
                textinfodos.setText(lista);
            }
        });

        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correoIngresado = txtCorreoIniciar.getText().trim();
                String contraseniaIngresada = new String(psContrasenia.getPassword());


                String correoValido = "ariel.manzano@udla.edu.ec";
                String contraseniaValida = "1234";

                if (correoIngresado.equalsIgnoreCase(correoValido) &&
                        contraseniaIngresada.equals(contraseniaValida)) {

                    txtFallasMostrar.setSelectedComponent(RegistroUsuario);
                    JOptionPane.showMessageDialog(null, "¡Bienvenido Alejandro Manzano!");


                    txtCorreoIniciar.setText("");
                    psContrasenia.setText("");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Credenciales incorrectas",
                            "Error de autenticación",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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

                    // Filtrar por estado solo si no hay cédula (para mostrar TODO lo del usuario si se busca por cédula)
                    if (cedula.isEmpty()) {
                        String estado = f.getEstado();
                        if ((estado.equalsIgnoreCase("Activo") && !mostrarActivos) ||
                                (estado.equalsIgnoreCase("Pendiente") && !mostrarPendientes) ||
                                (estado.equalsIgnoreCase("Finalizado") && !mostrarFinalizados)) {
                            continue;
                        }
                    }

                    resultado.append(f.toString()).append("\n");
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

                if (fallaMostrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Busque una falla con el ID primero");
                    return;
                }

                // Verificar si el correo pertenece a un técnico válido
                boolean esTecnico = false;
                for (Usuarios u : registroUsuarios.getListaUsuarios()) {
                    if (u.getCorreo().equalsIgnoreCase(correoTecnico) &&
                            u.getRol().equalsIgnoreCase("tecnico")) {
                        esTecnico = true;
                        break;
                    }
                }

                if (!esTecnico) {
                    JOptionPane.showMessageDialog(null, "El correo no pertenece a un técnico válido");
                    return;
                }

                // Asignar técnico a la falla por ID
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
                    btnMostrarFallasRegistradas.doClick();  // refrescar la vista
                    txtPonerACargo.setText("");  // limpiar campo
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró una falla con ese ID");
                }
            }
        });
        ButtonAActivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIdUnicoFalla.getText().trim();
                String fallaMostrada = txtFallasRegistradas.getText().trim();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID primero");
                    ButtonPendiente.setSelected(true);
                    return;
                }

                if (fallaMostrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Busque una falla con el ID primero");
                    ButtonPendiente.setSelected(true);
                    return;
                }

                boolean exito = false;
                for (Fallas f : registroFallas.getColaFallas()) {
                    if (f.getIdUnico().equalsIgnoreCase(id)) {
                        f.setEstado("Activo");
                        exito = true;
                        break;
                    }
                }

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Estado cambiado a Activo");
                    btnMostrarFallasRegistradas.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado");
                    ButtonPendiente.setSelected(true);
                }
            }
        });
        ButtonPendiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIdUnicoFalla.getText().trim();
                String fallaMostrada = txtFallasRegistradas.getText().trim();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID primero");
                    return;
                }

                if (fallaMostrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Busque una falla con el ID primero");
                    return;
                }

                boolean exito = false;
                for (Fallas f : registroFallas.getColaFallas()) {
                    if (f.getIdUnico().equalsIgnoreCase(id)) {
                        f.setEstado("Pendiente");
                        exito = true;
                        break;
                    }
                }

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Estado cambiado a Pendiente");
                    btnMostrarFallasRegistradas.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado");
                    ButtonAActivo.setSelected(true);
                }
            }
        });
        ButtonFinalizado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIdUnicoFalla.getText().trim();
                String fallaMostrada = txtFallasRegistradas.getText().trim();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID primero");
                    return;
                }

                if (fallaMostrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Busque una falla con el ID primero");
                    return;
                }

                boolean exito = false;
                for (Fallas f : registroFallas.getColaFallas()) {
                    if (f.getIdUnico().equalsIgnoreCase(id)) {
                        f.setEstado("Finalizado");
                        exito = true;
                        break;
                    }
                }

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Estado cambiado a Finalizado");
                    btnMostrarFallasRegistradas.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo cambiar el estado");
                    ButtonPendiente.setSelected(true);
                }
            }
        });
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

                    resultado.append(f.toString()).append("\n");
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
        BtnAgregarTecnico.addActionListener(e -> {
            String nombre = txtNombreTecnico.getText();
            String cedula = txtCedulaTecnico.getText();
            String correo = txtCorreoTecnico.getText();

            Tecnico nuevoTecnico = new Tecnico(nombre, cedula, correo);
            registroTecnicos.registrarTecnico(nuevoTecnico);


            Usuarios nuevoUsuario = new Usuarios(nombre, cedula, correo, "Tecnico");
            registroUsuarios.registrarUsuario(nuevoUsuario, false); // no mostrar mensaje

            txtNombreTecnico.setText("");
            txtCedulaTecnico.setText("");
            txtCorreoTecnico.setText("");
        });
        BtnMostrarTecnico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder tecnicosStr = new StringBuilder();
                for (Usuarios u : registroUsuarios.getListaUsuarios()) {
                    if (u.getRol().equalsIgnoreCase("Tecnico")) {
                        tecnicosStr.append(u.toString()).append("\n\n");
                    }
                }

                if (tecnicosStr.length() == 0) {
                    TxtATecnico.setText("No hay técnicos registrados.");
                } else {
                    TxtATecnico.setText(tecnicosStr.toString());
                }
            }
        });
        BtnBuscarTecnico.addActionListener(e -> {
            String nombre = txtNombreTecnico.getText().trim();
            String cedula = txtCedulaTecnico.getText().trim();

            if (!nombre.isEmpty() && !cedula.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Por favor use solo un campo de búsqueda a la vez",
                        "Búsqueda inválida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            StringBuilder resultado = new StringBuilder();
            for (Usuarios u : registroUsuarios.getListaUsuarios()) {
                if (!u.getRol().equalsIgnoreCase("Tecnico")) continue;

                if (!nombre.isEmpty() && u.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    resultado.append(u.toString()).append("\n\n");
                } else if (!cedula.isEmpty() && u.getCedula().equals(cedula)) {
                    resultado.append(u.toString()).append("\n\n");
                }
            }

            if (resultado.length() == 0) {
                TxtATecnico.setText("No se encontraron técnicos con los datos ingresados.");
            } else {
                TxtATecnico.setText(resultado.toString());
            }
        });
        btnBuscarPorId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idBuscado = txtIdUnicoFalla.getText().trim();
                if (idBuscado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un ID de falla para buscar");
                    return;
                }

                for (Fallas f : registroFallas.getColaFallas()) {
                    if (f.getIdUnico().equalsIgnoreCase(idBuscado)) {
                        txtFallasRegistradas.setText(f.toString());
                        return;
                    }
                }

                txtFallasRegistradas.setText("No se encontró una falla con ID: " + idBuscado);
            }
        });
        BtnFallasGravedad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArbolFallas arbol = new ArbolFallas();
                Queue<Fallas> cola;
                cola = RegistroFallas.getColaFallas();
                for (Fallas f : cola) {
                    arbol.insertar(f);
                }

                String resultado = arbol.inOrden();

                if (resultado.isEmpty()) {
                    textfallasgravedad.setText("No hay fallas registradas");
                }else {
                    textfallasgravedad.setText(resultado);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
