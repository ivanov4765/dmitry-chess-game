package com.dmitry.chess.pieces

import com.dmitry.chess.{Board, Cord}

case class Queen(isWhite: Boolean) extends Piece {

  override def getValidMoves(cord: Cord, board: Board): Seq[Cord] =
    computeAllAvailableMoves(cord, board, diagonalMoves = true, linearMoves = true)

  override val symbol: String = if (isWhite) "♛︎" else "♕"
}
