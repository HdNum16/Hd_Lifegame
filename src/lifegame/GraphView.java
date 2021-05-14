package lifegame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GraphView extends JPanel implements BoardListener{
	private ArrayList<Integer> Data;
	private BoardModel model;
	public GraphView(BoardModel model) {
		this.model = model;
		Data = new ArrayList<Integer>();
		Data.add(model.NumOfLivingCells());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int WIDTH = this.getWidth();
		int HEIGHT = this.getHeight();
		int EX = (int) WIDTH / 12;
		int EY = 32;
		int DATA_SIZE = Data.size();
		int MAX_DATA = 0;
		
		for (int i = 0; i < 12 && i < DATA_SIZE; i++) {
			if (Data.get(DATA_SIZE - 1 - i) > MAX_DATA) MAX_DATA = Data.get(DATA_SIZE - 1 - i);
		}
		
		if (MAX_DATA > 0) while ((int) HEIGHT / (EY * MAX_DATA) == 0 && EY > 2) {
			EY /= 2;
		}
		
		for (int i = 1; i < 13 && i < DATA_SIZE; i++) {
			g.drawLine(WIDTH - (i-1) * EX - 2, HEIGHT - Data.get(DATA_SIZE - i) * EY - 2, WIDTH - i * EX - 2, HEIGHT - Data.get(DATA_SIZE - 1 - i) * EY - 2);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke bs1 = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[] {1}, 0);
		g2.setStroke(bs1);
		
		for (int y = HEIGHT - 2; y > 0; y -= EY) {
			g.drawLine(0, y, WIDTH - 1, y);
		}
		
		BasicStroke bs2 = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(bs2);
		
		for (int i = 0; i < 13 && i < DATA_SIZE; i++) {
			g.drawLine(WIDTH - i * EX - 2, HEIGHT - Data.get(DATA_SIZE - 1 - i) * EY - 2, WIDTH - i * EX - 2, HEIGHT - Data.get(DATA_SIZE - 1 - i) * EY - 2);
			g.drawString(Data.get(DATA_SIZE - 1 - i).toString(), WIDTH - i * EX - 6, HEIGHT - Data.get(DATA_SIZE - 1 - i) * EY - 7);
		}
	}
	
	public void updated(BoardModel model) {
		Data.add(model.NumOfLivingCells());
		super.repaint();
	}
}
