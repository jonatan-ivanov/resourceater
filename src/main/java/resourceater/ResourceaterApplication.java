package resourceater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jonatan Ivanov
 */
@EnableFeignClients
@SpringBootApplication
public class ResourceaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceaterApplication.class, args);
    }
}
