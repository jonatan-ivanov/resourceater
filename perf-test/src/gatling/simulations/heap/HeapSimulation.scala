package heap

import java.time.Duration

import io.gatling.core.Predef._
import io.gatling.http.HeaderValues._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

/**
 * @author Jonatan Ivanov
 */
class HeapSimulation extends Simulation  {
    val size = "512KB"
    val ttl: Duration = Duration.ofMinutes(1)
    val rate = 10 // 5MB/sec (300MB/min), 10 objects/sec (600 objects/min)
    val duration = 300

    println(s"size: $size")
    println(s"ttl: $ttl")
    println(s"rate: $rate")
    println(s"duration: $duration")

    val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8080")
        .contentTypeHeader(ApplicationJson)
        .acceptHeader(ApplicationJson)

    setUp(
        scenario("heapStress")
            .exec(
                http("createObject")
                    .post("/resources/objects")
                    .body(StringBody(s"""{ "size": "$size", "ttl": "$ttl" }""")).asJson
            )
            .inject(constantUsersPerSec(rate) during duration)
            .protocols(httpProtocol)
    )
}
