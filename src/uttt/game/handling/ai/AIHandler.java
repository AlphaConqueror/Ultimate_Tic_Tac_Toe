package uttt.game.handling.ai;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.game.handling.Board;
import uttt.utils.Move;
import uttt.utils.Symbol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AIHandler {

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

    private static MarkInterface[] getEmptyMarks(BoardInterface board) {
        List<MarkInterface> marks = new LinkedList<>();

        for(MarkInterface mark : board.getMarks())
            if(mark.getSymbol() == Symbol.EMPTY)
                marks.add(mark);

        return marks.toArray(new MarkInterface[0]);
    }
}
