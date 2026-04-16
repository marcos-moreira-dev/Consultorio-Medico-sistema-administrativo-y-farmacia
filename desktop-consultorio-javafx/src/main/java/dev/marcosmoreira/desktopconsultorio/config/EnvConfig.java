package dev.marcosmoreira.desktopconsultorio.config;

/**
 * Utilidad mínima para leer configuración desde variables de entorno o
 * propiedades del sistema sin acoplar la app a un framework externo.
 */
public final class EnvConfig {

    private EnvConfig() {
    }

    public static String get(String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }
        String env = System.getenv(key);
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        return defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
