package resourceater;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Jonatan Ivanov
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ResourceaterApplicationTests {
    @Test void contextLoads() {}
}
