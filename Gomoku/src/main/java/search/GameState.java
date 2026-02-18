package search;

/** 
 * Immutable representation of a game state in Gomoku.
 * @author Erkin Tunç Boya
 * @version 1.9
 * @since 2026-02-18
 */

import java.util.ArrayList;
import java.util.List;

public final class GameState {

    // Encoding:
    // -1 = empty
    // 0 = MIN
    // 1 = MAX
    private static final byte EMPTY_CELL = (byte) -1; // -1 is not a valid player value, so we can use it to represent
                                                      // emptiness

    private final byte[][] board;
    private final PlayerType currentPlayer;
    private final Move lastMove;
    private final int winLength;

    public GameState(byte[][] board,
            PlayerType currentPlayer,
            Move lastMove,
            int winLength) {

        this.board = board; // no copy
        this.currentPlayer = currentPlayer;
        this.lastMove = lastMove;
        this.winLength = winLength;
    }

    // ----------------------------
    // Getters
    // ----------------------------

    public PlayerType getCurrentPlayer() {
        return currentPlayer;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public int getWinLength() {
        return winLength;
    }

    public int getBoardSize() {
        return board.length;
    }

    public byte getCell(int row, int col) {
        return board[row][col];
    }

    // ----------------------------
    // State transitions
    // ----------------------------

    public GameState applyMove(Move move) {
        if (!isMoveLegal(move)) {
            throw new IllegalArgumentException("Illegal move: " + move);
        }

        byte[][] newBoard = deepCopyBoard(this.board);
        newBoard[move.row()][move.col()] = valueFor(currentPlayer);

        return new GameState(
                newBoard,
                currentPlayer.opponent(),
                move,
                winLength);
    }

    public List<Move> getLegalMoves() {
        List<Move> legalMoves = new ArrayList<>();

        // First move must be center
        if (lastMove == null) {
            int center = board.length / 2;
            if (board[center][center] == EMPTY_CELL) {
                legalMoves.add(new Move(center, center));
            }
            return legalMoves;
        }

        // Generate only empty cells adjacent to any piece
        // (simple implementation: scan all cells and apply adjacency test)
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != EMPTY_CELL) {
                    continue;
                }
                if (isAdjacentToAnyPiece(row, col)) {
                    legalMoves.add(new Move(row, col));
                }
            }
        }

        return legalMoves;
    }

    public boolean isTerminal() {
        if (lastMove == null) {
            return false; // No move has been played yet. We are at the initial state.
        }

        PlayerType playerWhoJustMoved = currentPlayer.opponent();
        if (isWinningMove(lastMove, playerWhoJustMoved)) {
            return true;
        }

        return isBoardFull();
    }

    public int getUtility() {
        if (lastMove == null) {
            throw new IllegalStateException("Utility undefined: no moves played.");
        }

        PlayerType playerWhoJustMoved = currentPlayer.opponent();

        if (isWinningMove(lastMove, playerWhoJustMoved)) {
            return (playerWhoJustMoved == PlayerType.MAX) ? 1 : -1;
        }

        if (isBoardFull()) {
            return 0;
        }

        throw new IllegalStateException("Utility is only defined for terminal states.");
    }

    // ----------------------------
    // Rules / legality
    // ----------------------------

    private boolean isMoveLegal(Move move) {
        int row = move.row();
        int col = move.col();

        if (!isInsideBoard(row, col)) {
            return false;
        }
        if (board[row][col] != EMPTY_CELL) {
            return false;
        }

        if (lastMove == null) {
            int center = board.length / 2;
            return row == center && col == center;
        }

        return isAdjacentToAnyPiece(row, col);
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    private boolean isAdjacentToAnyPiece(int row, int col) {
        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int colDelta = -1; colDelta <= 1; colDelta++) {
                if (rowDelta == 0 && colDelta == 0) {
                    continue;
                }
                int adjacentRow = row + rowDelta;
                int adjacentCol = col + colDelta;

                if (isInsideBoard(adjacentRow, adjacentCol) && board[adjacentRow][adjacentCol] != EMPTY_CELL) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private byte valueFor(PlayerType playerType) {
        return (playerType == PlayerType.MAX) ? (byte) 1 : (byte) 0;
    }

    // ----------------------------
    // Win detection (only lines through lastMove)
    // ----------------------------

    private boolean isWinningMove(Move move, PlayerType playerType) {
        byte playerValue = valueFor(playerType);

        return hasLineOfLength(move, playerValue, 1, 0) // horizontal
                || hasLineOfLength(move, playerValue, 0, 1) // vertical
                || hasLineOfLength(move, playerValue, 1, 1) // diagonal \
                || hasLineOfLength(move, playerValue, 1, -1); // diagonal /
    }

    private boolean hasLineOfLength(Move originMove, byte playerValue, int rowStep, int colStep) {
        int totalCount = 1; // include origin

        totalCount += countInDirection(originMove, playerValue, rowStep, colStep);
        totalCount += countInDirection(originMove, playerValue, -rowStep, -colStep);

        return totalCount >= winLength;
    }

    private int countInDirection(Move originMove, byte playerValue, int rowStep, int colStep) {
        int count = 0;

        int currentRow = originMove.row() + rowStep;
        int currentCol = originMove.col() + colStep;

        while (isInsideBoard(currentRow, currentCol) && board[currentRow][currentCol] == playerValue) {
            count++;
            currentRow += rowStep;
            currentCol += colStep;
        }

        return count;
    }

    // ----------------------------
    // Copy helpers
    // ----------------------------

    private byte[][] deepCopyBoard(byte[][] originalBoard) {
        byte[][] copy = new byte[originalBoard.length][originalBoard[0].length];
        for (int row = 0; row < originalBoard.length; row++) {
            System.arraycopy(originalBoard[row], 0, copy[row], 0, originalBoard[row].length);
        }
        return copy;
    }
}