package unit.service

import com.google.inject.{Inject, Singleton}
import dao.SubscriptionDao
import models.SubscriptionRequest

trait SubscriptionServiceLike{
  def createSubscription(subscriptionRequest: SubscriptionRequest):Boolean
  def removeSubscription(user_id:String):Boolean
}

@Singleton
class SubscriptionService @Inject()(subscriptionDao: SubscriptionDao) extends SubscriptionServiceLike {

  def createSubscription(subscriptionRequest: SubscriptionRequest):Boolean =
    subscriptionDao.createSubscription(subscriptionRequest)


  def removeSubscription(user_id:String):Boolean = subscriptionDao.removeSubscription(user_id)
}
