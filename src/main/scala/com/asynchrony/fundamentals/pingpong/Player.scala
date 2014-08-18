package com.asynchrony.fundamentals.pingpong

import akka.actor.{ActorLogging, ActorRef, Actor}

class Player(referee: ActorRef, name: String) extends Actor with ActorLogging{

  referee ! Join(name)

  def receive = {
    case Game(opponent) =>
      log.info("Starting new game against " + opponent)
      opponent.ref ! Ping()

    case Pong() =>
      sender ! Ping()

    case Ping() =>
      sender ! Pong()
  }
}

case class Join(name: String)
case class Game(opponent: ActivePlayer)
case class Ping()
case class Pong()