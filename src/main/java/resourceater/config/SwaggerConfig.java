package resourceater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resourceater.ResourceaterApplication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * @author Jonatan Ivanov
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final Set<String> SUPPORTED_MEDIA_TYPES = Set.of(APPLICATION_JSON_VALUE);

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("Resourceater API")
            .consumes(SUPPORTED_MEDIA_TYPES)
            .produces(SUPPORTED_MEDIA_TYPES)
            .apiInfo(resourcesApiInfo())
            .select().apis(basePackage(ResourceaterApplication.class.getPackageName()))
            .build();
    }

    private ApiInfo resourcesApiInfo() {
        return new ApiInfoBuilder()
            .title("Resourceater REST API")
            .description("Service API to consume your resources on demand")
            .version("1.0")
            .contact(new Contact(
                "Jonatan Ivanov",
                "https://github.com/jonatan-ivanov",
                "jonatan.ivanov@gmail.com"))
            .build();
    }
}
