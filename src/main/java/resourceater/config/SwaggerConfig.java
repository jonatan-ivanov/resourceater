package resourceater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resourceater.ResourceaterApplication;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * @author Jonatan Ivanov
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(basePackage(ResourceaterApplication.class.getPackageName()))
            .build();
    }
}
