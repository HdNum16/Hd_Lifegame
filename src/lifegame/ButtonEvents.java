package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;


public class ButtonEvents implements ActionListener, WindowListener {
	private BoardModel model;
	private SubThread th;
	public ButtonEvents(BoardModel model) {
		this.model = model;
		th = new SubThread(model);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Next")) {
			model.next();
		}
		
		else if (ae.getActionCommand().equals("Undo")) {
			model.undo();
		}
		
		else if (ae.getActionCommand().equals("New game")) {
			SwingUtilities.invokeLater(new Main());
		}
		
		else if (ae.getActionCommand().equals("Auto")) {
			if (th.isAlive()) {
				th.interrupt();
			}
			else {
				th = new SubThread(model);
				th.start();
			}
		}
	}
	
	public void windowOpened(WindowEvent e) {
	}
	public void windowClosing(WindowEvent e) {
		th.interrupt();
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}
}
