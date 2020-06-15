package uttt.game.handling;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.PlayerInterface;
import uttt.game.SimulatorInterface;
import uttt.game.UserInterface;
import uttt.utils.Move;
import uttt.utils.Symbol;

public class Simulator implements SimulatorInterface {

    private BoardInterface[] boards;
    private Symbol currentSymbol;
    private int indexNextBoard;

    public Simulator() {
        boards = new BoardInterface[9];
        currentSymbol = Symbol.EMPTY;
        indexNextBoard = -1;

        for(int i = 0; i < 9; i++)
            boards[i] = UTTTFactory.createBoard();
    }

    public BoardInterface[] getBoards() {
        return boards;
    }

    public void setBoards(BoardInterface[] boards) {
        this.boards = boards;
    }

    public Symbol getCurrentPlayerSymbol() {
        return currentSymbol;
    }

    public void setCurrentPlayerSymbol(Symbol symbol) {
        currentSymbol = symbol;
    }

    public boolean setMarkAt(Symbol symbol, int boardIndex, int markIndex) throws IllegalArgumentException {
        if(symbol == null)
            throw new IllegalArgumentException("Symbol is null.");

        if(boardIndex < 0 || boardIndex > 8)
            throw new IllegalArgumentException("Board index out of bounds.");

        if(markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException("Mark index out of bounds.");

        if(boards == null || boards[boardIndex] == null)
            throw new IllegalArgumentException("Board is null.");

        if(symbol != currentSymbol)
            throw new IllegalArgumentException("Symbol is not that from the current player.");

        if(indexNextBoard != -1 && boardIndex != indexNextBoard)
            return false;

        if(boards[boardIndex].isMovePossible(markIndex)) {
            boards[boardIndex].setMarkAt(symbol, markIndex);
            setCurrentPlayerSymbol(currentSymbol.flip());
            setIndexNextBoard(boards[markIndex].isClosed() ? -1 : markIndex);
            return true;
        }

        return false;
    }

    public int getIndexNextBoard() {
        return indexNextBoard;
    }

    public void setIndexNextBoard(int index) {
        indexNextBoard = index;
    }

    public boolean isGameOver() {
        if(getWinner() != Symbol.EMPTY)
            return true;

        for(int i = 0; i < 9; i++)
            if(!boards[i].isClosed())
                return false;

        return true;
    }

    public boolean isMovePossible(int boardIndex) throws IllegalArgumentException {
        if(boardIndex < 0 || boardIndex > 8)
            throw new IllegalArgumentException();

        if(indexNextBoard != -1 && boardIndex != indexNextBoard)
            return false;

        BoardInterface board = boards[boardIndex];

        if(board.isClosed())
            return false;

        for(int i = 0; i < 9; i++)
            if(board.isMovePossible(i))
                return true;

        return false;
    }

    public boolean isMovePossible(int boardIndex, int markIndex) {
        if(boardIndex < 0 || boardIndex > 8)
            throw new IllegalArgumentException("Board index out of bounds.");

        if(markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException("Mark index out of bounds.");

        if(indexNextBoard != -1 && boardIndex != indexNextBoard)
            return false;

        if(boards[boardIndex].isClosed())
            return false;

        return boards[boardIndex].isMovePossible(markIndex);
    }

    public Symbol getWinner() {
        for(int i = 0; i < 2; i++) {
            Symbol symbol = (i == 0) ? Symbol.CROSS : Symbol.CIRCLE;

            for(int j = 0; j < 3; j++) {
                //Vertical lines
                if(boards[j].getWinner() == symbol && boards[j + 3].getWinner() == symbol && boards[j + 6].getWinner() == symbol)
                    return symbol;
                //Horizontal lines
                if(boards[j * 3].getWinner() == symbol && boards[j * 3 + 1].getWinner() == symbol && boards[j * 3 + 2].getWinner() == symbol)
                    return symbol;
            }

            //Diagonal line: Top left -> Bottom right
            if(boards[0].getWinner() == symbol && boards[4].getWinner() == symbol && boards[8].getWinner() == symbol)
                return symbol;
            //Diagonal line: Top right -> Bottom left
            if(boards[2].getWinner() == symbol && boards[4].getWinner() == symbol && boards[6].getWinner() == symbol)
                return symbol;
        }

        return Symbol.EMPTY;
    }

    public void run(PlayerInterface playerOne, PlayerInterface playerTwo, UserInterface ui) {
        setCurrentPlayerSymbol(Symbol.CROSS);

        while(!isGameOver()) {
            PlayerInterface currentPlayer = playerOne.getSymbol() == currentSymbol ? playerOne : playerTwo;
            Move move = currentPlayer.getPlayerMove(this, ui);

            setMarkAt(currentSymbol, move.getBoardIndex(), move.getMarkIndex());
            ui.updateScreen(this);
        }

        ui.showGameOverScreen(this);
    }
}
