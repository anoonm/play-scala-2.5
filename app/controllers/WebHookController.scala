package controllers

import com.google.inject.{Inject, Singleton}
import models.SubscriptionRequest
import play.api.Logger
import play.api.libs.json.{Reads, Writes, JsSuccess, JsError}
import play.api.mvc.{AnyContent, Request, Action, Controller}
import unit.service.SubscriptionServiceLike

import scala.concurrent.Future

@Singleton
class WebHookController @Inject()(subscriptionService: SubscriptionServiceLike) extends Controller {

  def createSubscription() = Action{implicit request =>
    getRequestAsModel[SubscriptionRequest] map{
      subscriptionService.createSubscription(_)
    }

   Ok("Subscription created")
  }

  def getRequestAsModel[T](implicit tjs: Reads  [T], request: Request[AnyContent])  : Option[T] ={
    request.body.asJson map{
      json => json.validate[T] match{
        case JsSuccess(s,_) => s
        case JsError(e) => Logger.error("Failure to parse payload to create subscription")
          throw new Exception("Failure to parse payload to create subscription")
      }
    }
  }
}
