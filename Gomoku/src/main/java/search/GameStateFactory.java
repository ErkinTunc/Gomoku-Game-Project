package search;

/** 
 * Factory class to convert gameplay-layer board snapshots (Piece[][]) into search-layer GameState objects.
 * @author Erkin Tunç Boya
 * @version 1.9
 * @since 2026-02-18
 */

import model.Piece;

public final class GameStateFactory {

    private static final byte EMPTY_CELL = (byte) -1; // Empty cell represents by -1, since 0 and 1 are used for
                                                      // players.

    private GameStateFactory() {
        // Utility class
    }

    /**
     * Builds a search-layer GameState from a gameplay-layer board snapshot.
     *
     * Encoding:
     * -1 = empty
     * 0 = MIN
     * 1 = MAX
     *
     * Assumption: Piece.getColor() returns 0/1.
     */
    public static GameState fromPieceBoard(
            Piece[][] pieceBoard,
            PlayerType currentPlayer,
            Move lastMove,
            int winLength) {
        byte[][] searchBoard = convertPieceBoardToByteBoard(pieceBoard);
        return new GameState(searchBoard, currentPlayer, lastMove, winLength);
    }

    private static byte[][] convertPieceBoardToByteBoard(Piece[][] pieceBoard) {
        int rows = pieceBoard.length;
        int cols = pieceBoard[0].length;

        byte[][] board = new byte[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Piece piece = pieceBoard[row][col];

                if (piece == null) {
                    board[row][col] = EMPTY_CELL;
                    continue;
                }

                int color = piece.getColor(); // must be 0 or 1
                if (color != 0 && color != 1) {
                    throw new IllegalArgumentException("Piece color must be 0/1, got: " + color);
                }

                board[row][col] = (byte) color;
            }
        }

        return board;
    }
}