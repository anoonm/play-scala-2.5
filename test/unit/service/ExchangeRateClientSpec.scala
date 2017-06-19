package unit.service

import helper.ControllerHelper
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.core.server.Server
import play.api.routing.sird._
import play.api.mvc.{Action, Results}
import play.api.routing.sird._
import play.api.test.WsTestClient
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.Await

/**
 * Created by Anoopriya on 6/17/2017.
 */
class ExchangeRateClientSpec extends PlaySpec with MockitoSugar{

  "ExchangeRateClient" should {

    "throw Exception if the call to WS endpoint fails" in {
     val helper = mock[ControllerHelper]

      Server.withRouter() {
        case GET(p"/") => Action {
          Results.InternalServerError
        }
      } { implicit port =>
        WsTestClient.withClient { client =>
          an[Exception] should be thrownBy Await.result(new ExchangeRateClient(client,"",helper).getRates(None,None,None), 10.seconds)
        }
      }
    }

  }

}
