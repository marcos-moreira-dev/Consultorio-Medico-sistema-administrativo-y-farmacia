package dev.marcosmoreira.desktopconsultorio.app;

import javafx.application.Application;

/**
 * Punto de entrada principal de la aplicación desktop.
 */
public final class AppLauncher {

    private AppLauncher() {
    }

    public static void main(String[] args) {
        Application.launch(DesktopConsultorioApplication.class, args);
    }
}
