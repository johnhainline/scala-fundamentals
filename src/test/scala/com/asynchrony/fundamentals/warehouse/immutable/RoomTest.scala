package com.asynchrony.fundamentals.warehouse.immutable

import com.asynchrony.fundamentals.warehouse.Room
import org.scalatest.{DiagrammedAssertions, FunSpec, Matchers}

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
