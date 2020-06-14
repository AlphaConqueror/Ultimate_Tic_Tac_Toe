package uttt.game.handling.ai;

public class MoveValuation {

    private final int depth;
    private final int score;

    public MoveValuation(int depth, int score) {
        this.depth = depth;
        this.score = score;
    }

    public int getDepth() {
        return depth;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Move valuation [Score: " + score + ", depth: " + depth + "]";
    }
}
