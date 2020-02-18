package in.mukta.shastra.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.mukta.shastra.swagger.provider.CentralizedSwaggerDocumentProvider;

/**
 * @author Nilesh
 * Service class called from Swagger-ui to get swagger json. 
 */
@RestController
public class CentralizedDocumentationService {
	
	@Autowired
    private CentralizedSwaggerDocumentProvider centralizedDocumentUpdateProvider;

	/**
	 * The 'serviceUrl' property of SwaggerService class is directly mapped to 'location'
	 * property of SwaggerResource which is called from Swagger-ui for getting the JSON.
	 * This 'serviceUrl' is explicitly set to following url in properties file, so every call from ui
	 * will land on this method which returns the JSON for service name from the registry.
	 * @param serviceName(path parameter) - Name of the service for which swagger JSON is to be returned.
	 * @return swagger JSON string stored in registry for given service name or blank string.
	 */
	@GetMapping("/api-doc/{servicename}")
	public String getServiceDefinition(@PathVariable("servicename") String serviceName) {
		return centralizedDocumentUpdateProvider.getSwaggerJSONForService(serviceName);
	}
}
