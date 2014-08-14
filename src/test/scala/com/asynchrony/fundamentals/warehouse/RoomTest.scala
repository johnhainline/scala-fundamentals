package com.asynchrony.fundamentals.warehouse

import org.scalatest.{DiagrammedAssertions, Matchers, FunSpec}
import DiagrammedAssertions._

class RoomTest extends FunSpec with Matchers{
  it("can hold boxes") {
    val myBox = new Box(100)
    val room = Room(1000)
    val roomWithBox = room.addBox(myBox)
    roomWithBox.boxes should contain (myBox)
  }

  it("changes used size when boxes are added") {
    val myBox = new Box(100)
    val room = Room(1000)
    val roomWithBox = room.addBox(myBox)
    DiagrammedAssertions.assert(roomWithBox.usedVolume == myBox.volume)
  }
}
