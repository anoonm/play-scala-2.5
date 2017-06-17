package unit.service

import com.google.inject.{Inject, Singleton}
import dao.SubscriptionDao
import models.SubscriptionRequest

trait SubscriptionServiceLike{
  def createSubscription(subscriptionRequest: SubscriptionRequest)
}

@Singleton
class SubscriptionService @Inject()(subscriptionDao: SubscriptionDao) extends SubscriptionServiceLike {

  def createSubscription(subscriptionRequest: SubscriptionRequest) = {
    subscriptionDao.createSubscription(subscriptionRequest)
  }
}
