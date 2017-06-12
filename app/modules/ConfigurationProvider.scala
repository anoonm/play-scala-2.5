package modules

import com.google.inject.AbstractModule
import play.api.{Environment, Configuration}
import com.google.inject.name.Names
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
  }
}
