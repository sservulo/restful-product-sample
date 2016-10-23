package ws.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import ws.endpoint.RestProductEndpoint;


/**
 * Configuration file for integration of Jersy endpoint registration with Spring IoC.
 * @author Samuel
 *
 */
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RestProductEndpoint.class);
    }
}