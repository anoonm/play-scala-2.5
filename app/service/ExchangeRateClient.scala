package unit.service

import com.google.inject.name.Named
import com.google.inject.{Inject, Singleton}
import helper.ControllerHelper
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.ws.WSClient
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by Anoopriya on 6/17/2017.
 */
@Singleton
class ExchangeRateClient @Inject()
(ws: WSClient,
 @Named("fixer.io") endpoint: String,
 helper: ControllerHelper) {

  def getRates(base: Option[String], target: Option[String], timestamp: Option[String]): Future[JsValue] = {

    val queryString: List[(String, String)] = helper.extractQueryParameter(base, target)

    val endPointUrl = endpoint + helper.getEndpointSuffix(timestamp)
    Logger.info(s"Calling Endpoint $endPointUrl to get exchange rates with query parameters${queryString}")

    ws.url(endPointUrl).withQueryString(queryString: _*).get().map(_.json).recoverWith {
      case e: Exception => throw e
    }

  }

}
