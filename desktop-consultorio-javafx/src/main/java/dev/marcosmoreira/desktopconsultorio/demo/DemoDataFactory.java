package dev.marcosmoreira.desktopconsultorio.demo;

import dev.marcosmoreira.desktopconsultorio.http.dto.atenciones.AtencionDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.auditoria.EventoAuditoriaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.citas.CitaDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.cobros.CobroDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.dashboard.DashboardResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.pacientes.PacienteResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.profesionales.ProfesionalResumenDto;
import dev.marcosmoreira.desktopconsultorio.http.dto.usuarios.UsuarioResumenDto;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.ActividadRecienteModel;
import dev.marcosmoreira.desktopconsultorio.modules.dashboard.model.DashboardMetricModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Fábrica centralizada de datos demo.
 * Single source of truth para todos los datos de demostración.
 */
public final class DemoDataFactory {

    private DemoDataFactory() {}

    // ============================================================
    // Pacientes (25 registros canónicos)
    // ============================================================

    private static final String[][] PACIENTES = {
            {"Carlos Alberto", "Mendoza Vera", "0912345678", "0991001001"},
            {"María Fernanda", "Solís Paredes", "0923456789", "0991001002"},
            {"José Luis", "Villacrés Cedeño", null, "0991001003"},
            {"Ana Lucía", "Peñafiel Torres", "0934567890", "0991001004"},
            {"Miguel Ángel", "Cevallos Rivas", null, "0991001005"},
            {"Rosa Elena", "Gómez Mendoza", "0945678901", "0991001006"},
            {"Fernando José", "Noboa Caamaño", "0956789012", "0991001007"},
            {"Carmen Elena", "Noboa Flores", null, "0991001008"},
            {"Roberto Carlos", "Espinoza Ruiz", "0967890123", "0991001009"},
            {"Gabriela Paola", "Vera Castillo", "0978901234", "0991001010"},
            {"Luis Eduardo", "Montalvo Salazar", "0989012345", "0991001011"},
            {"Patricia Elena", "Vargas Romero", null, "0991001012"},
            {"Andrés Felipe", "Delgado Mendoza", "0990123456", "0991001013"},
            {"Lucía Fernanda", "Salazar Cedeño", "0901234567", "0991001014"},
            {"Jorge Enrique", "Paredes Molina", "0911234567", "0991001015"},
            {"Sofía Alejandra", "Mora Cedeño", null, "0991001016"},
            {"Ricardo Antonio", "Proaño Vásquez", "0922345678", "0991001017"},
            {"Valentina María", "Ortiz Salinas", "0933456789", "0991001018"},
            {"Diego Fernando", "Bustamante Loor", "0944567890", "0991001019"},
            {"Carolina Isabel", "Morales Andrade", null, "0991001020"},
            {"Héctor Javier", "Ruíz Paredes", "0955678901", "0991001021"},
            {"Natalia Gabriela", "Torres Zambrano", "0966789012", "0991001022"},
            {"Mauricio Esteban", "Figueroa Reyes", "0977890123", "0991001023"},
            {"Daniela Alejandra", "Acosta Herrera", null, "0991001024"},
            {"Santiago Rafael", "Carrera Luna", "0988901234", "0991001025"}
    };

    public static List<PacienteResumenDto> getPacientes() {
        List<PacienteResumenDto> list = new ArrayList<>();
        for (int i = 0; i < PACIENTES.length; i++) {
            PacienteResumenDto p = new PacienteResumenDto();
            p.setPacienteId((long) (i + 1));
            p.setNombres(PACIENTES[i][0]);
            p.setApellidos(PACIENTES[i][1]);
            p.setCedula(PACIENTES[i][2]);
            p.setTelefono(PACIENTES[i][3]);
            p.setTotalCitas((int) (Math.random() * 10));
            p.setTotalAtenciones((int) (Math.random() * 8));
            list.add(p);
        }
        return list;
    }

    // ============================================================
    // Profesionales (9 registros canónicos)
    // ============================================================

    private static final String[][] PROFESIONALES = {
            {"Jorge Luis", "Paredes Molina", "Medicina general", "ACTIVO"},
            {"Elena Sofía", "Mora Cedeño", "Medicina interna", "ACTIVO"},
            {"Andrés Felipe", "Delgado Mendoza", "Cardiología", "ACTIVO"},
            {"Lucía Fernanda", "Salazar Cedeño", "Ginecología", "ACTIVO"},
            {"Ricardo Antonio", "Proaño Vásquez", "Pediatría", "ACTIVO"},
            {"Valentina María", "Ortiz Salinas", "Dermatología", "INACTIVO"},
            {"Diego Fernando", "Bustamante Loor", "Traumatología", "ACTIVO"},
            {"Carolina Isabel", "Morales Andrade", "Neurología", "ACTIVO"},
            {"Héctor Javier", "Ruíz Paredes", "Oftalmología", "ACTIVO"}
    };

