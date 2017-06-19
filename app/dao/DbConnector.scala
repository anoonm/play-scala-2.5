package dao

import com.google.inject.{Singleton, Inject}
import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoCollection}
import constants.Constants._
import play.api.inject.ApplicationLifecycle
import play.api.{Configuration, Logger}

import scala.concurrent.Future

/**
 * Created by Anoopriya on 6/12/2017.
 */
@Singleton
class DbConnector @Inject() (configuration: Configuration, lifecycle: ApplicationLifecycle){

  lifecycle.addStopHook {
    () =>
    {
      Logger.info("Closing mongo connection as play shutsdown")
      Future.successful(mongoClient.close())
    }
  }

  val mongoClient = getMongoClient(configuration.getConfig(MONGO_CONFIG).get)
  val collection = initMongo
  val subscriptionCollection: MongoCollection = collection._1
  val exchangeRateCollection: MongoCollection = collection ._2

  private def initMongo = {
    Logger.info("Initializing Mongo")
    val mongoConfig = configuration.getConfig(MONGO_CONFIG).get
    val mongoDB = mongoClient.apply(mongoConfig.getString(DB_NAME).get)
    (mongoDB.apply(mongoConfig.getString(SUBSCRIPTION_COLLECTION).get), mongoDB.apply(mongoConfig.getString(EXCHANGERATE_COLLECTION).get))
  }

  private def getMongoClient(mongoConfig: Configuration): MongoClient = {
    val mongoClientURI = MongoClientURI(mongoConfig.getString(MONGO_URI).get)
    MongoClient(mongoClientURI)
  }
}
