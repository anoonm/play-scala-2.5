package actors

import actors.SchedulerActor.GET_RATE
import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.Inject
import com.google.inject.name.Named
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.duration._

/**
 * Created by Anoopriya on 6/17/2017.
 */
class Scheduler @Inject() (system: ActorSystem,
                            @Named("scheduler-actor")scheduleActor: ActorRef) {
  //For Testing purpose, the interval is kept as 2 seconds
  system.scheduler.schedule( 0.microseconds, 2.seconds, scheduleActor, GET_RATE)
}
