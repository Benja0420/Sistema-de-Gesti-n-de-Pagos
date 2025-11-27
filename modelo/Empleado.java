package modelo;

import java.time.LocalDate;

public class Empleado {
    private int idEmpleado;
    private String nombreEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String tipoContrato;
    private boolean planSalud;
    private boolean afp;

    public Empleado() {
    }

    public Empleado(int idEmpleado, String nombreEmpleado, LocalDate fechaInicio, 
                    LocalDate fechaTermino, String tipoContrato, boolean planSalud, boolean afp) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.tipoContrato = tipoContrato;
        this.planSalud = planSalud;
        this.afp = afp;
    }

    // Getters y Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public boolean isPlanSalud() {
        return planSalud;
    }

    public void setPlanSalud(boolean planSalud) {
        this.planSalud = planSalud;
    }

    public boolean isAfp() {
        return afp;
    }

    public void setAfp(boolean afp) {
        this.afp = afp;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaTermino=" + fechaTermino +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", planSalud=" + planSalud +
                ", afp=" + afp +
                '}';
    }
}
