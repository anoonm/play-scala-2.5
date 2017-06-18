package actors

import akka.actor.{ActorRef, Actor}
import com.google.inject.Inject
import com.google.inject.name.Named
import models.ExchangeRateResponse
import play.Logger
import service.ExchangeRateService

/**
 * Created by Anoopriya on 6/17/2017.
 */
class CompareRatesActor @Inject() (exchangeRateService: ExchangeRateService,
                                   @Named("notify-subscriber-actor") notifySubscriber:ActorRef) extends Actor {

  def receive = {
    case newRateResp: ExchangeRateResponse =>
      Logger.debug("Comparing current rates with previous rates from DB")
      exchangeRateService.findAndInsert(newRateResp).map{
        dbRateResp =>
          if(!dbRateResp.equals(newRateResp)){
            Logger.info("There is change in exchange rates.. Notifying users")
            notifySubscriber ! newRateResp }
          else Logger.info("No Change in rates to notify subscribers")
      }
  }

}
