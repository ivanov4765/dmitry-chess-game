package com.dmitry.chess.pieces

import com.dmitry.chess.{Board, Cord}

case class Knight(isWhite: Boolean) extends Piece {

  override def getValidMoves(cord: Cord, board: Board): Seq[Cord] =
    Seq(
      Cord(cord.x + 1, cord.y + 2),
      Cord(cord.x + 2, cord.y + 1),
      Cord(cord.x + 1, cord.y - 2),
      Cord(cord.x + 2, cord.y - 1),
      Cord(cord.x - 1, cord.y + 2),
      Cord(cord.x - 2, cord.y + 1),
      Cord(cord.x - 1, cord.y - 2),
      Cord(cord.x - 2, cord.y - 1)
    ).filter(cord => board.getPieceByCordAndColor(cord, this.isWhite).isEmpty)

  override val symbol: String = if (isWhite) "♞" else "♘"
}