package helper

import com.google.inject.Singleton
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTimeZone, DateTime}
import play.api.Logger

import scala.collection.mutable.ListBuffer

/**
 * Created by Anoopriya on 6/17/2017.
 */
@Singleton
class ControllerHelper {
  def getEndpointSuffix(timestamp:Option[String])= timestamp.map(getDate).getOrElse("/latest")

  def extractQueryParameter(base: Option[String], target:Option[String]): List[(String, String)] = {

    val queryListBuff = ListBuffer.empty[(String,String)]
    base match {
      case Some(baseCurrency) => queryListBuff += (("base", baseCurrency))
      case None => ""
    }

    target match {
      case Some(targetSymbol) => queryListBuff += (("symbols", targetSymbol))
      case None => ""
    }
    queryListBuff.toList
  }


  def getDate(dateTimeStr: String) : String =  {
    def getCutOffTime(dateTimeCET: DateTime) = {
      dateTimeCET.withHourOfDay(16).withMinuteOfHour(0).withSecondOfMinute(0)

    }
    //Central European Time(CET) is 1 hour ahead of Coordinated Universal Time(UTC)
    val dateTimeCET =  DateTime.parse(dateTimeStr).toDateTime(DateTimeZone.forID("CET"))
    Logger.debug(s"DateTime in CET ${dateTimeCET}")

    val cutOffTime = getCutOffTime(dateTimeCET)
    Logger.debug(s"CutOffTime in CET ${cutOffTime}")

    val newDateTime = if(dateTimeCET.isBefore(cutOffTime))dateTimeCET.minusDays(1) else dateTimeCET
    Logger.debug(s"Requesting Fixer.io for datetime ${newDateTime}")

    val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd")
    newDateTime.toString(dateFormat)
  }

}
