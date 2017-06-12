package controllers

import com.google.inject.name.Named
import com.google.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json.{JsValue, Json, JsError, JsSuccess}
import play.api.libs.ws.WSClient
import play.api.mvc.Controller
import play.api.mvc.Action
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.collection.mutable.ListBuffer

@Singleton
class ExchangeRateController @Inject()(ws: WSClient,@Named("fixer.io") endpoint: String) extends Controller {

  def getRates(base: Option[String], target: Option[String]) = Action.async {

    val queryString: List[(String, String)] = extractQueryParameter(base, target)
    var wsReq = ws.url(endpoint).withQueryString(queryString :_* )

    Logger.info(s"Calling Endpoint ${wsReq.uri} to get exchange rates")

    wsReq.get().map {
      response =>
        Logger.debug(s"Response from Fixer: ${response.json}")

        Ok(response.json)
    }.recover{case e:Exception => InternalServerError(e.getMessage)}
  }

  def extractQueryParameter(base: Option[String], target:Option[String]): List[(String, String)] = {

    val queryListBuff = ListBuffer.empty[(String,String)]
    base match {
      case Some(baseCurrency) => queryListBuff += (("base", baseCurrency))
      case None => ""
    }

    target match {
      case Some(targetSymbol) => queryListBuff += (("symbols", targetSymbol))
      case None => ""
    }

    queryListBuff.toList
  }
}
