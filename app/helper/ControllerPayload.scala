package helper

import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AnyContent, Request}

/**
 * Created by Anoopriya on 6/17/2017.
 */
trait ControllerPayload {
  def getRequestAsModel[T](implicit tjs: Reads  [T], request: Request[AnyContent]): T ={
    getModelFromJson(getRequestBodyAsJson)
  }

  def getRequestBodyAsJson(implicit request: Request[AnyContent]): JsValue =
    request.body.asJson.getOrElse(throw new IllegalArgumentException("No Json found in payload"))

  def getModelFromJson[T](jsValue: JsValue)(implicit tjs: Reads[T]):T = {
    jsValue.validate[T] match{
      case JsSuccess(s,_) => s
      case JsError(e) =>
        Logger.error("Failure to validate JSON to required model")
        throw new JsResultException(e)
    }
  }
}
