package dev.marcosmoreira.consultorio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Punto de entrada principal del backend del sistema de consultorio.
 *
 * <p>Esta clase cumple dos responsabilidades base dentro del arranque de la aplicación:</p>
 *
 * <ol>
 *   <li>Servir como clase bootstrap para Spring Boot.</li>
 *   <li>Definir el paquete base desde el cual Spring debe escanear componentes,
 *   configuraciones, servicios, controladores, entidades y repositorios.</li>
 * </ol>
 *
 * <p>Se usa explícitamente {@code scanBasePackages = "dev.marcosmoreira.consultorio"}
 * porque esta clase se encuentra dentro del subpaquete {@code app}. Si no se indicara
 * este paquete base, Spring solo escanearía {@code dev.marcosmoreira.consultorio.app}
 * y sus subpaquetes, dejando fuera módulos hermanos como {@code pacientes},
 * {@code usuarios}, {@code citas}, {@code config}, entre otros.</p>
 *
 * <p>Desde la perspectiva de arquitectura, esta clase no contiene lógica de negocio.
 * Su única finalidad es inicializar el contenedor de Spring y delegar el ciclo de vida
 * de la aplicación al framework.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = "dev.marcosmoreira.consultorio")
@EnableJpaRepositories(basePackages = "dev.marcosmoreira.consultorio")
@EntityScan(basePackages = "dev.marcosmoreira.consultorio")
public class BackendConsultorioApplication {

    /**
     * Método principal de arranque de la aplicación Spring Boot.
     *
     * <p>Este método delega en {@link SpringApplication#run(Class, String...)}
     * la creación del contexto de Spring, la carga de configuraciones,
     * el escaneo de beans y la inicialización del servidor embebido.</p>
     *
     * @param args argumentos recibidos desde la línea de comandos al iniciar
     *             la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendConsultorioApplication.class, args);
    }
}
