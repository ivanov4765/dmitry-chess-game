## Chess Game

**Requirements:** 

You have been asked to write a computer program to allow two human players to play chess.

The program should simply read in moves and validate them, tracking and showing the board state.  
It should determine if a move leaves the player in check.  It does not need to handle checkmate.

Provided is a Java interface via which each attempted move can be obtained.

**Solution:**

* Game starts with an empty board:
```
   a   b   c   d   e   f   g   h
8| ♖ | ♘ | ♗︎ | ♕ | ♔ | ♗︎ | ♘ | ♖ |8    Player 2(black) Removed: 
7| ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ |7
6|   |   |   |   |   |   |   |   |6
5|   |   |   |   |   |   |   |   |5
4|   |   |   |   |   |   |   |   |4
3|   |   |   |   |   |   |   |   |3
2| ♟︎ | ♟︎ | ♟︎ | ♟︎ | ♟︎ | ♟︎ | ♟︎ | ♟︎ |2
1| ♜ | ♞ | ♝ | ♛︎ | ♚ | ♝ | ♞ | ♜ |1    Player 1(white) Removed: 
   a   b   c   d   e   f   g   h
```

* Then reads data from an input file and tries to perform a move

* If move is successful:
```
    Next Move: ♟︎ from (e2) to (e4)
   a   b   c   d   e   f   g   h
8| ♖ | ♘ | ♗︎ | ♕ | ♔ | ♗︎ | ♘ | ♖ |8    Player 2(black) Removed: 
7| ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ | ♙ |7
6|   |   |   |   |   |   |   |   |6
5|   |   |   |   |   |   |   |   |5
4|   |   |   |   | ♟︎ |   |   |   |4
3|   |   |   |   |   |   |   |   |3
2| ♟︎ | ♟︎ | ♟︎ | ♟︎ |   | ♟︎ | ♟︎ | ♟︎ |2
1| ♜ | ♞ | ♝ | ♛︎ | ♚ | ♝ | ♞ | ♜ |1    Player 1(white) Removed: 
   a   b   c   d   e   f   g   h
```

* If move is invalid:
```
   a   b   c   d   e   f   g   h
8| ♖ | ♘ | ♗︎ | ♕ | ♔ | ♗︎ | ♘ | ♖ |8    Player 2(black) Removed: 
7| ♙ | ♙ | ♙ | ♙ |   | ♙ | ♙ | ♙ |7
6|   |   |   |   |   |   |   |   |6
5|   |   |   |   | ♙ |   |   |   |5
4|   |   |   |   | ♟︎ |   |   |   |4
3|   |   |   |   |   |   |   |   |3
2| ♟︎ | ♟︎ | ♟︎ | ♟︎ |   | ♟︎ | ♟︎ | ♟︎ |2
1| ♜ | ♞ | ♝ | ♛︎ | ♚ | ♝ | ♞ | ♜ |1    Player 1(white) Removed: 
   a   b   c   d   e   f   g   h
Invalid move: move from (b1) to (b3) not allowed for ♞ (Knight)
```

* After each move validate if opposite king is in check state:
```
Next Move: ♛︎ from (f3) to (f7)
   a   b   c   d   e   f   g   h
8| ♖ |   | ♗︎ | ♕ | ♔ | ♗︎ | ♘ | ♖ |8    Player 2(black) Removed: 
7| ♙ | ♙ | ♙ |   |   | ♛︎ | ♙ | ♙ |7
6|   |   | ♘ | ♙ |   |   |   |   |6
5|   |   |   |   | ♙ |   |   |   |5
4|   |   | ♝ |   | ♟︎ |   |   |   |4
3|   |   |   |   |   |   |   |   |3
2| ♟︎ | ♟︎ | ♟︎ | ♟︎ |   | ♟︎ | ♟︎ | ♟︎ |2
1| ♜ | ♞ | ♝ |   | ♚ |   | ♞ | ♜ |1    Player 1(white) Removed: ♙
   a   b   c   d   e   f   g   h
Player 2 (black) is in check by ♛︎ from (f7)!
```

* Additional validation for empty cell(e.g. Move g5h5):
```
   a   b   c   d   e   f   g   h
8| ♖ | ♘ |   | ♕ | ♔ | ♗︎ | ♘ | ♖ |8    Player 2(black) Removed: 
7| ♙ | ♙ | ♙ |   |   | ♙ | ♙ | ♙ |7
6|   |   |   |   | ♗︎ |   |   |   |6
5|   |   |   | ♟︎ | ♙ |   |   |   |5
4|   |   |   |   |   |   |   |   |4
3|   |   | ♞ |   |   |   |   | ♟︎ |3
2| ♟︎ | ♟︎ | ♟︎ | ♟︎ |   | ♟︎ | ♟︎ | ♜ |2
1| ♜ |   | ♝ | ♛︎ | ♚ | ♝ | ♞ |   |1    Player 1(white) Removed: ♙
   a   b   c   d   e   f   g   h
Invalid move: no piece in the input cell (g5)
```

* The game stores the removed pieces as: `Player 1(white) Removed: ♙`