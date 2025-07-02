public class Usuarios {
    private String nombre;
    private String cedula;
    private String correo;
    private String rol;
    private int fallasRegistradas;

    // Constructor completo (el que te falta)
    public Usuarios(String nombre, String cedula, String correo, String rol, int fallasRegistradas) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.rol = rol;
        this.fallasRegistradas = fallasRegistradas;
    }

    // Constructor sin fallasRegistradas (lo usas al agregar nuevos)
    public Usuarios(String nombre, String cedula, String correo, String rol) {
        this(nombre, cedula, correo, rol, 0);
    }

    // Constructor por defecto de usuarios sin especificar rol (opcional)
    public Usuarios(String nombre, String cedula, String correo) {
        this(nombre, cedula, correo, "Usuario", 0);
    }

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public int getFallasRegistradas() {
        return fallasRegistradas;
    }

    public void incrementarFallas() {
        this.fallasRegistradas++;
    }


    @Override
    public String toString() {
        return "Usuario: " +
                "Nombre= " + nombre +
                ", Cédula= " + cedula +
                ", Correo= " + correo +
                ", Rol= " + rol +
                ", Fallas Registradas= " + fallasRegistradas;
    }
}

