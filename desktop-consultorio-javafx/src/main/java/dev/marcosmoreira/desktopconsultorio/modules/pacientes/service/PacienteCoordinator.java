package dev.marcosmoreira.desktopconsultorio.modules.pacientes.service;

import dev.marcosmoreira.desktopconsultorio.http.service.PacienteApiService;
import dev.marcosmoreira.desktopconsultorio.modules.pacientes.viewmodel.PacientesViewModel;

/**
 * Coordinador del módulo de pacientes.
 *
 * <p>Crea y configura el ViewModel, coordina la navegación entre módulos
 * y maneja diálogos de confirmación para operaciones críticas.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class PacienteCoordinator {

    private final PacienteApiService pacienteApi;
    private PacientesViewModel viewModel;

    public PacienteCoordinator(PacienteApiService pacienteApi) {
        this.pacienteApi = pacienteApi;
    }

    /**
     * Crea y retorna el ViewModel del módulo.
     */
    public PacientesViewModel createViewModel() {
        if (viewModel == null) {
            viewModel = new PacientesViewModel(pacienteApi);
        }
        return viewModel;
    }

    /**
     * Navega al módulo de citas filtrando por paciente.
     */
    public void irACitasDePaciente(Long pacienteId) {
        // TODO: Implementar navegación al módulo de citas con filtro
    }

    /**
     * Navega al módulo de atenciones del paciente.
     */
    public void irAAtencionesDePaciente(Long pacienteId) {
        // TODO: Implementar navegación al módulo de atenciones con filtro
    }
}
