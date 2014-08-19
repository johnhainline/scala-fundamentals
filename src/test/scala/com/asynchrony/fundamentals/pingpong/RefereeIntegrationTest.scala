package com.asynchrony.fundamentals.pingpong

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, DefaultTimeout, TestKit}
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, DiagrammedAssertions, Matchers}
import scala.concurrent.duration._
import DiagrammedAssertions._


class RefereeIntegrationTest extends TestKit(ActorSystem("RefereeIntegrationTest"))
  with DefaultTimeout with ImplicitSender with FunSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  describe("A Referee") {
    val playerNames = List("Joe", "John", "Steve", "Jim", "Aaron")

    it ("should send the appropriate status response") {
      val referee = system.actorOf(Props[Referee](new Referee))

      within(500 millis) {
        referee ! StatusRequest

        expectMsg(RefereeStatus(List[ActivePlayer](), Set[ActiveGame]()))
      }
    }

    it ("should add players to games correctly") {
      val referee = system.actorOf(Props[Referee](new Referee))

      playerNames.foreach{ name =>
        system.actorOf(Props[Player](new Player(referee, name)))
      }

      within(500 millis) {
        referee ! StatusRequest

        receiveWhile(500 millis) {
          case refereeStatus: RefereeStatus =>
            DiagrammedAssertions.assert(refereeStatus.availablePlayers.length == 1)
            DiagrammedAssertions.assert(refereeStatus.activeGames.size == 2)
        }
      }
    }

    // This test doesn't queue Depart and StatusRequest correctly (potentially).
    it ("should allow players to leave") {
      val referee = system.actorOf(Props[Referee](new Referee))
      system.actorOf(Props[Player](new Player(referee, "PlayerOne")))
      system.actorOf(Props[Player](new Player(referee, "PlayerTwo")))

      within(500 millis) {
        referee ! Depart("PlayerOne")
        expectNoMsg

        referee ! StatusRequest
        receiveWhile(500 millis) {
          case refereeStatus: RefereeStatus =>
            DiagrammedAssertions.assert(refereeStatus.availablePlayers.length == 1)
            DiagrammedAssertions.assert(refereeStatus.activeGames.size == 0)
        }
      }
    }
  }
}
