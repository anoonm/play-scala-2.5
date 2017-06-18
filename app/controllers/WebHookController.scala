package controllers

import com.google.inject.{Inject, Singleton}
import helper.ControllerPayload
import models.Subscription
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import service.SubscriptionServiceLike

@Singleton
class WebHookController @Inject()(subscriptionService: SubscriptionServiceLike)
  extends Controller
  with ControllerPayload{

  def createSubscription() = Action{implicit request =>
    val subscriptionModel = getRequestAsModel[Subscription]
    Ok(Json.toJson(subscriptionService.createSubscription(subscriptionModel)))
  }

  def removeSubscription(user_id:String) = Action{implicit request =>
    Ok(Json.toJson(subscriptionService.removeSubscription(user_id)))
  }
}
