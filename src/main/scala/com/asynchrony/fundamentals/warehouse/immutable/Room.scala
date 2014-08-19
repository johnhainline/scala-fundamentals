package com.asynchrony.fundamentals.warehouse.immutable

case class Room(volume: Int, boxes:Seq[Box] = Seq()) {
  val usedVolume = boxes.map(_.volume).sum

  def addBox(box: Box) = copy(boxes = boxes :+ box)
}

object Room {
  def apply(volume: Int) = {
    new Room(volume)
  }
}
