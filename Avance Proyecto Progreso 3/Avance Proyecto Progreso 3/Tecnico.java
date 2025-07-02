public class Tecnico {
    private String nombre;
    private String cedula;
    private String correo;
    private final String rol = "Tecnico";  // Ahora es constante

    public Tecnico(String nombre, String cedula, String correo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



    @Override
    public String toString() {
        return "Técnico:\n" +
                "Nombre: " + nombre + "\n" +
                "Cédula: " + cedula + "\n" +
                "Correo: " + correo + "\n" +
                "Rol: " + rol;
    }
}