    public static List<ProfesionalResumenDto> getProfesionales() {
        List<ProfesionalResumenDto> list = new ArrayList<>();
        for (int i = 0; i < PROFESIONALES.length; i++) {
            ProfesionalResumenDto p = new ProfesionalResumenDto();
            p.setProfesionalId((long) (i + 1));
            p.setNombres(PROFESIONALES[i][0]);
            p.setApellidos(PROFESIONALES[i][1]);
            p.setEspecialidadBreve(PROFESIONALES[i][2]);
            p.setEstadoProfesional(PROFESIONALES[i][3]);
            p.setTotalCitasMes(5 + (i * 3) % 12);
            list.add(p);
        }
        return list;
    }

    // ============================================================
    // Citas demo para una fecha dada
    // ============================================================

    public static List<CitaDto> getCitasForDate(LocalDate fecha) {
        List<CitaDto> list = new ArrayList<>();
        String[] pacientes = {"Carlos Alberto Mendoza", "María Fernanda Solís", "José Luis Villacrés",
                "Ana Lucía Peñafiel", "Miguel Ángel Cevallos"};
        String[] profesionales = {"Dr. Paredes - Medicina general", "Dra. Mora - Medicina interna",
                "Dr. Delgado - Cardiología", "Dra. Salazar - Ginecología", "Dr. Proaño - Pediatría"};
        String[] motivos = {"Control general", "Seguimiento", "Cardiología", "Consulta ginecológica", "Control pediátrico"};
        for (int i = 0; i < 5; i++) {
            CitaDto c = new CitaDto();
            c.setCitaId((long) (i + 1));
            c.setNombrePaciente(pacientes[i]);
            c.setNombreProfesional(profesionales[i]);
            c.setFechaHoraInicio(fecha.atTime(8 + i, 0));
            c.setMotivoBreve(motivos[i]);
            c.setEstadoCita("PROGRAMADA");
            list.add(c);
        }
        return list;
    }

    // ============================================================
    // Cobros (25 registros)
    // ============================================================

    public static List<CobroDto> getCobros() {
        List<CobroDto> list = new ArrayList<>();
        String[] metodos = {"EFECTIVO", "TRANSFERENCIA", "TARJETA"};
        String[] estados = {"PAGADO", "PAGADO", "PAGADO", "PAGADO", "PENDIENTE"};
        for (int i = 0; i < PACIENTES.length; i++) {
            CobroDto c = new CobroDto();
            c.setCobroId((long) (i + 1));
            c.setNombrePaciente(PACIENTES[i][0] + " " + PACIENTES[i][1]);
            double monto = 15 + (i * 5) % 40;
            c.setMonto(BigDecimal.valueOf(monto));
            c.setMetodoPago(metodos[i % metodos.length]);
            c.setEstadoCobro(estados[i % estados.length]);
            c.setFechaHoraRegistro(LocalDateTime.now().minusDays(i));
            list.add(c);
        }
        return list;
    }

    // ============================================================
    // Atenciones (19 registros)
    // ============================================================

    public static List<AtencionDto> getAtenciones() {
        List<AtencionDto> list = new ArrayList<>();
        String[] profesionales = {"Dr. Paredes - Medicina general", "Dra. Mora - Medicina interna", "Dr. Delgado - Cardiología"};
        String[] notas = {"Control general. Paciente refiere cansancio.", "Molestia en garganta con irritación moderada.",
                "Malestar estomacal con evolución corta.", "Chequeo de presión arterial. Valores normales.",
                "Control de diabetes. Glucosa en 142.", "Dolor lumbar crónico. Se indica reposo.",
                "Seguimiento de tratamiento. Mejoría notable.", "Consulta de primera vez. Estudios solicitados.",
                "Resultados de laboratorio normales.", "Revisión de medicamentos. Ajuste de dosis.",
                "Certificado médico deportivo.", "Dolor de cabeza recurrente. TAC solicitada.",
                "Control prenatal. Todo normal.", "Alergia estacional. Antihistamínicos recetados.",
                "Infección urinaria. Antibiótico recetado."};
        for (int i = 0; i < 19; i++) {
            AtencionDto a = new AtencionDto();
            a.setAtencionId((long) (i + 1));
            a.setNombrePaciente(PACIENTES[i][0] + " " + PACIENTES[i][1]);
            a.setNombreProfesional(profesionales[i % profesionales.length]);
            a.setFechaHoraAtencion(LocalDateTime.now().minusDays(i).withHour(8 + (i % 9)).withMinute(i * 3 % 60));
            a.setNotaBreve(notas[i % notas.length]);
            list.add(a);
        }
        return list;
    }

    // ============================================================
    // Dashboard métricas (computadas desde datos demo)
    // ============================================================

