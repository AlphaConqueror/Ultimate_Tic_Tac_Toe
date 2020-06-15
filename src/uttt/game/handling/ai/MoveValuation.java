package uttt.game.handling.ai;

public class MoveValuation {

    private final int depth;
    private final int score;

    /**
     * Constructor of {@link MoveValuation}
     *
     * @param depth The initial depth of the move valuation.
     * @param score The initial score of the move valuation.
     */
    public MoveValuation(int depth, int score) {
        this.depth = depth;
        this.score = score;
    }

    /**
     * Used to get the depth of the move valuation.
     *
     * @return The depth of the move valuation.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Used to get the score of the move valuation.
     *
     * @return The score of the move valuation.
     */
    public int getScore() {
        return score;
    }

    /**
     * Used to get a string containing information about the move valuation.
     *
     * @return {@code String} containing information about the move valuation.
     */
    @Override
    public String toString() {
        return "Move valuation [Score: " + score + ", depth: " + depth + "]";
    }
}
