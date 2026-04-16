package dev.marcosmoreira.consultorio.profesionales.api.response;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;
import java.time.LocalDateTime;

/**
 * DTO de salida con el detalle de un profesional.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ProfesionalResponse {

    private Long profesionalId;
    private Long usuarioId;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String especialidadBreve;
    private String registroProfesional;
    private String estadoProfesional;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ProfesionalResponse() {
    }

    /**
     * Construye la respuesta completa.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param nombreCompleto nombre completo derivado
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional
     * @param estadoProfesional estado lógico del profesional
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de última actualización
     */
    public ProfesionalResponse(
            Long profesionalId,
            Long usuarioId,
            String nombres,
            String apellidos,
            String nombreCompleto,
            String especialidadBreve,
            String registroProfesional,
            String estadoProfesional,
            LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacion
    ) {
        this.profesionalId = profesionalId;
        this.usuarioId = usuarioId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreCompleto = nombreCompleto;
        this.especialidadBreve = especialidadBreve;
        this.registroProfesional = registroProfesional;
        this.estadoProfesional = estadoProfesional;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Construye la respuesta a partir del dominio.
     *
     * @param profesional profesional del dominio
     * @return DTO listo para serializar
     */
    public static ProfesionalResponse fromDomain(Profesional profesional) {
        if (profesional == null) {
            throw new IllegalArgumentException("El profesional no puede ser nulo.");
        }

        return new ProfesionalResponse(
                profesional.getProfesionalId(),
                profesional.getUsuarioId(),
                profesional.getNombres(),
                profesional.getApellidos(),
                profesional.getNombreCompleto(),
                profesional.getEspecialidadBreve(),
                profesional.getRegistroProfesional(),
                profesional.getEstadoProfesional() == null ? null : profesional.getEstadoProfesional().name(),
                profesional.getFechaCreacion(),
                profesional.getFechaActualizacion()
        );
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEspecialidadBreve() {
        return especialidadBreve;
    }

    public void setEspecialidadBreve(String especialidadBreve) {
        this.especialidadBreve = especialidadBreve;
    }

    public String getRegistroProfesional() {
        return registroProfesional;
    }

    public void setRegistroProfesional(String registroProfesional) {
        this.registroProfesional = registroProfesional;
    }

    public String getEstadoProfesional() {
        return estadoProfesional;
    }

    public void setEstadoProfesional(String estadoProfesional) {
        this.estadoProfesional = estadoProfesional;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
