package resourceater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Jonatan Ivanov
 */
@EnableFeignClients
@EnableSwagger2WebMvc
@EnableHypermediaSupport(type = HypermediaType.HAL)
@SpringBootApplication
public class ResourceaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceaterApplication.class, args);
    }
}
