package search;

/**
 * Heuristic evaluation for non-terminal states.
 * Score convention: positive favors MAX, negative favors MIN.
 */
public interface Evaluator {
    int evaluate(GameState state);
}