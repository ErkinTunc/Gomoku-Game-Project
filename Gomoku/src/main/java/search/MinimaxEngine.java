package search;

import java.util.List;

/**
 * Depth-limited Minimax search over immutable GameState.
 */
public final class MinimaxEngine {

    private final Evaluator evaluator;

    public MinimaxEngine(Evaluator evaluator) {
        if (evaluator == null) {
            throw new IllegalArgumentException("evaluator cannot be null.");
        }
        this.evaluator = evaluator;
    }

    public SearchResult findBestMove(GameState rootState, int depth) {
        if (rootState == null) {
            throw new IllegalArgumentException("rootState cannot be null.");
        }
        if (depth < 0) {
            throw new IllegalArgumentException("depth must be >= 0.");
        }

        return minimax(rootState, depth);
    }

    private SearchResult minimax(GameState state, int depth) {

        // Terminal => exact utility (no heuristic)
        if (state.isTerminal()) {
            return new SearchResult(null, state.getUtility());
        }

        // Depth limit => heuristic
        if (depth == 0) {
            return new SearchResult(null, evaluator.evaluate(state));
        }

        List<Move> legalMoves = state.getLegalMoves();

        // If no moves exist, treat as draw-like (should be rare given your rules)
        if (legalMoves.isEmpty()) {
            return new SearchResult(null, 0);
        }

        boolean isMaxTurn = (state.getCurrentPlayer() == PlayerType.MAX);

        Move bestMove = null;
        int bestScore = isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : legalMoves) {
            GameState childState = state.applyMove(move);
            int childScore = minimax(childState, depth - 1).evaluationScore();

            if (isMaxTurn) {
                if (childScore > bestScore) {
                    bestScore = childScore;
                    bestMove = move;
                }
            } else {
                if (childScore < bestScore) {
                    bestScore = childScore;
                    bestMove = move;
                }
            }
        }

        return new SearchResult(bestMove, bestScore);
    }
}