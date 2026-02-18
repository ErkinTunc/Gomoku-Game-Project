package search;

/**
 * Represents the two adversarial roles in the game.
 *
 * <ul>
 * <li>MAX: the maximizing player in Minimax</li>
 * <li>MIN: the minimizing player</li>
 * </ul>
 *
 * <p>
 * This abstraction decouples the search algorithm
 * from UI-specific player classes.
 *
 * <p>
 * Used heavily in:
 * <ul>
 * <li>Minimax role switching</li>
 * <li>Utility computation</li>
 * <li>Move evaluation</li>
 * </ul>
 * 
 * @version 1.9
 * @since 2026-02-18
 * @author Erkin
 */
public enum PlayerType {
    /**
     * Maximizing player (AI or first player).
     */
    MAX,

    /**
     * Minimizing opponent.
     */
    MIN;

    /**
     * Returns the opponent of the current player.
     *
     * @return the opposite PlayerType
     */
    public PlayerType opponent() {
        return this == MAX ? MIN : MAX;
    }
}