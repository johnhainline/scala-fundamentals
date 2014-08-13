package com.asynchrony.fundamentals

import scala.util.Random

class RandomDieRoller {
  val random = new Random
  def rollDie = random.nextInt(6) + 1
}

object Math {
  def main(args: Array[String]) {
    println("Hello, world!")
  }


  // Throws 2 dice "timesToThrow" times and returns a map where key = dice result, value = number of times thrown
  def diceThrowsDistribution(timesToThrow: Int, dieRoller: RandomDieRoller = new RandomDieRoller): Map[Int, Int] = {
    Map.empty[Int, Int]
  }
}
