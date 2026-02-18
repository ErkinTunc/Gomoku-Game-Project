package search;

/**
 * Immutable representation of a move in Gomoku.
 *
 * <p>
 * A move corresponds to placing a piece
 * at a specific row and column.
 * </p>
 * 
 * <p>
 * Moves are first-class entities in the search engine:
 * </p>
 * 
 * <ul>
 * <li>Minimax returns a Move</li>
 * <li>Alpha-Beta prunes based on move ordering</li>
 * <li>Evaluation heuristics score potential moves</li>
 * </ul>
 *
 * <p>
 * This class is implemented as a Java record
 * to ensure immutability and clarity.
 *
 * @param row row index (0-based)
 * @param col column index (0-based)
 * @since 2026-02-18
 * @author Erkin
 */
public record Move(int row, int col) {
}