package org.example.lld.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum Status {
    ACTIVE,
    BLACK_WIN,
    WHITE_WIN,
    FORFEIT,
    STALEMATE,
    RESIGNATION;
}

public class Chess {
    boolean end = false;
    Board board;
    Player[] players;
    Player turn;
    Random random;
    List<Move> playedMove;

    public Chess(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        random = new Random();
        playedMove = new ArrayList<>();
    }

    void start () {
        while (true) {
            while (!playMove(turn, random.nextInt(8), random.nextInt(8), random.nextInt(8), random.nextInt(8)));
            if (turn.win) break;
            switchTurn();
        }
    }

    public boolean playMove(Player player, int startX, int startY, int endX, int endY) {
        Box startBox = board.boxes[startX][startY];
        Box endBox = board.boxes[endX][endY];
        Move move = new Move(player, startBox, endBox);
        if (player.canMove(move)) {
            player.move(move);
            playedMove.add(move);
            return true;
        }
        else return false;
    }

    void switchTurn() {
        turn = turn == players[0] ? players[1] : players[0];
    }
}

class Player {
    private int id;
    private boolean isWhite;
    boolean win;
    List<Piece> killedPiece;
    List<Move> playedMove;

    public Player (boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean canMove(Move move) {
        Box startBox = move.startBox;
        Box endBox = move.endBox;
        return startBox.piece != null && startBox.piece.isWhite == isWhite && startBox.piece.canMove(startBox, endBox);
    }

    public void move(Move move) {
        Box startBox = move.startBox;
        Box endBox = move.endBox;
        if (endBox.piece != null) {
            kill(move);
        }
        move.movedPiece = startBox.piece;
        endBox.piece = startBox.piece;
        startBox.piece = null;
        playedMove.add(move);
    }

    public void kill(Move move) {
        Box endBox = move.endBox;
        endBox.piece.isKilled = true;
        move.killedPiece = endBox.piece;
        killedPiece.add(endBox.piece);;
        if (endBox.piece instanceof King) win = true;
    }
}

class Move {
    Player player;
    Piece movedPiece;
    Piece killedPiece;
    Box startBox;
    Box endBox;
    public Move (Player player, Box startBox, Box endBox) {
        this.player = player;
        this.startBox = startBox;
        this.endBox = endBox;
    }
}

class Board {
    Box[][] boxes;
}

class Box {
    Piece piece;
    int x;
    int y;
}

abstract class Piece {
    boolean isWhite;
    boolean isKilled;
    public Piece (boolean isWhite) {
        this.isWhite = isWhite;
    }
    public abstract boolean canMove(Box startBox, Box endBox);
}

class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx + dy == 1;
    }
}

class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx == dy || dx == 0 || dy == 0;
    }
}

class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx == dy;
    }
}

class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx * dy == 2;
    }
}

class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx == 0 || dy == 0;
    }
}

class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }
    public boolean canMove(Box startBox, Box endBox) {
        if (endBox.piece != null && endBox.piece.isWhite == this.isWhite) return false;
        int dx = Math.abs(endBox.x - startBox.x);
        int dy = Math.abs(endBox.y - startBox.y);
        return dx == 0 && dy == 1;
    }
}