    public static DashboardResumenDto getDashboardResumen() {
        DashboardResumenDto r = new DashboardResumenDto();
        r.setTotalPacientes((long) PACIENTES.length);
        r.setCitasHoy(5L);
        r.setCitasSemana(23L);
        r.setAtencionesMes(19L);
        r.setCobrosMes((long) getCobros().size());
        r.setTotalCobradoMes(getCobros().stream()
                .filter(c -> "PAGADO".equals(c.getEstadoCobro()))
                .map(CobroDto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        r.setCobrosPendientes(getCobros().stream()
                .filter(c -> "PENDIENTE".equals(c.getEstadoCobro())).count());
        r.setProfesionalesActivos((long) getProfesionales().stream()
                .filter(p -> "ACTIVO".equals(p.getEstadoProfesional())).count());
        r.setGeneradoEn(LocalDateTime.now().toString());
        return r;
    }

    public static List<DashboardMetricModel> getDashboardMetrics() {
        DashboardResumenDto r = getDashboardResumen();
        List<DashboardMetricModel> list = new ArrayList<>();
        list.add(new DashboardMetricModel("Pacientes", String.valueOf(PACIENTES.length), "👥", "metric-pacientes"));
        list.add(new DashboardMetricModel("Citas Hoy", "5", "📅", "metric-citas"));
        list.add(new DashboardMetricModel("Cobros del Mes", "$" + r.getTotalCobradoMes().toPlainString(), "💰", "metric-cobros"));
        list.add(new DashboardMetricModel("Profesionales", String.valueOf(r.getProfesionalesActivos()), "🏥", "metric-profesionales"));
        return list;
    }

    // ============================================================
    // Actividad reciente
    // ============================================================

    public static List<ActividadRecienteModel> getActividadReciente() {
        return List.of(
                new ActividadRecienteModel("Hace 5 min", "admin", "COBRO", "Cobro registrado: Carlos Mendoza - $50.00", "💰"),
                new ActividadRecienteModel("Hace 15 min", "recepcion", "CITA", "Cita programada: María Solís - Dra. Mora", "📅"),
                new ActividadRecienteModel("Hace 30 min", "dr.paredes", "ATENCION", "Atención completada: José Villacrés", "🩺"),
                new ActividadRecienteModel("Hace 1 hora", "admin", "PACIENTE", "Nuevo paciente registrado: Ana Peñafiel", "👤"),
                new ActividadRecienteModel("Hace 2 horas", "dra.mora", "ATENCION", "Atención completada: Miguel Cevallos", "🩺")
        );
    }

    // ============================================================
    // Usuarios demo
    // ============================================================

    public static List<UsuarioResumenDto> getUsuarios() {
        List<UsuarioResumenDto> list = new ArrayList<>();
        list.add(crearUsuario(1L, 1L, "ADMIN_CONSULTORIO", "Administrador", "admin.consultorio", "Activo"));
        list.add(crearUsuario(2L, 2L, "OPERADOR_CONSULTORIO", "Operador", "recepcion.ana", "Activo"));
        list.add(crearUsuario(3L, 3L, "PROFESIONAL_CONSULTORIO", "Profesional", "dr.paredes", "Activo"));
        list.add(crearUsuario(4L, 3L, "PROFESIONAL_CONSULTORIO", "Profesional", "dra.mora", "Activo"));
        list.add(crearUsuario(5L, 2L, "OPERADOR_CONSULTORIO", "Operador", "operador2", "Inactivo"));
        return list;
    }

    private static UsuarioResumenDto crearUsuario(Long id, Long rolId, String rolCodigo, String rolNombre, String username, String estado) {
        UsuarioResumenDto u = new UsuarioResumenDto();
        u.setUsuarioId(id);
        u.setRolId(rolId);
        u.setRolCodigo(rolCodigo);
        u.setRolNombre(rolNombre);
        u.setUsername(username);
        u.setNombreCompleto(username);
        u.setEstado(estado);
        return u;
    }

    // ============================================================
    // Auditoría demo
    // ============================================================

    public static List<EventoAuditoriaDto> getAuditEvents() {
        List<EventoAuditoriaDto> list = new ArrayList<>();
        EventoAuditoriaDto e1 = new EventoAuditoriaDto();
        e1.setEventoId(1L);
        e1.setModulo("PACIENTES");
        e1.setTipoEvento("CREATE");
        e1.setUsername("admin.consultorio");
        e1.setDescripcion("Paciente creado: Carlos Alberto Mendoza Vera");
        e1.setFechaHora(LocalDateTime.now().minusMinutes(5));
        list.add(e1);

        EventoAuditoriaDto e2 = new EventoAuditoriaDto();
        e2.setEventoId(2L);
        e2.setModulo("COBROS");
        e2.setTipoEvento("CREATE");
        e2.setUsername("recepcion.ana");
        e2.setDescripcion("Cobro registrado: $50.00 - Carlos Mendoza");
        e2.setFechaHora(LocalDateTime.now().minusMinutes(15));
        list.add(e2);
        return list;
    }
}
