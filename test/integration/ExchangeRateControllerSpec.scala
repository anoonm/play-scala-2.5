package integration

import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.http.HttpVerbs
import play.api.test.FakeRequest
import play.api.test.Helpers._
import constants.Constants.RATES_NOT_FOUND

/**
 * Created by Anoopriya on 6/12/2017.
 */
class ExchangeRateControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar{
  "ExchangeRateController" should {

    "render the exchange rate response with EUR as default currency" in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates")).get

      status(ratesResp) mustBe OK
      contentType(ratesResp) mustBe Some("application/json")
      (contentAsJson(ratesResp) \ "base").as[String] mustBe "EUR"
    }

    "render the exchange rate response by passing in base currency USD" in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?base=USD")).get

      (contentAsJson(ratesResp) \ "base").as[String] mustBe "USD"
    }

    "fetch exchange rate response for the target currency" in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?target=CAD,USD")).get

      (contentAsJson(ratesResp) \ "rates").as[Map[String, Double]].size mustBe 2
    }

    "fetch exchange rate response for the timestamp before 4PM cutofftime" in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?timestamp=2017-06-15T11:34:46Z")).get

      (contentAsJson(ratesResp) \ "date").as[String] mustBe "2017-06-14"
    }

    "fetch exchange rate response for the timestamp after 4PM cutofftime" in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?timestamp=2017-06-15T17:34:46Z")).get

      (contentAsJson(ratesResp) \ "date").as[String] mustBe "2017-06-15"
    }

    "throw error for a invalid 'base' " in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?base=USDE")).get

      status(ratesResp) mustBe BAD_REQUEST
      (contentAsJson(ratesResp) \ "error").as[String] mustBe "Invalid base"
    }

    "throw error for a invalid 'target' " in {
      val ratesResp = route(app, FakeRequest(HttpVerbs.GET, "/rates?target=EEE")).get

      status(ratesResp) mustBe BAD_REQUEST
      (contentAsJson(ratesResp) \ "error").as[String] mustBe RATES_NOT_FOUND
    }
  }
}
