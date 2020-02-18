package in.mukta.shastra.swagger.registry;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Nilesh
 */
@Primary
@Configuration("centralizedSwaggerServiceRegistry")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="documentation.swagger")
public class CentralizedSwaggerServiceRegistry {

	List<SwaggerService> swaggerServices;

    public List<SwaggerService> getServices() {
        return swaggerServices;
    }

    public void setServices(List<SwaggerService> swaggerServiceList) {
        this.swaggerServices = swaggerServiceList;
    }
    
    @EnableConfigurationProperties
    @ConfigurationProperties(prefix="documentation.swagger.services")
    public static class SwaggerService {
        private String name;
        private String serviceUrl;
        private String version;
        private String swaggerServiceUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

		public String getSwaggerServiceUrl() {
			return swaggerServiceUrl;
		}

		public void setSwaggerServiceUrl(String swaggerServiceUrl) {
			this.swaggerServiceUrl = swaggerServiceUrl;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("SwaggerService [name=").append(name).append(", serviceUrl=").append(serviceUrl)
					.append(", version=").append(version).append(", swaggerServiceUrl=").append(swaggerServiceUrl)
					.append("]");
			return builder.toString();
		}
    }
}