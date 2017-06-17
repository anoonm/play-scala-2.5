package models

import play.api.libs.json.Json

case class Error(error: String)

object Error{
  implicit val format = Json.format[Error]

  def constructErrorResponse(message:String): Error = Error(message)
}