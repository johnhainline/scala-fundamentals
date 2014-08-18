package com.asynchrony.fundamentals.pingpong

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import org.scalatest.{Matchers, FunSpec}


import akka.pattern.ask

import scala.concurrent.Await
import scala.concurrent.duration._

class RefereeTest extends FunSpec with Matchers {

  describe("A Referee") {
    val system = ActorSystem()
    val referee = system.actorOf(Props[Referee](new Referee))
    val playerNames = List("Joe", "John", "Steve", "Jim", "Aaron")

    it ("should send the appropriate status response") {
      implicit val timeout = Timeout(3 seconds)
      val res = Await.result(ask(referee, StatusRequest), 3 seconds).asInstanceOf[RefereeStatus]
      res should equal(RefereeStatus(List.empty[ActivePlayer], Set.empty[ActiveGame]))
    }

    it ("should add players to games correctly") {
      playerNames.foreach{ name =>
        system.actorOf(Props[Player](new Player(referee, name)))
      }

      implicit val timeout = Timeout(3 seconds)
      val res = Await.result(ask(referee, StatusRequest), 3 seconds).asInstanceOf[RefereeStatus]
      res.availablePlayers.length should equal(1)
      res.activeGames.size should equal(2)

    }
  }
}
