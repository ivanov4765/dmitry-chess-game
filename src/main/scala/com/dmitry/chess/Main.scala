package com.dmitry.chess

object Main {

  def main(args: Array[String]): Unit = {

    val fileName = "checkmate.txt"

    ChessGameUtil.getInputFile(fileName)
      .foreach(
        inputFile => {
          val gameBoard = new Board()
          gameBoard.printBoard()

          var continueGame = true
          while(continueGame){
            ChessGameUtil.getNextMove(inputFile) match {
              case Some(nextMove) =>
                continueGame = gameBoard.movePiece(nextMove)
              case None =>
                println("\nGame Ended")
                continueGame = false
            }
          }
        }
      )
  }
}
