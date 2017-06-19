package models

import play.api.libs.json.Json

/**
 * Created by Anoopriya on 6/13/2017.
 */
case class Subscription(user_id: String, webhook_url:String)

object Subscription{
  implicit val format = Json.format[Subscription]
}
