package dev.marcosmoreira.desktopconsultorio.http.dto.cobros;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Registro de cobro asociado a una atención.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CobroDto {

    @JsonProperty("cobroId")
    private Long cobroId;

    @JsonProperty("atencionId")
    private Long atencionId;

    @JsonProperty("nombrePaciente")
    private String nombrePaciente;

    @JsonProperty("nombreProfesional")
    private String nombreProfesional;

    @JsonProperty("monto")
    private BigDecimal monto;

    @JsonProperty("metodoPago")
    private String metodoPago;

    @JsonProperty("estadoCobro")
    private String estadoCobro;

    @JsonProperty("observacionAdministrativa")
    private String observacionAdministrativa;

    @JsonProperty("fechaHoraRegistro")
    private LocalDateTime fechaHoraRegistro;

    public Long getCobroId() { return cobroId; }
    public void setCobroId(Long cobroId) { this.cobroId = cobroId; }
    public Long getAtencionId() { return atencionId; }
    public void setAtencionId(Long atencionId) { this.atencionId = atencionId; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public String getNombreProfesional() { return nombreProfesional; }
    public void setNombreProfesional(String nombreProfesional) { this.nombreProfesional = nombreProfesional; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public String getEstadoCobro() { return estadoCobro; }
    public void setEstadoCobro(String estadoCobro) { this.estadoCobro = estadoCobro; }
    public String getObservacionAdministrativa() { return observacionAdministrativa; }
    public void setObservacionAdministrativa(String observacionAdministrativa) { this.observacionAdministrativa = observacionAdministrativa; }
    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) { this.fechaHoraRegistro = fechaHoraRegistro; }
}
