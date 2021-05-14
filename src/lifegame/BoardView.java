package lifegame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BoardView extends JPanel implements BoardListener, MouseListener, MouseMotionListener {
	private BoardModel model;
	private int CELL_LENGTH;
	private int FROM_LEFT;
	private int FROM_TOP;
	private int[] previousMousePoint = {-1, -1};
	
	public BoardView(BoardModel model) {
		this.model = model;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int cols = model.getCols();
		int rows = model.getRows();
		int WIDTH = this.getWidth() - 2;
		int HEIGHT = this.getHeight() - 2;
		
		CELL_LENGTH = ((int) WIDTH / cols > (int) HEIGHT / rows)? (int) HEIGHT / rows : (int) WIDTH / cols;
		FROM_LEFT = 1 + (int) (WIDTH - CELL_LENGTH * cols) / 2;
		FROM_TOP = 1 + (int) (HEIGHT - CELL_LENGTH * rows) / 2;
		
		for (int x = FROM_LEFT; x <= FROM_LEFT + CELL_LENGTH * cols; x = x + CELL_LENGTH) {
			g.drawLine(x, FROM_TOP, x, FROM_TOP + CELL_LENGTH * rows); 
		}
		for (int y = FROM_TOP; y <= FROM_TOP + CELL_LENGTH * rows; y = y + CELL_LENGTH) {
			g.drawLine(FROM_LEFT, y, FROM_LEFT + CELL_LENGTH * cols, y); 
		}
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (model.isAlive(x, y)) g.fillRect(FROM_LEFT + x * CELL_LENGTH, FROM_TOP + y * CELL_LENGTH, CELL_LENGTH, CELL_LENGTH);
			}
		}
	}
	
	private void changeCS(int x, int y) {
		if (x < model.getCols() && y < model.getRows()) {
			int[] p = {x, y};
			if (p[0] == previousMousePoint[0] && p[1] == previousMousePoint[1]) return;
			previousMousePoint = p;
			model.changeCellState(x, y);
		}
	}
	
	public void updated(BoardModel model) {
		super.repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		previousMousePoint[0] = -1;
		previousMousePoint[0] = -1;
		if (e.getX() >= FROM_LEFT && e.getY() >= FROM_TOP) changeCS((int) (e.getX() - FROM_LEFT) / CELL_LENGTH, (int) (e.getY() - FROM_TOP) / CELL_LENGTH);
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
		if (e.getX() >= FROM_LEFT && e.getY() >= FROM_TOP) changeCS((int) (e.getX() - FROM_LEFT) / CELL_LENGTH, (int) (e.getY() - FROM_TOP) / CELL_LENGTH);
	}
	public void mouseMoved(MouseEvent e) {
	}
}
