package resourceater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 * @author Jonatan Ivanov
 */
@EnableFeignClients
@EnableHypermediaSupport(type = HypermediaType.HAL)
@SpringBootApplication
@PropertySource("classpath:build.properties")
public class ResourceaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceaterApplication.class, args);
    }
}
