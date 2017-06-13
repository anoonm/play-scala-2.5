import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
 * Created by Anoopriya on 6/12/2017.
 */
class ExchangeRateControllerSpec extends PlaySpec with OneAppPerTest{
  "ExchangeRateController" should {

    "render the exchange rate response with EUR as default currency" in {
      val ratesResp = route(app, FakeRequest(GET, "/rates")).get

      status(ratesResp) mustBe OK
      contentType(ratesResp) mustBe Some("application/json")
      (contentAsJson(ratesResp) \ "base").as[String] mustBe "EUR"
    }

    "render the exchange rate response by passing in base currency USD" in {
      val ratesResp = route(app, FakeRequest(GET, "/rates?base=USD")).get

      (contentAsJson(ratesResp) \ "base").as[String] mustBe "USD"
    }

    "fetch exchange rate response for the target currency" in {
      val ratesResp = route(app, FakeRequest(GET, "/rates?target=CAD,USD")).get

      (contentAsJson(ratesResp) \ "rates").as[Map[String, Double]].size mustBe 2
    }

    //TODO handle exception scenario

  }
}
