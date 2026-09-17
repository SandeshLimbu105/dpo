package org.texas.dposervice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI dpoServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DPO Service — Data Protection Officer API")
                        .version("1.0")
                        .description("""
                            Government Service Interoperability Prototype.

                            **DPO Service** provides read-only access to:
                            - Audit logs (field-level, with filters and pagination)
                            - Consent statistics

                            DPO is a **watchdog**: it cannot modify any business data.

                            Authentication: JWT Bearer token (from `/api/auth/login`).
                            Only users with the `DPO` role can access `/api/dpo/**`.
                            """)
                        .contact(new Contact()
                                .name("Government Interoperability Prototype")))
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SCHEME_NAME,
                        new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Paste the JWT returned by /api/auth/login")));
    }
}