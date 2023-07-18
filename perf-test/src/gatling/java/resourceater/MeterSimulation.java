package resourceater;

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
public class MeterSimulation extends Simulation {
    final String size = "1000";
    final String type = "TIMER";
    final int meterUsersPerSec = 1; // 1_000 timers/sec (60_000 timers/min), 3_000 time series/sec (180_000 time series/min)
    final double scrapeUsersPerSec = 0.1; // ~1 scrape per every 10s (6 scrapes/min)
    final Duration meterStressDuration = Duration.ofSeconds(60);
    final Duration scrapeStressDuration = Duration.ofSeconds(60);

    final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
        .contentTypeHeader(ApplicationJson())
        .acceptHeader(ApplicationJson());

    final ChainBuilder createMeters = exec(http("createMeters")
        .post("/resources/meters")
        .header(ContentType(), ApplicationJson())
        .header(Accept(), ApplicationJson())
        .body(StringBody("{ \"size\": \"%s\", \"type\": \"%s\" }".formatted(size, type))).asJson()
    );

    final ChainBuilder scrapeMetrics = exec(http("scrapeMetrics")
        .get("/actuator/prometheus")
        .header(Accept(), "application/openmetrics-text; version=1.0.0; charset=utf-8"));

    {
        System.out.printf("size: %s%n", size);
        System.out.printf("type: %s%n", type);
        System.out.printf("meterUsersPerSec: %s%n", meterUsersPerSec);
        System.out.printf("scrapeUsersPerSec: %s%n", scrapeUsersPerSec);
        System.out.printf("meterStressDuration: %s%n", meterStressDuration);
        System.out.printf("scrapeStressDuration: %s%n", scrapeStressDuration);

        setUp(
            scenario("createMetersStress")
                .exec(createMeters)
                .injectOpen(constantUsersPerSec(meterUsersPerSec).during(meterStressDuration)),
            scenario("scrapeMetricsStress")
                .exec(scrapeMetrics)
                .injectOpen(constantUsersPerSec(scrapeUsersPerSec).during(scrapeStressDuration))
        ).protocols(httpProtocol);
    }
}
