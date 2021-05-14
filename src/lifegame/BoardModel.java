package lifegame;

import java.util.ArrayList;
import java.util.LinkedList;

public class BoardModel {
	private int cols;
	private int rows;
	private boolean[][] cells;
	private boolean[][] previousCells;
	private LinkedList<boolean[][]> linkedListCells;
	private ArrayList<BoardListener> listeners;
	
	public BoardModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows][cols];
		previousCells = new boolean[rows][cols];
		linkedListCells = new LinkedList<boolean[][]>();
		linkedListCells.add(cloneCells(cells));
		listeners = new ArrayList<BoardListener>();
	}
	
	private boolean[][] cloneCells(boolean[][] c) {
		boolean[][] clone = new boolean[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				clone[y][x] = c[y][x];
			}
		}
		return clone;
	}
	
	public void setNewModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows][cols];
		previousCells = new boolean[rows][cols];
		linkedListCells = new LinkedList<boolean[][]>();
		linkedListCells.add(cloneCells(cells));
		fireUpdate();
	}
	
	public void resetListCells() {
		previousCells = cloneCells(cells);
		linkedListCells = new LinkedList<boolean[][]>();
		linkedListCells.add(cloneCells(cells));
		fireUpdate();
	}
	
	private void addLL(boolean[][] c) {
		if (linkedListCells.size() > 32) {
			linkedListCells.removeFirst();
		}
		
		linkedListCells.add(c);
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}
	
	public void fireUpdate() {
		for (BoardListener listener: listeners) {
			listener.updated(this);
		}
	}
	
	public void printForDebug() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (cells[y][x]) System.out.print('*');
				else System.out.print('.');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void changeCellState(int x, int y) {
		previousCells = cloneCells(cells);
		cells[y][x] = !cells[y][x];
		addLL(cloneCells(cells));
		fireUpdate();
	}
	
	private int sumAround(int x, int y) {
		int sum = 0;
		for (int dy = -1; dy <= 1; dy++) {
			for (int dx = -1; dx <= 1; dx++) {
				if (y+dy >= 0 && y+dy < rows && x+dx >= 0 && x+dx < cols) {
					if (previousCells[y+dy][x+dx]) sum++;
				}
			}
		}
		if (previousCells[y][x]) sum -= 1;
		return sum;
	}
	
	public synchronized void next() {
		previousCells = cloneCells(cells);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				int s = sumAround(x, y);
				if ((cells[y][x] && s != 2 && s != 3) || (!cells[y][x] && s == 3)) {
					cells[y][x] = !cells[y][x];
				}
			}
		}
		addLL(cloneCells(cells));
		fireUpdate();
	}
	
	public boolean isUndoable() {
		return linkedListCells.size() > 1;
	}
	
	public void undo() {
		if (isUndoable()) {
			cells = cloneCells(previousCells);
			linkedListCells.removeLast();
			if (linkedListCells.size() > 1) previousCells = cloneCells(linkedListCells.get(linkedListCells.size() - 2));
			fireUpdate();
		}
	}
	
	public boolean isAlive(int x, int y) {
		return cells[y][x];
	}
	
	public int NumOfLivingCells() {
		int lc = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (isAlive(x, y)) lc++;
			}
		}
		return lc;
	}
}
