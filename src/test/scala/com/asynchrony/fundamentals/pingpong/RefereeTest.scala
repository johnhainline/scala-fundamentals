package com.asynchrony.fundamentals.pingpong

import akka.actor.Status.Success
import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestActorRef, ImplicitSender, DefaultTimeout, TestKit}
import org.scalatest.{DiagrammedAssertions, BeforeAndAfterAll, Matchers, FunSpecLike}
import akka.pattern._
import scala.concurrent.duration._
import DiagrammedAssertions._

class RefereeTest extends TestKit(ActorSystem("RefereeIntegrationTest"))
with DefaultTimeout with ImplicitSender with FunSpecLike with Matchers with BeforeAndAfterAll {

  describe("A Referee") {

    it("should send the appropriate status response") {
      val referee = TestActorRef[Referee]

      referee ! StatusRequest

      expectMsg(RefereeStatus(List[ActivePlayer](), Set[ActiveGame]()))
    }

    it("should add players to games correctly") {
      val referee = TestActorRef[Referee]
      val playerNames = List("Joe", "John", "Steve", "Jim", "Aaron")

      playerNames.foreach { name =>
        referee ! Join(name)
      }

      val refereeStatus = referee.ask(StatusRequest).value.get.get.asInstanceOf[RefereeStatus]
      DiagrammedAssertions.assert(refereeStatus.availablePlayers.length == 1)
      DiagrammedAssertions.assert(refereeStatus.activeGames.size == 2)
    }

    it("should allow players to leave") {
      val referee = TestActorRef[Referee]
      referee ! Join("PlayerOne")
      referee ! Join("PlayerTwo")

      referee ! Depart("PlayerOne")

      val refereeStatus = referee.ask(StatusRequest).value.get.get.asInstanceOf[RefereeStatus]
      DiagrammedAssertions.assert(refereeStatus.availablePlayers.length == 1)
      DiagrammedAssertions.assert(refereeStatus.activeGames.size == 0)
    }
  }
}
