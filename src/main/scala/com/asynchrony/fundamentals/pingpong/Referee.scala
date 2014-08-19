package com.asynchrony.fundamentals.pingpong

import akka.actor.{ActorLogging, ActorRef, Actor}

class Referee extends Actor with ActorLogging {
  var availablePlayers = List.empty[ActivePlayer]
  var activeGames = Set.empty[ActiveGame]

  def receive = {
    case Join(name) =>
      if (availablePlayers.isEmpty)
        availablePlayers = List(ActivePlayer(name, sender()))
      else {
        val otherPlayer = availablePlayers.head
        activeGames = activeGames + ActiveGame(otherPlayer, ActivePlayer(name, sender()))
        availablePlayers = availablePlayers.tail
        sender ! Game(otherPlayer)
      }

    case StatusRequest =>
      sender() ! RefereeStatus(availablePlayers, activeGames)

    case Depart(name) =>
      availablePlayers = ActivePlayer(name, sender()) :: availablePlayers
      activeGames = activeGames.filterNot(activeGame => activeGame.p1.name == name || activeGame.p2.name == name)
  }
}

case class ActiveGame(p1: ActivePlayer, p2: ActivePlayer)
case class ActivePlayer(name: String, ref: ActorRef)

case object StatusRequest
case class RefereeStatus(availablePlayers: List[ActivePlayer], activeGames: Set[ActiveGame])
