package in.mukta.shastra.swagger.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import in.mukta.shastra.swagger.registry.CentralizedSwaggerServiceRegistry;
import in.mukta.shastra.swagger.registry.CentralizedSwaggerServiceRegistry.SwaggerService;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @author Nilesh
 * Swagger-ui works on Swagger resources which need to be customized in this case,
 * by implementing get() method of SwaggerResourceProvider.
 * @Primary annotation will force
 * this class to be picked up over others for implementation.
 */
@Primary
@Component
public class CentralizedSwaggerResourceProvider implements SwaggerResourcesProvider {

	@Autowired
    private CentralizedSwaggerServiceRegistry centralizedSwaggerServiceRegistry;

	/**
	 *  This method gets all the registered swagger resources from centralized swagger registry
	 *  and adds them to SwaggerResources.
	 */
	@Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        centralizedSwaggerServiceRegistry.getServices().forEach(service -> {
        	resources.add(getSwaggerResource(service));
        });
        return resources;
    }

    private SwaggerResource getSwaggerResource(SwaggerService service) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(service.getName());
        swaggerResource.setLocation(service.getServiceUrl());
        swaggerResource.setSwaggerVersion(service.getVersion());
        return swaggerResource;	
    }
}
