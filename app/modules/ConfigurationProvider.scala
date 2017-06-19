package modules

import com.google.inject.AbstractModule
import dao.{ExchangeRateMongoDao, ExchangeRateDao, SubscriptionMongoDao, SubscriptionDao}
import play.api.{Environment, Configuration}
import com.google.inject.name.Names
import service.{ExchangeRateService, ExchangeRateServiceLike}
import service.{SubscriptionServiceLike, SubscriptionService}

/**
 * Created by Anoopriya on 6/12/2017.
 */
class ConfigurationProvider (
                              environment: Environment,
                              configuration: Configuration) extends AbstractModule {
  def configure() = {
    bind(classOf[String])
      .annotatedWith(Names.named("fixer.io"))
      .toInstance( configuration.getString("fixer.io").get)

    bind(classOf[SubscriptionServiceLike]).to(classOf[SubscriptionService])
    bind(classOf[SubscriptionDao]).to(classOf[SubscriptionMongoDao])

    bind(classOf[ExchangeRateServiceLike]).to(classOf[ExchangeRateService])
    bind(classOf[ExchangeRateDao]).to(classOf[ExchangeRateMongoDao])
  }
}
