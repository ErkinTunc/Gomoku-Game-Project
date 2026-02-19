package search;

/**
 * Result of an adversarial search: best move + its evaluation.
 */
public record SearchResult(Move bestMove, int evaluationScore) {
}