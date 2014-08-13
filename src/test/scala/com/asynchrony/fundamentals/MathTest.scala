package com.asynchrony.fundamentals

import org.scalatest.mock.MockitoSugar
import org.scalatest._
import org.mockito.Mockito._

class MathTest extends FlatSpec with Matchers with MockitoSugar {

  val randomDieRoller = mock[RandomDieRoller]

  "diceThrowsDistribution" should "return a single result" in {
    when(randomDieRoller.rollDie).thenReturn(1, 4)

    val results = Math.diceThrowsDistribution(1, randomDieRoller)

    results should equal (Map(5 -> 1))
  }

  "diceThrowsDistribution" should "return the full dice throw distribution" in {
    when(randomDieRoller.rollDie).thenReturn(1, 1, 4, 3, 6, 5, 3, 3, 5, 2, 2, 1)

    val results = Math.diceThrowsDistribution(6, randomDieRoller)

    results should equal (Map(2 -> 1, 7 -> 2, 11 -> 1, 6 -> 1, 3 -> 1))
  }

}
