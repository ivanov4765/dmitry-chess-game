package com.dmitry.chess.pieces

import com.dmitry.chess.{Board, Cord}

case class Bishop(isWhite: Boolean) extends Piece {

  override def getValidMoves(cord: Cord, board: Board): Seq[Cord] =
    computeAllAvailableMoves(cord, board, diagonalMoves = true)

  override val symbol: String = if (isWhite) "♝" else "♗︎"
}
