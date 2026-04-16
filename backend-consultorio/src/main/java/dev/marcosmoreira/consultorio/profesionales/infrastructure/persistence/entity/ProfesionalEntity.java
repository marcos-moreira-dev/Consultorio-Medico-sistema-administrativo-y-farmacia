package dev.marcosmoreira.consultorio.profesionales.infrastructure.persistence.entity;

import dev.marcosmoreira.consultorio.profesionales.domain.enums.EstadoProfesional;
import dev.marcosmoreira.consultorio.shared.persistence.AuditableEntity;
import jakarta.persistence.*;

/**
 * Entidad JPA del módulo de profesionales.
 *
 * <p>Se alinea con la tabla {@code profesional} de la base de datos V2 del proyecto.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Entity
@Table(schema = "consultorio", name = "profesional")
public class ProfesionalEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profesional_id")
    private Long profesionalId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "especialidad_breve", length = 120)
    private String especialidadBreve;

    @Column(name = "registro_profesional", length = 50)
    private String registroProfesional;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_profesional", nullable = false, length = 20)
    private EstadoProfesional estadoProfesional;

    /**
     * Constructor vacío requerido por JPA.
     */
    public ProfesionalEntity() {
    }

    /**
     * Construye una entidad completa de profesional.
     *
     * @param profesionalId identificador del profesional
     * @param usuarioId identificador del usuario asociado, si existe
     * @param nombres nombres del profesional
     * @param apellidos apellidos del profesional
     * @param especialidadBreve especialidad breve
     * @param registroProfesional registro profesional
     * @param estadoProfesional estado lógico
     */
    public ProfesionalEntity(
            Long profesionalId,
            Long usuarioId,
            String nombres,
            String apellidos,
            String especialidadBreve,
            String registroProfesional,
            EstadoProfesional estadoProfesional
    ) {
        this.profesionalId = profesionalId;
        this.usuarioId = usuarioId;
        this.nombres = normalizeNullableText(nombres);
        this.apellidos = normalizeNullableText(apellidos);
        this.especialidadBreve = normalizeNullableText(especialidadBreve);
        this.registroProfesional = normalizeNullableText(registroProfesional);
        this.estadoProfesional = estadoProfesional;
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
        this.nombres = normalizeNullableText(nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = normalizeNullableText(apellidos);
    }

    public String getEspecialidadBreve() {
        return especialidadBreve;
    }

    public void setEspecialidadBreve(String especialidadBreve) {
        this.especialidadBreve = normalizeNullableText(especialidadBreve);
    }

    public String getRegistroProfesional() {
        return registroProfesional;
    }

    public void setRegistroProfesional(String registroProfesional) {
        this.registroProfesional = normalizeNullableText(registroProfesional);
    }

    public EstadoProfesional getEstadoProfesional() {
        return estadoProfesional;
    }

    public void setEstadoProfesional(EstadoProfesional estadoProfesional) {
        this.estadoProfesional = estadoProfesional;
    }

    /**
     * Normaliza un texto opcional.
     *
     * @param value texto a normalizar
     * @return texto con trim aplicado o {@code null} si queda vacío
     */
    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();
        return normalized.isEmpty() ? null : normalized;
    }
}
