package dev.marcosmoreira.desktopconsultorio.modules.pacientes.model;

import javafx.beans.property.*;

/**
 * Modelo de filtros para la búsqueda de pacientes.
 *
 * <p>Permite filtrar la lista de pacientes por texto libre (nombre, apellido,
 * cédula) y fecha de nacimiento. Todos los campos son observables para
 * reactividad automática en la UI.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteFilterModel {

    private final StringProperty q = new SimpleStringProperty();
    private final StringProperty cedula = new SimpleStringProperty();

    public StringProperty qProperty() { return q; }
    public StringProperty cedulaProperty() { return cedula; }

    public String getQ() { return q.get(); }
    public void setQ(String q) { this.q.set(q); }

    public String getCedula() { return cedula.get(); }
    public void setCedula(String cedula) { this.cedula.set(cedula); }
}
