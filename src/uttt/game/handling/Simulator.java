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

    @Override
    public BoardInterface[] getBoards() {
        return boards;
    }

    @Override
    public void setBoards(BoardInterface[] boards) throws IllegalArgumentException {
        this.boards = boards;
    }

    @Override
    public Symbol getCurrentPlayerSymbol() {
        return currentSymbol;
    }

    @Override
    public void setCurrentPlayerSymbol(Symbol symbol) throws IllegalArgumentException {
        currentSymbol = symbol;
    }

    @Override
    public boolean setMarkAt(Symbol symbol, int boardIndex, int markIndex) throws IllegalArgumentException {
        if(symbol == Symbol.EMPTY || symbol != currentSymbol)
            return false;

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

    @Override
    public int getIndexNextBoard() {
        return indexNextBoard;
    }

    @Override
    public void setIndexNextBoard(int index) throws IllegalArgumentException {
        indexNextBoard = index;
    }

    @Override
    public boolean isGameOver() {
        if(getWinner() != Symbol.EMPTY)
            return true;

        for(int i = 0; i < 9; i++)
            if(!boards[i].isClosed())
                return false;

        return true;
    }

    @Override
    public boolean isMovePossible(int boardIndex) throws IllegalArgumentException {
        if(boardIndex < 0 || boardIndex > 8)
            return false;

        BoardInterface board = boards[boardIndex];

        if(board.isClosed())
            return false;

        for(int i = 0; i < 9; i++)
            if(board.isMovePossible(i))
                return true;

        return false;
    }

    @Override
    public boolean isMovePossible(int boardIndex, int markIndex) throws IllegalArgumentException {
        if(boards[boardIndex].isClosed())
            return false;

        return boards[boardIndex].isMovePossible(markIndex);
    }

    @Override
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

    @Override
    public void run(PlayerInterface playerOne, PlayerInterface playerTwo, UserInterface ui) throws IllegalArgumentException {
        setCurrentPlayerSymbol(Symbol.CROSS);

        //TODO: Log and debug

        while(!isGameOver()) {
            PlayerInterface currentPlayer = playerOne.getSymbol() == currentSymbol ? playerOne : playerTwo;
            Move move = currentPlayer.getPlayerMove(this, ui);

            setMarkAt(currentSymbol, move.getBoardIndex(), move.getMarkIndex());
            ui.updateScreen(this);
        }
    }
}
