package modules

import actors.{NotifySubscriberActor, CompareRatesActor, SchedulerActor, Scheduler}
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

/**
 * Created by Anoopriya on 6/17/2017.
 */
class ActorProvider extends AbstractModule with AkkaGuiceSupport{
def configure = {
  bindActor[SchedulerActor]("scheduler-actor")
  bindActor[CompareRatesActor]("compare-rate-actor")
  bindActor[NotifySubscriberActor]("notify-subscriber-actor")
  bind(classOf[Scheduler]).asEagerSingleton()
}
}
