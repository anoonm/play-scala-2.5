package models

import org.joda.time.DateTime
import play.Logger
import play.api.libs.json.Json

case class Error(error: String)

object Error{
  implicit val format = Json.format[Error]

  def constructErrorResponse(message:String): Error = Error(message)
}

case class ExchangeRateResponse(
       base: String,
       date: String,
       rates: Map[String,Double]){

  override def equals(obj: scala.Any): Boolean = obj match{
    case compareObj:ExchangeRateResponse => {
      def isDateAfterOrEqual = {
        DateTime.parse(compareObj.date).equals(DateTime.parse(this.date)) ||
        DateTime.parse(compareObj.date).isAfter(DateTime.parse(this.date))
      }

      def areRatesEquals() = {
        compareObj.rates.keySet == this.rates.keySet &&
        this.rates.keys.forall(k => this.rates(k).equals(compareObj.rates(k)))
      }

      compareObj.base.equals(this.base) && isDateAfterOrEqual && areRatesEquals
    }
    case _ => false
  }

}

object ExchangeRateResponse{
  implicit val exchangeRateFormat = Json.format[ExchangeRateResponse]
}