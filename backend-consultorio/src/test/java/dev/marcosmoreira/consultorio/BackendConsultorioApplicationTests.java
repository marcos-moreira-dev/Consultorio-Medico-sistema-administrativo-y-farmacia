package dev.marcosmoreira.consultorio;

import dev.marcosmoreira.consultorio.app.BackendConsultorioApplication;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas básicas sobre la clase principal del backend.
 *
 * <p>En esta etapa no se fuerza todavía un {@code contextLoads()} completo
 * porque el proyecto sigue en construcción y faltan piezas de configuración
 * y recursos. Esta prueba verifica, al menos, que la clase principal expone
 * correctamente su punto de entrada.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
class BackendConsultorioApplicationTests {

    /**
     * Verifica que la clase principal exponga un método {@code main}
     * público, estático y con firma estándar.
     *
     * @throws Exception si la reflexión falla al resolver el método
     */
    @Test
    void deberiaExponerMetodoMainPublicoYStatic() throws Exception {
        Method mainMethod = BackendConsultorioApplication.class.getMethod("main", String[].class);

        assertNotNull(mainMethod);
        assertTrue(Modifier.isPublic(mainMethod.getModifiers()));
        assertTrue(Modifier.isStatic(mainMethod.getModifiers()));
        assertEquals(void.class, mainMethod.getReturnType());
    }
}
