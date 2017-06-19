package actors


import akka.actor.{ActorRef, Actor}
import com.google.inject.Inject
import com.google.inject.name.Named
import helper.ControllerPayload
import models.ExchangeRateResponse
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WSClient

object SchedulerActor{
  case object GET_RATE
}
/**
 * Created by Anoopriya on 6/17/2017.
 */
class SchedulerActor @Inject()(wsClient:WSClient,
                               @Named("fixer.io") endpoint: String,
                                @Named("compare-rate-actor") compareRatesActor:ActorRef)
  extends Actor
  with ControllerPayload{
  import actors.SchedulerActor.GET_RATE

  def receive = {
    case GET_RATE =>
      Logger.info("Getting current exchange rate info")
    wsClient.url(endpoint+"latest").get().map{
        resp => compareRatesActor ! getModelFromJson[ExchangeRateResponse](resp.json)
    }recoverWith{
      case e:Exception => throw e
    }

  }
}
