package Models;


import java.time.LocalDate;

public class Prestamo {

    private int id;
    private String IdLibro;
    private String numeroControl;
    private LocalDate fechaEntrega;
    private LocalDate fechaSalida;
    private int unidades;

    public int getId() {
        return id;
    }

    // Métodos GETTERS y SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public String getIdLibro() {
        return IdLibro;
    }

    public void setIdLibro(String id_libro) {
        this.IdLibro = id_libro;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
}
