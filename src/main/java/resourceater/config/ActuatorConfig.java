package resourceater.config;

import resourceater.actuator.info.RuntimeInfoContributor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ActuatorConfig {
    @Bean
    public RuntimeInfoContributor runtimeInfoContributor(Environment environment) {
        return new RuntimeInfoContributor(environment);
    }
}
