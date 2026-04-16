package dev.marcosmoreira.desktopconsultorio.modules.pacientes.model;

import javafx.beans.property.*;

/**
 * Modelo de fila para la tabla de pacientes.
 *
 * <p>Representa una fila individual en el {@code TableView} de pacientes
 * con propiedades JavaFX observables para actualización reactiva.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteItemModel {

    private final LongProperty pacienteId = new SimpleLongProperty();
    private final StringProperty nombres = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty cedula = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();
    private final IntegerProperty totalCitas = new SimpleIntegerProperty();
    private final IntegerProperty totalAtenciones = new SimpleIntegerProperty();

    public LongProperty pacienteIdProperty() { return pacienteId; }
    public StringProperty nombresProperty() { return nombres; }
    public StringProperty apellidosProperty() { return apellidos; }
    public StringProperty cedulaProperty() { return cedula; }
    public StringProperty telefonoProperty() { return telefono; }
    public IntegerProperty totalCitasProperty() { return totalCitas; }
    public IntegerProperty totalAtencionesProperty() { return totalAtenciones; }

    public long getPacienteId() { return pacienteId.get(); }
    public void setPacienteId(long pacienteId) { this.pacienteId.set(pacienteId); }

    public String getNombres() { return nombres.get(); }
    public void setNombres(String nombres) { this.nombres.set(nombres); }

    public String getApellidos() { return apellidos.get(); }
    public void setApellidos(String apellidos) { this.apellidos.set(apellidos); }

    public String getCedula() { return cedula.get(); }
    public void setCedula(String cedula) { this.cedula.set(cedula); }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }

    public int getTotalCitas() { return totalCitas.get(); }
    public void setTotalCitas(int totalCitas) { this.totalCitas.set(totalCitas); }

    public int getTotalAtenciones() { return totalAtenciones.get(); }
    public void setTotalAtenciones(int totalAtenciones) { this.totalAtenciones.set(totalAtenciones); }

    public String getNombreCompleto() {
        return (nombres.get() != null ? nombres.get() : "") + " " + (apellidos.get() != null ? apellidos.get() : "");
    }
}
