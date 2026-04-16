package dev.marcosmoreira.desktopconsultorio.modules.dashboard.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DashboardMetricModel {

    private final StringProperty titulo = new SimpleStringProperty();
    private final StringProperty valor = new SimpleStringProperty();
    private final StringProperty icono = new SimpleStringProperty();
    private final StringProperty colorClase = new SimpleStringProperty();

    public DashboardMetricModel() {}

    public DashboardMetricModel(String titulo, String valor, String icono, String colorClase) {
        this.titulo.set(titulo);
        this.valor.set(valor);
        this.icono.set(icono);
        this.colorClase.set(colorClase);
    }

    public StringProperty tituloProperty() { return titulo; }
    public StringProperty valorProperty() { return valor; }
    public StringProperty iconoProperty() { return icono; }
    public StringProperty colorClaseProperty() { return colorClase; }
    public String getTitulo() { return titulo.get(); }
    public String getValor() { return valor.get(); }
    public String getIcono() { return icono.get(); }
    public String getColorClase() { return colorClase.get(); }
}
