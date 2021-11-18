package com.dmitry.chess

import com.dmitry.chess.ChessGameUtil.convertToChessBoardX

case class Cord(x: Int, y: Int) {
  override def toString: String = s"(${('a' + y).toChar}${convertToChessBoardX(x)})"
}