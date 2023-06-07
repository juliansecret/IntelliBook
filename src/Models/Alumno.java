
package Models;


public class Alumno  {
    private int id;
    private String nombre;
    private String correo;
    private String numeroControl;
    private String telefono;
    private String carrera;
    
   //Constructor vacio
    public Alumno(){
        
    }
//Constructor con parametros
    public Alumno(int id, String nombre, String correo, String numeroControl, String telefono, String carrera) {
        this.nombre = nombre;
        this.correo = correo;
        this.numeroControl = numeroControl;
        this.telefono = telefono;
        this.carrera = carrera;
    }

    // Se agregan los metodos de getters y setter en la clase alumno
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
   

}

