package dev.marcosmoreira.consultorio.profesionales.api.response;

import dev.marcosmoreira.consultorio.profesionales.domain.model.Profesional;

/**
 * DTO de salida resumido para listados de profesionales.
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class ProfesionalResumenResponse {

    private Long profesionalId;
    private Long usuarioId;
    private String nombreCompleto;
    private String especialidadBreve;
    private String registroProfesional;
    private String estadoProfesional;

    /**
     * Constructor vacío requerido por serialización.
     */
    public ProfesionalResumenResponse() {
    }

    /**
     * Construye la respuesta resumida.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado
     * @param nombreCompleto nombre completo derivado
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional
     * @param estadoProfesional estado lógico del profesional
     */
    public ProfesionalResumenResponse(
            Long profesionalId,
            Long usuarioId,
            String nombreCompleto,
            String especialidadBreve,
            String registroProfesional,
            String estadoProfesional
    ) {
        this.profesionalId = profesionalId;
        this.usuarioId = usuarioId;
        this.nombreCompleto = nombreCompleto;
        this.especialidadBreve = especialidadBreve;
        this.registroProfesional = registroProfesional;
        this.estadoProfesional = estadoProfesional;
    }

    /**
     * Construye la respuesta a partir del dominio.
     *
     * @param profesional profesional del dominio
     * @return DTO resumido
     */
    public static ProfesionalResumenResponse fromDomain(Profesional profesional) {
        if (profesional == null) {
            throw new IllegalArgumentException("El profesional no puede ser nulo.");
        }

        return new ProfesionalResumenResponse(
                profesional.getProfesionalId(),
                profesional.getUsuarioId(),
                profesional.getNombreCompleto(),
                profesional.getEspecialidadBreve(),
                profesional.getRegistroProfesional(),
                profesional.getEstadoProfesional() == null ? null : profesional.getEstadoProfesional().name()
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
}
