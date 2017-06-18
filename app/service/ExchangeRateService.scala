package service

import com.google.inject.Inject
import dao.ExchangeRateDao
import models.ExchangeRateResponse
import play.api.Logger

/**
 * Created by Anoopriya on 6/18/2017.
 */


trait ExchangeRateServiceLike{
  def findAndInsert(newRateResponse: ExchangeRateResponse):Option[ExchangeRateResponse]
  def save(newRateResponse: ExchangeRateResponse)
}

class ExchangeRateService @Inject()(exchangeRateDao: ExchangeRateDao)
                extends ExchangeRateServiceLike{

  def findAndInsert( newRateResponse: ExchangeRateResponse): Option[ExchangeRateResponse] = {
    exchangeRateDao.getRate() match{
      case Some(previousRateResponse) => Some(previousRateResponse)
      case None =>
        Logger.info("Insert the exchange rate response as no record exists in database")
        exchangeRateDao.insertRate(newRateResponse)
        None
    }
  }

  def save(newRateResponse: ExchangeRateResponse) = exchangeRateDao.save(newRateResponse)
}
