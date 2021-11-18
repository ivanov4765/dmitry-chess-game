package com.dmitry.chess.pieces

import com.dmitry.chess.ChessGameUtil.{buildShiftedCord, diagonalShifts, linearShifts}
import com.dmitry.chess.{Board, Cord}

trait Piece{

  val isWhite: Boolean
  val symbol: String
  def getValidMoves(cord: Cord, board: Board): Seq[Cord]

  def computeAllAvailableMoves(cord: Cord,
                               board: Board,
                               diagonalMoves: Boolean = false,
                               linearMoves: Boolean = false): Seq[Cord] = {

    def getValidMovesForShifts(shifts: Seq[(Int, Int)]): Seq[Cord] =
        shifts.flatten(shift =>{
          (1 to 7).takeWhile(i =>
              board
                .getPieceByCord(buildShiftedCord(cord, shift, i)) match {
                case Some(piece) => piece.isWhite != isWhite
                case None => true
          }
        ).map(i => buildShiftedCord(cord, shift, i))
      })

    (linearMoves, diagonalMoves) match {
      case (true, true) => getValidMovesForShifts(linearShifts)
                            .concat(getValidMovesForShifts(diagonalShifts))
      case (true, _) => getValidMovesForShifts(linearShifts)
      case (_, true) => getValidMovesForShifts(diagonalShifts)
      case _ => Seq()
    }
  }
}
