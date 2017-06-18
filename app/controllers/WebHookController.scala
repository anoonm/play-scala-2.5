package controllers

import com.google.inject.{Inject, Singleton}
import helper.ControllerPayload
import models.SubscriptionRequest
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AnyContent, Request, Action, Controller}
import unit.service.SubscriptionServiceLike

import scala.concurrent.Future

@Singleton
class WebHookController @Inject()(subscriptionService: SubscriptionServiceLike)
  extends Controller
  with ControllerPayload{

  def createSubscription() = Action{implicit request =>
    val subscriptionModel = getRequestAsModel[SubscriptionRequest]
    Ok(Json.toJson(subscriptionService.createSubscription(subscriptionModel)))
  }

  def removeSubscription(user_id:String) = Action{implicit request =>
    Ok(Json.toJson(subscriptionService.removeSubscription(user_id)))
  }
}
