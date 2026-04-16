package dev.marcosmoreira.desktopconsultorio.shared.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Clase base para todos los ViewModels del sistema.
 * Proporciona propiedades comunes de loading y error,
 * y un método executeAsync para tareas en background con manejo consistente.
 */
public abstract class BaseViewModel {

    protected final BooleanProperty cargando = new SimpleBooleanProperty(false);
    protected final StringProperty mensajeError = new SimpleStringProperty("");

    public BooleanProperty cargandoProperty() { return cargando; }
    public StringProperty mensajeErrorProperty() { return mensajeError; }
    public boolean isCargando() { return cargando.get(); }

    /**
     * Ejecuta una tarea en un virtual thread con manejo consistente de errores.
     *
     * @param context descripción del contexto para mensajes de error
     * @param task tarea a ejecutar (puede lanzar Exception)
     * @param onSuccess callback en el UI thread si la tarea completa exitosamente
     */
    protected void executeAsync(String context, RunnableWithException task, Runnable onSuccess) {
        cargando.set(true);
        mensajeError.set("");
        Thread.ofVirtual().start(() -> {
            try {
                task.run();
                Platform.runLater(() -> {
                    cargando.set(false);
                    if (onSuccess != null) onSuccess.run();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    mensajeError.set("Error en " + context + ": " + e.getMessage());
                    cargando.set(false);
                });
            }
        });
    }

    @FunctionalInterface
    protected interface RunnableWithException {
        void run() throws Exception;
    }
}
