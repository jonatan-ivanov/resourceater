package resourceater.simulations.heap;

import java.time.Duration;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.http.HeaderNames.Accept;
import static io.gatling.http.HeaderNames.ContentType;
import static io.gatling.http.HeaderValues.ApplicationJson;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

@SuppressWarnings("unused")
public class HeapSimulation extends Simulation {
    final String size = "512KB";
    final Duration ttl = Duration.ofMinutes(1);
    final int usersPerSec = 10; // 5MiB/sec (300MiB/min), 10 objects/sec (600 objects/min)
    final Duration duration = Duration.ofSeconds(300);

    final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
        .contentTypeHeader(ApplicationJson())
        .acceptHeader(ApplicationJson());

    final ChainBuilder createObject = exec(http("createObject")
        .post("/resources/objects")
        .header(ContentType(), ApplicationJson())
        .header(Accept(), ApplicationJson())
        .body(StringBody("{ \"size\": \"%s\", \"ttl\": \"%s\" }".formatted(size, ttl))).asJson()
    );

    {
        System.out.printf("size: %s%n", size);
        System.out.printf("ttl: %s%n", ttl);
        System.out.printf("usersPerSec: %s%n", usersPerSec);
        System.out.printf("duration: %s%n", duration);

        setUp(scenario("heapStress")
            .exec(createObject)
            .injectOpen(constantUsersPerSec(usersPerSec).during(duration))
        ).protocols(httpProtocol);
    }
}
