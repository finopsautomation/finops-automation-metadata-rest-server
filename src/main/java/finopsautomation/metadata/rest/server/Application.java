package finopsautomation.metadata.rest.server;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot REST Server for Metadata APIS
 */
@SpringBootApplication(scanBasePackages = "finopsautomation.metadata")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public GroupedOpenApi metadataGroup() {
		return GroupedOpenApi.builder().group("metadata")
//				.addOperationCustomizer((operation, handlerMethod) -> {
//					operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
//					return operation;
//				})
				.addOpenApiCustomizer(openApi -> openApi.info(new Info().title("FinOps Metadata API")))
				.packagesToScan("finopsautomation.metadata.rest.server.controllers")
				.build();
	}
}
