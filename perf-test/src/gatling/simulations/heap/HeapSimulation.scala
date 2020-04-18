package heap

import io.gatling.core.Predef._
import io.gatling.http.HeaderValues._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class HeapSimulation extends Simulation  {
    val httpProtocol: HttpProtocolBuilder = http
        .baseUrl("http://localhost:8080")
        .contentTypeHeader(ApplicationJson)
        .acceptHeader(ApplicationJson)

    setUp(
        scenario("heapStress")
            .exec(
                http("createObject")
                    .post("/resources/objects")
                    .body(StringBody("""{ "size": "10MB" }""")).asJson
            )
            .inject(constantUsersPerSec(1) during(60))
            .protocols(httpProtocol)
    )
}
