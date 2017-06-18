package dao

import com.google.inject.{Inject, Singleton}
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import constants.Constants._
import models.SubscriptionRequest

trait SubscriptionDao {
  def createSubscription(subscriptionRequest: SubscriptionRequest):Boolean
  def removeSubscription(user_id:String):Boolean
}

@Singleton
class SubscriptionMongoDao @Inject()(dbConnector: DbConnector) extends SubscriptionDao {
  def createSubscription(subscriptionRequest: SubscriptionRequest):Boolean = {
    def buildMongoDbObject(): DBObject = {
      val builder = MongoDBObject.newBuilder
      builder += USER_ID -> subscriptionRequest.user_id
      builder += WEBHOOK_URL -> subscriptionRequest.webhook_url
      builder.result
    }

    dbConnector.subscriptionCollection.insert(buildMongoDbObject).getN >= 0
  }

  def removeSubscription(user_id:String):Boolean ={
    val userId = MongoDBObject(USER_ID -> user_id)
    dbConnector.subscriptionCollection.remove(userId).getN >= 0
  }
}
