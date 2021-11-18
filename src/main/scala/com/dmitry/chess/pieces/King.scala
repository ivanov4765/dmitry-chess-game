package com.dmitry.chess.pieces

import com.dmitry.chess.ChessGameUtil.{buildShiftedCord, diagonalShifts, linearShifts}
import com.dmitry.chess.{Board, Cord}


case class King(isWhite: Boolean) extends Piece {

  override def getValidMoves(cord: Cord, board: Board): Seq[Cord] =
    (for{
        xMove <- -1 to 1
        yMove <- -1 to 1
      } yield Cord(cord.x + xMove, cord.y + yMove))
      .filter(cord => board.getPieceByCordAndColor(cord, this.isWhite).isEmpty)

  def isInCheck(cord: Cord, board: Board): Boolean = {

    def getInCheckCords[A](shifts: Seq[(Int, Int)]): Seq[Cord] =
      shifts.flatten(shift =>{
        (1 to 7).takeWhile(i =>
          board
            .getPieceByCord(buildShiftedCord(cord, shift, i)) match {
            case Some(piece) => (piece.isWhite != isWhite) && (piece.isInstanceOf[A] || piece.isInstanceOf[Queen])
            case None => true
          }
        ).filter(i => board
          .getPieceByCordAndColor(buildShiftedCord(cord, shift, i), !isWhite).isDefined)
          .map(i => buildShiftedCord(cord, shift, i))
      })

    getInCheckCords[Rook](linearShifts).nonEmpty || getInCheckCords[Bishop](diagonalShifts).nonEmpty

    //TODO: add "check" validation for Pawn

  }

  override val symbol: String = if (isWhite) "♚" else "♔"
}