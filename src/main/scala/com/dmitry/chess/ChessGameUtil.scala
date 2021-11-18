package com.dmitry.chess

import com.whitehatgaming.UserInputFile

import java.io.IOException

object ChessGameUtil {

  val linearShifts = Seq((0, 1), (0, -1), (-1, 0), (1, 0))

  val diagonalShifts = Seq((1, 1), (1, -1), (-1, -1), (-1, 1))

  def convertToChessBoardX(x: Int): Int = x + 8 - x*2

  def buildShiftedCord(cord: Cord, shift: (Int, Int), i: Int): Cord = Cord(cord.x + (shift._1 * i), cord.y + (shift._2 * i))

  def getNextMove(file: UserInputFile): Option[Array[Int]] = {
    try {
      val nextMoveArr = file.nextMove()
      //chek for empty line in the input file
      if(nextMoveArr == null)
        None
      //check if input data is within the board limits
      else if(nextMoveArr.exists(index => index.abs > 7)) {
        println(s"ERROR: Input data is invalid(${nextMoveArr.mkString(",")})")
        None
      } else
        Some(nextMoveArr)
    } catch {
      case _: IOException => None
    }
  }

  def getInputFile(fileName: String): Option[UserInputFile] ={
    try{
      Some(new UserInputFile(getClass.getResource(s"/$fileName").getPath))
    } catch {
      case _: Exception =>
        println(s"$fileName - File not found")
        None
    }
  }
}
