package controllers

import com.google.inject.{Inject, Singleton}
import play.api.Logger
import play.api.mvc.{Action, Controller}
import unit.service.ExchangeRateClient

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ExchangeRateController @Inject()
                        (client: ExchangeRateClient) extends Controller {

  def getRates(base: Option[String], target: Option[String], timestamp:Option[String]) = Action.async {
    client.getRates(base,target,timestamp).map {
      response =>
        Logger.debug(s"Response from Fixer: ${response}")
        (response \ "error").asOpt[String] match{
          case Some(_) =>  BadRequest(response)
          case None =>  Ok(response)
        }
    }.recover{
      case e:Exception =>
        Logger.error(s"Exception occurred while trying to fetch exchange rate: ${e.toString}")
        InternalServerError(s"${e.toString}")
    }
  }
}
