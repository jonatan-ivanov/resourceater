package simulations.heap

import io.gatling.core.Predef._
import io.gatling.http.HeaderNames.{Accept, ContentType}
import io.gatling.http.HeaderValues._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import java.time.Duration
import scala.annotation.unused

/**
 * @author Jonatan Ivanov
 */

@unused
class HeapSimulation extends Simulation {
    private val size = "512KB"
    private val ttl: Duration = Duration.ofMinutes(1)
    private val rate = 10 // 5MB/sec (300MB/min), 10 objects/sec (600 objects/min)
    private val duration = 300

    println(s"size: $size")
    println(s"ttl: $ttl")
    println(s"rate: $rate")
    println(s"duration: $duration")

    private val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8080")
        .contentTypeHeader(ApplicationJson)
        .acceptHeader(ApplicationJson)

    setUp(
        scenario("heapStress")
            .exec(
                http("createObject")
                    .post("/resources/objects")
                    .header(ContentType, ApplicationJson)
                    .header(Accept, ApplicationJson)
                    .body(StringBody(s"""{ "size": "$size", "ttl": "$ttl" }""")).asJson
            )
            .inject(constantUsersPerSec(rate) during duration)
            .protocols(httpProtocol)
    )
}
