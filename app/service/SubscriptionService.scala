package service

import com.google.inject.{Inject, Singleton}
import dao.SubscriptionDao
import models.Subscription

trait SubscriptionServiceLike{
  def createSubscription(subscriptionRequest: Subscription):Boolean
  def removeSubscription(user_id:String):Boolean
  def getAllSubscribers():List[Subscription]
}

@Singleton
class SubscriptionService @Inject()(subscriptionDao: SubscriptionDao) extends SubscriptionServiceLike {

  def createSubscription(subscriptionRequest: Subscription):Boolean =
    subscriptionDao.createSubscription(subscriptionRequest)


  def removeSubscription(user_id:String):Boolean = subscriptionDao.removeSubscription(user_id)

  def getAllSubscribers():List[Subscription] = subscriptionDao.getAllSubscribers
}
