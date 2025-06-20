package org.example.lld.jigsaw;

public class Jigsaw {
}

abstract class Piece {
}

class MiddlePiece extends Piece {
}

class EdgePiece extends Piece {
}

class CornerPiece extends Piece {
}

interface PieceFactory {
    Piece create();
    void solve(Piece piece);
}

class MiddlePieceFactory implements PieceFactory {
    @Override
    public Piece create() {
        return new MiddlePiece();
    }
    @Override
    public void solve(Piece piece) {
        System.out.println("Solved a " + piece.getClass().getSimpleName());
    }
}

class EdgePieceFactory implements PieceFactory {
    @Override
    public Piece create() {
        return new EdgePiece();
    }
    @Override
    public void solve(Piece piece) {
        System.out.println("Solved a " + piece.getClass().getSimpleName());
    }
}

class CornerPieceFactory implements PieceFactory {
    @Override
    public Piece create() {
        return new CornerPiece();
    }
    @Override
    public void solve(Piece piece) {
        System.out.println("Solved a " + piece.getClass().getSimpleName());
    }
}

class JigsawPuzzle {
    private PieceFactory pieceFactory;

    public void setPieceFactory(PieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
    }
    public Piece createPiece() {
        return pieceFactory.create();
    }
    public void solvePiece(Piece piece) {
        pieceFactory.solve(piece);
    }
}

class client {
    public static void main(String[] args) {
        JigsawPuzzle puzzle = new JigsawPuzzle();

        puzzle.setPieceFactory(new MiddlePieceFactory());
        Piece middlePiece = puzzle.createPiece();
        puzzle.solvePiece(middlePiece);

        puzzle.setPieceFactory(new EdgePieceFactory());
        Piece edgePiece = puzzle.createPiece();
        puzzle.solvePiece(edgePiece);

        puzzle.setPieceFactory(new CornerPieceFactory());
        Piece cornerPiece = puzzle.createPiece();
        puzzle.solvePiece(cornerPiece);
    }
}