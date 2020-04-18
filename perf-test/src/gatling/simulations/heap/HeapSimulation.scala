package heap

import io.gatling.core.Predef._
import io.gatling.http.HeaderValues._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class HeapSimulation extends Simulation  {
    val size = "100KB"
    val rate = 100
    val duration = 60

    println(s"size: $size")
    println(s"rate: $size")
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
                    .body(StringBody(s"""{ "size": "$size", "disposable": true }""")).asJson
            )
            .inject(constantUsersPerSec(rate) during(duration))
            .protocols(httpProtocol)
    )
}
