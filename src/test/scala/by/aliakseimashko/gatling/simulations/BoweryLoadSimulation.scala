package by.aliakseimashko.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.config.HttpProtocolBuilder
import io.gatling.http.config.HttpProtocolBuilder.toHttpProtocol
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import scala.concurrent.duration._
import scala.language.postfixOps

class BoweryLoadSimulation extends Simulation{

  val sentHeaders = Map("Content-Type" -> "application/json", "Accept" -> "*/*")

  val formsCount = 6
  val formValue1 = "job-info"
  val formValue2 = "subject"
  val id = "6093030ee5ad6600281be0f8"

  val endpoint = "/graphql"
  val filePath1 = "src\\test\\resources\\body1.json"
  val filePath2 = "src\\test\\resources\\body1.json"
  val filePath3 = "src\\test\\resources\\body3.json"

  val theHttpProtocolBuilder: HttpProtocolBuilder = http
    .baseURL("https://api.qa.workbook.bowery.link")
    .headers(sentHeaders)

  val theScenarioBuilder: ScenarioBuilder = scenario("Load Scenario")
    .exec(
      http("POST to GraphQL endpoint 1")
        .post(endpoint)
        .body(RawFileBody(filePath1)).asJSON
        .check(status.is(200))
        .check(jsonPath("$..forms[*]").count.is(formsCount))
        .check(jsonPath("$..forms[0].key").is(formValue1))
        .check(jsonPath("$..forms[1].key").is(formValue2))
    )
    .pace(1 seconds)
    .exec(
      http("POST to GraphQL endpoint 2")
        .post(endpoint)
        .body(RawFileBody(filePath2)).asJSON
        .check(status.is(200))
        .check(jsonPath("$..notes[*]").count.is(0))
    )
    .pace(1 seconds)
    .exec(
      http("POST to GraphQL endpoint 3")
        .post(endpoint)
        .body(RawFileBody(filePath3)).asJSON
        .check(status.is(200))
        .check(jsonPath("$..id").is(id))
    )

  setUp(
    theScenarioBuilder.inject(rampUsers(20).over(5 seconds))
  ).protocols(theHttpProtocolBuilder)

}
