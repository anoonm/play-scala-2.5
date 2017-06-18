package dao

import com.google.inject.{Inject, Singleton}
import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import constants.Constants._
import models.Subscription
import salat._
import com.mongodb.casbah.Imports._

trait SubscriptionDao {
  def createSubscription(subscriptionRequest: Subscription):Boolean
  def removeSubscription(user_id:String):Boolean
  def getAllSubscribers():List[Subscription]
}

@Singleton
class SubscriptionMongoDao @Inject()(dbConnector: DbConnector) extends SubscriptionDao {
  def createSubscription(subscriptionRequest: Subscription):Boolean = {
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

  def getAllSubscribers():List[Subscription] =
    dbConnector.subscriptionCollection.find().map(grater[Subscription].asObject(_)).toList

}
