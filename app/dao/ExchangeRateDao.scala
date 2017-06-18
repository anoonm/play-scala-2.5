package dao

import com.google.inject.{Inject, Singleton}
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import constants.Constants._
import models.ExchangeRateResponse
import play.Logger
import salat._
import com.mongodb.casbah.Imports._

/**
 * Created by Anoopriya on 6/18/2017.
 */
trait ExchangeRateDao {
  def getRate():Option[ExchangeRateResponse]
  def insertRate(exchangeRateResp: ExchangeRateResponse):Boolean
  def save(newRateResponse: ExchangeRateResponse)
}

@Singleton
class ExchangeRateMongoDao @Inject()(dbConnector: DbConnector) extends ExchangeRateDao{
  def getRate = dbConnector.exchangeRateCollection.findOne.map(grater[ExchangeRateResponse].asObject(_))

  def insertRate(exchangeRateResp: ExchangeRateResponse):Boolean= {
    def buildMongoDbObject(): DBObject = {
      val builder = MongoDBObject.newBuilder
      builder += BASE -> exchangeRateResp.base
      builder += DATE -> exchangeRateResp.date
      builder += RATES -> exchangeRateResp.rates
      builder.result
    }

    dbConnector.exchangeRateCollection.insert(buildMongoDbObject).getN >=0
  }

  def save(newRateResponse: ExchangeRateResponse) = dbConnector.exchangeRateCollection.update(MongoDBObject(BASE -> "EUR"),grater[ExchangeRateResponse].asDBObject(newRateResponse))
}
