
import play.api.Play
import play.api.Play.current
import salat.Context

/**
 * Created by Anoopriya on 6/18/2017.
 */
package object dao {
    implicit val context = {
      val context = new Context {
        val name = "global"
      }
      context.registerGlobalKeyOverride(remapThis = "id", toThisInstead = "_id")
      context.registerClassLoader(Play.classloader)
      context
    }
}


