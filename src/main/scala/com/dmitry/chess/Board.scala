package com.dmitry.chess

import com.dmitry.chess.ChessGameUtil.convertToChessBoardX
import com.dmitry.chess.pieces.{Bishop, King, Knight, Pawn, Piece, Queen, Rook}

import scala.collection.mutable.ListBuffer

class Board {
  val listOfCells: Array[Cell] = (for {
    x <- 0 to 7
    y <- 0 to 7
  } yield Cell(Cord(x, y), (x,y) match {
    case (0, 0) => Some(Rook(false))
    case (0, 1) => Some(Knight(false))
    case (0, 2) => Some(Bishop(false))
    case (0, 3) => Some(Queen(false))
    case (0, 4) => Some(King(false))
    case (0, 5) => Some(Bishop(false))
    case (0, 6) => Some(Knight(false))
    case (0, 7) => Some(Rook(false))
    case (1,_)  => Some(Pawn(false))

    case (7, 0) => Some(Rook(true))
    case (7, 1) => Some(Knight(true))
    case (7, 2) => Some(Bishop(true))
    case (7, 3) => Some(Queen(true))
    case (7, 4) => Some(King(true))
    case (7, 5) => Some(Bishop(true))
    case (7, 6) => Some(Knight(true))
    case (7, 7) => Some(Rook(true))
    case (6,_)  => Some(Pawn(true))

    case _ => None

  })).toArray

  var listOfRemovedPieces = new ListBuffer[Piece]()

  def getPieceByCord(cord: Cord): Option[Piece] =
    listOfCells
      .find(cell => cell.cord == cord)
      .flatMap(cell => cell.piece)

  def getPieceByCordAndColor(cord: Cord, isWhite: Boolean): Option[Piece] =
    listOfCells
      .find(cell => cell.cord == cord)
      .flatMap(cell => cell.piece.filter(piece => piece.isWhite == isWhite)
      )

  def getKingCellByColor(isWhite: Boolean): Cell =
    listOfCells
      .find(cell => {
        cell.piece match {
          case Some(piece) => piece.isInstanceOf[King] && piece.isWhite == isWhite
          case None => false
        }
      }).getOrElse(Cell(Cord(0,0), None))

  def replacePieceByCord(cord: Cord, piece: Option[Piece]): Unit =
    listOfCells((8 * cord.x) + cord.y) = Cell(cord, piece)

  def isKingInCheck(isWhite: Boolean): Boolean = {
    val kingCell = getKingCellByColor(isWhite)
    kingCell.piece match {
      case Some(king) => king.asInstanceOf[King].isInCheck(kingCell.cord ,this)
      case None => false
    }
  }

  def movePiece(inputMove: Array[Int]): Boolean = {
    val fromCellCord = Cord(inputMove(1),inputMove(0))
    val targetCellCord = Cord(inputMove(3),inputMove(2))

    getPieceByCord(fromCellCord) match {
      case Some(pieceToMove) =>
        if(pieceToMove.getValidMoves(fromCellCord, this).contains(targetCellCord)){
          //Store removed pieces
          getPieceByCord(targetCellCord) match {
            case Some(value) => listOfRemovedPieces += value
            case None => ()
          }

          println(s"\nNext Move: ${pieceToMove.symbol} from $fromCellCord to $targetCellCord")
          replacePieceByCord(targetCellCord, Some(pieceToMove))
          replacePieceByCord(fromCellCord, None)
          printBoard()

          if(isKingInCheck(!pieceToMove.isWhite)){
            println(s"${if(!pieceToMove.isWhite) "Player 1 (white)" else "Player 2 (black)"} " +
              s"is in check by ${pieceToMove.symbol} from $targetCellCord!")
            false // after the move, king is in check -> stop the game

          } else {
            true // move was successful -> proceed to next move
          }

        } else {
          println(s"Invalid move: move from $fromCellCord to $targetCellCord " +
            s"not allowed for ${pieceToMove.symbol} (${pieceToMove.getClass.getSimpleName})")
          false // stop the game
        }

      case None =>
        println(s"Invalid move: no piece in the input cell $fromCellCord")
        false // stop the game
    }
  }

  def printBoard(): Unit ={
    println("   a   b   c   d   e   f   g   h")
    listOfCells.foreach(cell =>{
      //Printing the row number for each start of the row
      if(cell.cord.y == 0) print(s"${convertToChessBoardX(cell.cord.x)}|")

      //Printing the values of the row
      print(cell.piece match {
        case Some(value) => s" ${value.symbol} |"
        case None => "   |"
      })

      //Printing the row number for each end of the row
      if(cell.cord.y == 7) {
        print(s"${cell.cord.x + 8 - cell.cord.x*2}")
        //Printing the Player 2 info
        if(cell.cord.x == 0) print(s"    Player 2(black) Removed: ${listOfRemovedPieces
          .filter(piece => piece.isWhite)
          .flatMap(piece => piece.symbol)
          .mkString(" ")}")
        //Printing the Player 1 info
        if(cell.cord.x == 7) print(s"    Player 1(white) Removed: ${listOfRemovedPieces
          .filter(piece => !piece.isWhite)
          .flatMap(piece => piece.symbol)
          .mkString(" ")}")
        print("\n")
      }
    })
    println("   a   b   c   d   e   f   g   h")
  }
}

