package in.mukta.shastra.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import in.mukta.shastra.swagger.registry.CentralizedSwaggerServiceRegistry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Nilesh
 */
@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({CentralizedSwaggerServiceRegistry.class, CentralizedSwaggerServiceRegistry.SwaggerService.class})
@EnableScheduling
public class CentralizedSwaggerDocumentationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralizedSwaggerDocumentationApplication.class, args);
	}

}
