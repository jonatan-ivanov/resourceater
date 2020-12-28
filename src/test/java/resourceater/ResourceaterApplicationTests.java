package resourceater;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Jonatan Ivanov
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ResourceaterApplicationTests {
    @Test void contextLoads() {}
}
