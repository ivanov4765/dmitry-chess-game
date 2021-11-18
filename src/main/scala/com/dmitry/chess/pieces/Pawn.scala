package com.dmitry.chess.pieces

import com.dmitry.chess.{Board, Cord}

case class Pawn(isWhite: Boolean) extends Piece {

  override def getValidMoves(cord: Cord, board: Board): Seq[Cord] = {
    val isFirstMove = !isWhite && cord.x == 1 || isWhite && cord.x == 6
    val direction = if(isWhite) -1 else 1

    val twoSquareMoves = if(isFirstMove) Seq(Cord(cord.x + (2 * direction), cord.y)) else Nil

    val validForwardMoves = (
      Seq(Cord(cord.x + (1 * direction), cord.y)) ++ twoSquareMoves)
      .filter(cord => board.getPieceByCord(cord).isEmpty)


    val validDiagonalMoves = Seq(
      Cord(cord.x + (1 * direction), cord.y - 1),
      Cord(cord.x + (1 * direction) , cord.y + 1)
    ).filter(cord => board.getPieceByCordAndColor(cord, !this.isWhite).isDefined)

    validDiagonalMoves.concat(
      validForwardMoves
        .filter(cord => board.getPieceByCord(cord).isEmpty)
    )

  }

  override val symbol: String = if (isWhite) "♟︎" else "♙"
}
