package uttt.game.handling.ai;

import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.handling.Board;
import uttt.utils.Symbol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AIHandler {

    /**
     * Gets the worst move by evaluating score and depth of the move.
     * Passes parameters to #getWorstCaseMove.
     *
     * @param board         The board that is currently played on.
     * @param depth         The current depth of the branch.
     * @param playerSymbol  The symbol of the bot.
     * @param currentSymbol The symbol of the current player.
     *
     * @return The worst move valuation of all.
     */
    public static MoveValuation getNextMove(BoardInterface board, int depth, Symbol playerSymbol, Symbol currentSymbol) {
        List<MoveValuation> moveValuations = new ArrayList<>();
        MarkInterface[] emptyMarks = getEmptyMarks(board);

        if(emptyMarks.length > 1) {
            for (MarkInterface mark : emptyMarks) {
                if (mark.getSymbol() == Symbol.EMPTY) {
                    BoardInterface boardClone = Board.cloneBoard(board);

                    boardClone.setMarkAt(currentSymbol, mark.getPosition());

                    if(boardClone.getWinner() == Symbol.EMPTY)
                        moveValuations.add(getNextMove(boardClone, depth + 1, playerSymbol, currentSymbol.flip()));
                    else
                        moveValuations.add(new MoveValuation(depth, boardClone.getWinner() == playerSymbol ? 1 : 0));
                }
            }
        } else {
            BoardInterface boardClone = Board.cloneBoard(board);

            boardClone.setMarkAt(currentSymbol, emptyMarks[0].getPosition());
            moveValuations.add(new MoveValuation(depth, board.getWinner() == playerSymbol ? 1 : 0));
        }

        return getWorstCaseMove(moveValuations.toArray(new MoveValuation[0]), playerSymbol, currentSymbol);
    }

    /**
     * Gets the worst move by evaluating score and depth of the move.
     *
     * @param moves         All move valuations at the same depth of a branch.
     * @param playerSymbol  The symbol of the bot.
     * @param currentSymbol The symbol of the current player.
     *
     * @return The worst move valuation of all.
     */
    private static MoveValuation getWorstCaseMove(MoveValuation[] moves, Symbol playerSymbol, Symbol currentSymbol) {
        MoveValuation move = null;

        for(MoveValuation m : moves)
            if(move == null)
                move = m;
            else if(currentSymbol == playerSymbol) {
                if(m.getScore() > move.getScore())
                    move = m;
                else if (m.getScore() == move.getScore() && m.getDepth() > move.getDepth())
                    move = m;
            } else if(m.getScore() < move.getScore())
                return m;

        return move;
    }

    /**
     * Gets all empty marks on a board.
     *
     * @param board The board containing the marks.
     *
     * @return Array of all empty marks on the board.
     */
    private static MarkInterface[] getEmptyMarks(BoardInterface board) {
        List<MarkInterface> marks = new LinkedList<>();

        for(MarkInterface mark : board.getMarks())
            if(mark.getSymbol() == Symbol.EMPTY)
                marks.add(mark);

        return marks.toArray(new MarkInterface[0]);
    }
}
