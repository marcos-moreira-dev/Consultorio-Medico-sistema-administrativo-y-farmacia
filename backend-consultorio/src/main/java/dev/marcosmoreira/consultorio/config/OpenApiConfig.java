package dev.marcosmoreira.consultorio.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para documentar el backend del consultorio.
 *
 * <p>Define la información básica de la API y el esquema de seguridad Bearer JWT
 * usado por los endpoints protegidos. Esto facilita pruebas manuales, exploración
 * del contrato y estudio del backend.</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    /**
     * Construye la definición OpenAPI principal del backend.
     *
     * @return objeto OpenAPI configurado para Swagger UI y documentación del contrato
     */
    @Bean
    public OpenAPI consultorioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Consultorio Médico API")
                        .version("1.0")
                        .description("""
                                API del sistema administrativo de consultorio médico.
                                Incluye módulos de autenticación, pacientes, profesionales,
                                citas, atenciones, cobros, reportes y auditoría.
                                """)
                        .contact(new Contact()
                                .name("Marcos Moreira")
                                .email("marcos.moreira.dev@gmail.com")))
                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}
