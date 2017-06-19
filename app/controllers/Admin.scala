package controllers

import com.google.inject.{Inject, Singleton}
import com.iheart.playSwagger.SwaggerSpecGenerator
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import play.routing.Router


/**
 * Created by Anoopriya on 6/12/2017.
 */
@Singleton
class Admin @Inject()() extends Controller{
  implicit val cl = getClass.getClassLoader

  // The root package of your domain classes, play-swagger will automatically generate definitions when it encounters class references in this package.
  val domainPackage = "models"
  private lazy val generator = SwaggerSpecGenerator(domainPackage)

  def specs = Action { _ =>
      Ok(generator.generate().get)
    }

}
