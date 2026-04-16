package dev.marcosmoreira.desktopconsultorio.modules.dashboard.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.List;

public class ActividadRecienteModel {

    private final StringProperty fechaHora = new SimpleStringProperty();
    private final StringProperty usuario = new SimpleStringProperty();
    private final StringProperty tipo = new SimpleStringProperty();
    private final StringProperty descripcion = new SimpleStringProperty();
    private final StringProperty icono = new SimpleStringProperty();

    public ActividadRecienteModel() {}

    public ActividadRecienteModel(String fechaHora, String usuario, String tipo, String descripcion, String icono) {
        this.fechaHora.set(fechaHora);
        this.usuario.set(usuario);
        this.tipo.set(tipo);
        this.descripcion.set(descripcion);
        this.icono.set(icono);
    }

    public StringProperty fechaHoraProperty() { return fechaHora; }
    public StringProperty usuarioProperty() { return usuario; }
    public StringProperty tipoProperty() { return tipo; }
    public StringProperty descripcionProperty() { return descripcion; }
    public StringProperty iconoProperty() { return icono; }
    public String getFechaHora() { return fechaHora.get(); }
    public String getUsuario() { return usuario.get(); }
    public String getTipo() { return tipo.get(); }
    public String getDescripcion() { return descripcion.get(); }
    public String getIcono() { return icono.get(); }

    public static List<ActividadRecienteModel> demo() {
        return List.of(
                new ActividadRecienteModel("Hace 5 min", "admin", "COBRO", "Cobro registrado: Carlos Mendoza - $50.00", "💰"),
                new ActividadRecienteModel("Hace 15 min", "recepcion", "CITA", "Cita programada: María Solís - Dra. Mora", "📅"),
                new ActividadRecienteModel("Hace 30 min", "dr.paredes", "ATENCION", "Atención completada: José Villacrés", "🩺"),
                new ActividadRecienteModel("Hace 1 hora", "admin", "PACIENTE", "Nuevo paciente registrado: Ana Peñafiel", "👤"),
                new ActividadRecienteModel("Hace 2 horas", "dra.mora", "ATENCION", "Atención completada: Miguel Cevallos", "🩺")
        );
    }
}
