package unit.actors

import actors.{NotifySubscriberActor, CompareRatesActor}
import akka.actor.{Props, ActorSystem}
import akka.testkit._
import models.ExchangeRateResponse
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpecLike, Matchers, BeforeAndAfterAll}
import service.ExchangeRateService
import org.mockito.Matchers._
import org.mockito.Mockito._
/**
 * Created by Anoopriya on 6/18/2017.
 */
class CompareRatesActorSpec  extends TestKit(ActorSystem("MySpec")) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll with MockitoSugar{

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "Compare Rates actor" must {

    "notify subscribers when there is a change in exchange rates" in {
      val prevDateResponse = ExchangeRateResponse("EUR","2017-03-03",Map("USD" -> 1.4))
      val nextDateResponse = ExchangeRateResponse("EUR","2017-03-04",Map("USD" -> 1.5))

      val rateService = mock[ExchangeRateService]
      when(rateService.findAndInsert(nextDateResponse)).thenReturn(Some(prevDateResponse))
      val testProbe = TestProbe()
      val compareRateActor = system.actorOf(Props(classOf[CompareRatesActor],rateService,testProbe.ref ))
      compareRateActor ! nextDateResponse
      testProbe.expectMsg(nextDateResponse)
    }

    "do not notify subscribers when there is no change in exchange rates" in {
      val prevDateResponse = ExchangeRateResponse("EUR","2017-03-03",Map("USD" -> 1.4))
      val nextDateResponse = ExchangeRateResponse("EUR","2017-03-04",Map("USD" -> 1.4))

      val rateService = mock[ExchangeRateService]
      when(rateService.findAndInsert(nextDateResponse)).thenReturn(Some(prevDateResponse))
      val testProbe = TestProbe()
      val compareRateActor = system.actorOf(Props(classOf[CompareRatesActor],rateService,testProbe.ref ))
      compareRateActor ! nextDateResponse
      testProbe.expectNoMsg()
    }


    "fails when it receives message other than ExchangeRateResponse" in {
      val prevDateResponse = ExchangeRateResponse("EUR","2017-03-03",Map("USD" -> 1.4))
      val rateService = mock[ExchangeRateService]
      val testProbe = TestProbe()
      val compareRateActor = system.actorOf(Props(classOf[CompareRatesActor],rateService,testProbe.ref ))
      compareRateActor ! "Hello"
      testProbe.expectNoMsg()
    }
  }

}
