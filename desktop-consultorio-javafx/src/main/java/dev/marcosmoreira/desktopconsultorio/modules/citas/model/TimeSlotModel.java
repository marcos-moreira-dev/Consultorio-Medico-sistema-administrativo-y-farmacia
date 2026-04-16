package dev.marcosmoreira.desktopconsultorio.modules.citas.model;

import javafx.beans.property.*;
import java.time.LocalTime;

/**
 * Modelo de franja horaria para el timeline del día.
 *
 * <p>Representa una franja de 1 hora en la agenda del consultorio.
 * Puede estar ocupada por una cita o disponible.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class TimeSlotModel {

    private final LocalTime hora;
    private final StringProperty paciente = new SimpleStringProperty();
    private final StringProperty profesional = new SimpleStringProperty();
    private final StringProperty motivo = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty("DISPONIBLE");
    private final LongProperty citaId = new SimpleLongProperty(-1);

    public TimeSlotModel(LocalTime hora) {
        this.hora = hora;
    }

    public static TimeSlotModel disponible(LocalTime hora) {
        TimeSlotModel slot = new TimeSlotModel(hora);
        slot.paciente.set("Disponible");
        slot.profesional.set("—");
        return slot;
    }

    public static TimeSlotModel ocupada(LocalTime hora, Long citaId, String paciente, String profesional, String motivo, String estado) {
        TimeSlotModel slot = new TimeSlotModel(hora);
        slot.citaId.set(citaId);
        slot.paciente.set(paciente);
        slot.profesional.set(profesional);
        slot.motivo.set(motivo);
        slot.estado.set(estado);
        return slot;
    }

    public LocalTime getHora() { return hora; }
    public StringProperty pacienteProperty() { return paciente; }
    public StringProperty profesionalProperty() { return profesional; }
    public StringProperty motivoProperty() { return motivo; }
    public StringProperty estadoProperty() { return estado; }
    public LongProperty citaIdProperty() { return citaId; }

    public String getPaciente() { return paciente.get(); }
    public String getProfesional() { return profesional.get(); }
    public String getMotivo() { return motivo.get(); }
    public String getEstado() { return estado.get(); }
    public long getCitaId() { return citaId.get(); }

    public boolean isDisponible() {
        return "DISPONIBLE".equalsIgnoreCase(estado.get());
    }
}
