package dao

import com.google.inject.{Inject, Singleton}
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import models.SubscriptionRequest

trait SubscriptionDao {
  def createSubscription(subscriptionRequest: SubscriptionRequest)
}

@Singleton
class SubscriptionMongoDao @Inject()(dbConnector: DbConnector) extends SubscriptionDao {
  def createSubscription(subscriptionRequest: SubscriptionRequest) = {
    def buildMongoDbObject(): DBObject = {
      val builder = MongoDBObject.newBuilder
      builder += "user_id" -> subscriptionRequest.user_id
      builder += "webhook_url" -> subscriptionRequest.webhook_url
      builder.result
    }
  dbConnector.subscriptionCollection.insert(buildMongoDbObject)
  }
}
