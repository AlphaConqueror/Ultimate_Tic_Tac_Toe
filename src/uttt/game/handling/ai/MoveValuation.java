package uttt.game.handling.ai;

public class MoveValuation {

    private int depth, score;

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
}
