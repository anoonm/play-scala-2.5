package helper

import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AnyContent, Request}

/**
 * Created by Anoopriya on 6/17/2017.
 */
trait ControllerPayload {
  def getRequestAsModel[T](implicit tjs: Reads  [T], request: Request[AnyContent]): T ={
    getRequestBodyAsJson.validate[T] match{
      case JsSuccess(s,_) => s
      case JsError(e) =>
        Logger.error("Failure to parse payload")
        throw new JsResultException(e)
    }
  }

  def getRequestBodyAsJson(implicit request: Request[AnyContent]): JsValue =
    request.body.asJson.getOrElse(throw new IllegalArgumentException("No Json found in payload"))
}
