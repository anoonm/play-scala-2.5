package actors

import akka.actor.{Props, Actor}
import com.google.inject.Inject
import models.ExchangeRateResponse
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import service.{ExchangeRateService, SubscriptionServiceLike}

/**
 * Created by Anoopriya on 6/17/2017.
 */

object NotifySubscriberActor{
  def props = Props[NotifySubscriberActor]
}

class NotifySubscriberActor @Inject()(wsClient:WSClient,
                                      subscriptionService: SubscriptionServiceLike,
                                      exchangeRateService: ExchangeRateService) extends Actor{

  def receive = {
    case rateResp: ExchangeRateResponse =>
      subscriptionService.getAllSubscribers().map{
        subscriber =>
        wsClient.url(subscriber.webhook_url).post(Json.toJson(rateResp))
      }

      exchangeRateService.save(rateResp)
  }
}
