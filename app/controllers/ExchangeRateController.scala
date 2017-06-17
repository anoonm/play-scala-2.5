package controllers

import com.google.inject.{Inject, Singleton}
import models.Error._
import play.api.Logger
import play.api.libs.json.{Json, JsValue}
import play.api.mvc.{Action, Controller, Result}
import unit.service.ExchangeRateClient
import constants.Constants.RATES_NOT_FOUND
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ExchangeRateController @Inject()
                        (client: ExchangeRateClient) extends Controller {

  def getRates(base: Option[String], target: Option[String], timestamp:Option[String]) = Action.async {
    client.getRates(base,target,timestamp).map {
      implicit response =>
        Logger.debug(s"Response from Fixer: ${response}")
        (response \ "error").asOpt[String] match{
          case Some(_) =>  BadRequest(response)
          case None =>  processResponse
        }
    }.recover{
      case e:Exception =>
        Logger.error(s"Exception occurred while trying to fetch exchange rate: ${e.toString}")
        InternalServerError(s"${e.toString}")
    }
  }

  def processResponse(implicit jsValueResp: JsValue):Result = {
      def isRatesEmpty(implicit jsValueResp: JsValue):Boolean = {
        val parseRate =  (jsValueResp \ "rates").asOpt[Map[String,Double]] getOrElse(Map())
        parseRate.isEmpty
      }

    if(isRatesEmpty)BadRequest(buildInvalidInputResponse) else Ok(jsValueResp)
  }

  val buildInvalidInputResponse : JsValue = Json.toJson(constructErrorResponse(RATES_NOT_FOUND))

}
