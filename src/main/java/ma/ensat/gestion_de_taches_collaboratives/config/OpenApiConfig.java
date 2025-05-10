//package ma.ensat.gestion_de_taches_collaboratives.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenApiConfig {
//
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//                .components(
//                        new Components()
//                                .addSecuritySchemes("bearerAuth",
//                                        new SecurityScheme()
//                                                .name("bearerAuth")
//                                                .type(SecurityScheme.Type.HTTP)
//                                                .scheme("bearer")
//                                                .bearerFormat("JWT")
//                                )
//                )
//                .info(new Info()
//                        .title("API de Gestion de Tâches Collaboratives")
//                        .description("API REST sécurisée pour gérer des tâches collaboratives")
//                        .version("1.0")
//                        .contact(new Contact()
//                                .name("Votre Nom")
//                                .email("votre.email@example.com"))
//                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
//                );
//    }
//}

package ma.ensat.gestion_de_taches_collaboratives.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestion de Tâches Collaboratives")
                        .version("1.0")
                        .description("Documentation de l'API pour la gestion de tâches collaboratives")
                        .contact(new Contact()
                                .name("Votre Nom")
                                .email("votre.email@exemple.com")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}