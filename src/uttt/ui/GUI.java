package uttt.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.game.UserInterface;
import uttt.utils.Move;
import uttt.utils.Symbol;

public class GUI implements UserInterface {

	private Move UserMoveChoice = null;

	private final UTTTButton[][] buttons = new UTTTButton[9][9]; // create 9 buttons
	private final JPanel[] panels = new JPanel[9]; // create 9 panels

	private Symbol currentPlayer = Symbol.CROSS;
	private final String label = "Turn of Player with Symbol: ";
	private final JLabel jLabel = new JLabel(label + currentPlayer);

	public GUI() {
		gamePanel();
	}

	public void gamePanel() {
		JFrame frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel areaAndResultsPane = new JPanel();
		areaAndResultsPane.setLayout(new BoxLayout(areaAndResultsPane, BoxLayout.PAGE_AXIS));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 3));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		mainPanel.setBackground(Color.black);

		for (int i = 0; i < 9; i++) {
			JPanel panel = new JPanel(); // creating a panel with a box like a tic tac toe board
			panel.setLayout(new GridLayout(3, 3));
			panel.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
			panel.setBackground(Color.white);
			mainPanel.add(panel);
			panels[i] = panel;
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) { // placing the button onto the board
				buttons[i][j] = new UTTTButton(i, j);
				buttons[i][j].addActionListener(this::buttonAction);
				panels[i].add(buttons[i][j]);
			}
		}
		JTextArea textArea = new JTextArea("Turn of Player with Symbol:" + Symbol.CROSS);
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));

		areaAndResultsPane.add(mainPanel);
		areaAndResultsPane.add(jLabel);
		frame.add(areaAndResultsPane);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(800, 800);// set frame size and let the game begin
	}

	public void clearButtons() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				buttons[i][j].setText("");
			}
		}
	}

	@Override
	public Move getUserMove() {
		synchronized (this) {
			UserMoveChoice = null;
			try {
				while (UserMoveChoice == null)
					this.wait(50);
				return UserMoveChoice;
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		return null;
	}

	@Override
	public void showGameOverScreen(SimulatorInterface game) {
		JOptionPane.showMessageDialog(null, game.getWinner().toString() + " has won the game.", "Game Over", 0);
	}

	@Override
	public void updateScreen(SimulatorInterface game) {
		BoardInterface[] boards = game.getBoards();
		for (int boardIndex = 0; boardIndex < 9; boardIndex++) {
			panels[boardIndex].setBorder(BorderFactory.createLineBorder(Color.gray, 5));
		}
		int boardIndex = game.getIndexNextBoard();
		if (boardIndex == -1) {
			for (boardIndex = 0; boardIndex < 9; boardIndex++) {
				panels[boardIndex].setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
			}
		} else {
			panels[boardIndex].setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
		}
		int j = -1;
		for (int i = 0; i < boards.length; i++) {
			BoardInterface b = boards[i];
			MarkInterface[] pieces = b.getMarks();
			for (MarkInterface p : pieces) {
				j = p.getPosition();
				String symbol = p.getSymbol().toString();
				this.buttons[i][j].setText(symbol);

			}
			if (b.isClosed()) {
				panels[i].setBorder(BorderFactory.createLineBorder(Color.gray, 5));
			}
		}
		currentPlayer = game.getCurrentPlayerSymbol();
		jLabel.setText(label + currentPlayer);

	}

	@Override
	public String getUserInput(String Question, String[] PossibleAnswers) {
		while (true) {
			String Answer = JOptionPane.showInputDialog(Question, "");

			for (String PossibleAnswer : PossibleAnswers)
				if (PossibleAnswer.equals(Answer))
					return Answer;
		}
	}

	public void buttonAction(ActionEvent e) {
		UTTTButton source = (UTTTButton) e.getSource();
		UserMoveChoice = source.getMove();

	}
}
