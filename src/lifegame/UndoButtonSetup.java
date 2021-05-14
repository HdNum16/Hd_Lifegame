package lifegame;

import javax.swing.JButton;

public class UndoButtonSetup implements BoardListener {
	JButton undoButton;
	public UndoButtonSetup(JButton undoButton) {
		this.undoButton = undoButton;
	}
	
	public void updated(BoardModel model) {
		undoButton.setEnabled(model.isUndoable());
	}
}
