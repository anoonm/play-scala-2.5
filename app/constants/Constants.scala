package constants

/**
 * Created by Anoopriya on 6/12/2017.
 */
object Constants {
  //Mongo related constants
  val MONGO_CONFIG = "mongo"
  val MONGO_URI = "mongoUri"
  val DB_NAME = "dbName"
  val SUBSCRIPTION_COLLECTION = "subscriptionCollection"
  val EXCHANGERATE_COLLECTION = "exchangeRateCollection"
  val USER_ID = "user_id"
  val WEBHOOK_URL="webhook_url"
  val BASE = "base"
  val DATE = "date"
  val RATES = "rates"

  //Error Response
  val RATES_NOT_FOUND = "Rates not found. You may check the target or timestamp"
}
