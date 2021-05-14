package lifegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MenuItemEvents implements ActionListener {
	private BoardModel model;
	public MenuItemEvents(BoardModel model) {
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Open")) {
			File file = new File("history01.txt");
			try {
				if (validInputFile(file)) {
					FileReader fileReader = new FileReader(file);
					int ch = fileReader.read();
					int c = 0, r = 0;
					while (ch != -1 && (char) ch != ' ') {
						c = c * 10 + (ch - 48);
						ch = fileReader.read();
					}
					
					ch = fileReader.read();
					while (ch != -1 && (char) ch != '\n') {
						r = r * 10 + (ch - 48);
						ch = fileReader.read();
					}
					
					ch = fileReader.read();
					model.setNewModel(c, r);
					
					for (int y = 0; y < r; y++) {
						for (int x = 0; x < c; x++, ch = fileReader.read()) {
							if ((char) ch == '*') model.changeCellState(x, y);
						}
						ch = fileReader.read();
					}
					
					model.resetListCells();
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (ae.getActionCommand().equals("Save as")) {
			File file = new File("history01.txt");
			try {
				FileWriter fileWriter = new FileWriter(file);
				String saveString = model.getCols() + " " + model.getRows() + "\n";
				for (int y = 0; y < model.getRows(); y++) {
					for (int x = 0; x < model.getCols(); x++) {
						if (model.isAlive(x, y)) saveString = saveString + "*";
						else saveString = saveString + ".";
					}
					saveString = saveString + "\n";
				}
				fileWriter.write(saveString);
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (ae.getActionCommand().equals("Reset")) {
			model.setNewModel(model.getCols(), model.getRows());
		}
	}
	
	public boolean validInputFile(File file) throws IOException {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
			int ch = fileReader.read();
			int c = 0, r = 0;
			while (ch != -1 && (char) ch != ' ') {
				if (ch < 48 || ch > 57) return false;
				c = c * 10 + (ch - 48);
				ch = fileReader.read();
			}
			
			ch = fileReader.read();
			while (ch != -1 && (char) ch != '\n') {
				if (ch < 48 || ch > 57) return false;
				r = r * 10 + (ch - 48);
				ch = fileReader.read();
			}
			
			if (c < 4 || c > 40 || r < 4 || r > 40) return false;
			ch = fileReader.read();
			
			for (int y = 0; y < r; y++) {
				for (int x = 0; x < c; x++, ch = fileReader.read()) {
					if ((char) ch != '*' && (char) ch != '.') return false;
				}
				ch = fileReader.read();
			}
		}  finally {
			fileReader.close();
		}
		return true;
	}
}
