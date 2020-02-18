package in.mukta.shastra.swagger.provider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import in.mukta.shastra.swagger.registry.CentralizedSwaggerServiceRegistry;

/**
 * @author Nilesh
 * The class used to periodically connect to various swagger end points,
 * fetch swagger JSONs and store them in the registry which is the source for swagger-ui.
 */
@Component("centralizedDocumentUpdateProvider")
public class CentralizedSwaggerDocumentProvider {
	
	private static final Logger LOG = LoggerFactory.getLogger(CentralizedSwaggerDocumentProvider.class);
	
	@Autowired
    private CentralizedSwaggerServiceRegistry centralizedSwaggerServiceRegistry;
	
	private final RestTemplate restTemplate = new RestTemplate();

	/**
	 * This method is used to fetch swagger jsons from various sources and store the results in registry against
	 * the service name/uri. The method does a REST API call to the service url provided in SwaggerService to fetch
	 * swagger json for the service. This can be modified to fetch data from any sources.
	 */
	@Scheduled(fixedDelayString = "${documentation.swagger.config.refreshrate}")
	public void fetchCurrentDocumentation() {
		LOG.info("Fetching swagger implementation for {} services", centralizedSwaggerServiceRegistry.getServices().size());
		centralizedSwaggerServiceRegistry.getServices().parallelStream().forEach(service -> {
			CentralizedSwaggerRegistry.addSwaggerJSON(service.getName(), getSwaggerJSONFromUrl(service.getSwaggerServiceUrl()));
		});
		LOG.info("{}", "Swagger jsons updated successfully.");
	}
	
	/**
	 * Returns the swagger json stored in registry for given service name
	 * @param serviceName - name/uri of the service for which swagger json is to be fetched.
	 * @return swagger JSON string stored for given service
	 */
	public String getSwaggerJSONForService(String serviceName) {
		return CentralizedSwaggerRegistry.getSwaggerJSON(serviceName);
	}
	
	/**
	 * Does a RST API call on given uri to get Swagger JSON
	 * @param swaggerAPIUrl - REST URI to be called
	 * @return Swagger JSON string
	 */
	private String getSwaggerJSONFromUrl(String swaggerAPIUrl) {
		LOG.debug("Fetching swagger json from uri : {}", swaggerAPIUrl);
		try {
			URI uri = new URI(swaggerAPIUrl);
			return restTemplate.getForEntity(uri, String.class).getBody();
		}
		catch(URISyntaxException ue) {
			LOG.error("Error while creating uri with string {}", swaggerAPIUrl, ue);
			return "";
		}
		catch(Exception e) {
			LOG.error("Error while fetching json from uri {}", swaggerAPIUrl, e);
			return "";
		}
	}
	
	/**
	 * Private registry class to store JSON strings for each service.
	 * @author Nilesh
	 *
	 */
	private static class CentralizedSwaggerRegistry {
		private static final Map<String, String> swaggerJSONMap = new HashMap<>();
		
		public static void addSwaggerJSON(String serviceName, String jsonString) {
			swaggerJSONMap.put(serviceName, jsonString);
		}
		
		public static String getSwaggerJSON(String serviceName) {
			return swaggerJSONMap.get(serviceName);
		}
	}
}
