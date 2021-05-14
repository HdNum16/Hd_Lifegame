package lifegame;

import javax.swing.SwingUtilities; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class Main implements Runnable {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}
	
	public void run() {
		BoardModel model = new BoardModel(12, 12);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(300, 200));
		
		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		base.add(view, BorderLayout.CENTER);
		model.addListener(view);
		
		frame.pack();
		frame.setVisible(true);
		frame.setTitle("Lifegame");
		
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
		
		JButton nextButton = new JButton("Next");
		buttonPanel.add(nextButton);
		
		JButton undoButton = new JButton("Undo");
		buttonPanel.add(undoButton);
		undoButton.setEnabled(false);
		
		JButton newgameButton = new JButton("New game");
		buttonPanel.add(newgameButton);
		
		JButton autoButton = new JButton("Auto");
		buttonPanel.add(autoButton);
		
		ButtonEvents addActionListener = new ButtonEvents(model);
		
		frame.addWindowListener(addActionListener);
		
		nextButton.addActionListener(addActionListener);
		undoButton.addActionListener(addActionListener);
		newgameButton.addActionListener(addActionListener);
		autoButton.addActionListener(addActionListener);
		
		UndoButtonSetup undoButtonSetup = new UndoButtonSetup(undoButton);
		model.addListener(undoButtonSetup);
		
		
		JMenuBar menubar = new JMenuBar();
		JMenu FileMenu = new JMenu("File");
		JMenu SizeMenu = new JMenu("Size");
		JMenu viewMenu = new JMenu("Graph");
		
		frame.setJMenuBar(menubar);
		menubar.add(FileMenu);
		menubar.add(SizeMenu);
		menubar.add(viewMenu);
		
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveItem = new JMenuItem("Save as");
		JMenuItem resetItem = new JMenuItem("Reset");
		
		FileMenu.add(openItem);
		FileMenu.add(saveItem);
		FileMenu.add(resetItem);
		
		MenuItemEvents addItemActionListener = new MenuItemEvents(model);
		
		openItem.addActionListener(addItemActionListener);
		saveItem.addActionListener(addItemActionListener);
		resetItem.addActionListener(addItemActionListener);
		

		JScrollBar scrollbar = new JScrollBar(JScrollBar.VERTICAL, 12, 1, 4, 40);
		scrollbar.setPreferredSize(new Dimension(20, 120));
		SizeMenu.add(scrollbar);
		
		AdjustmentEvents addAdjustmentListener = new AdjustmentEvents(model);
		
		scrollbar.addAdjustmentListener(addAdjustmentListener);
		
		
		JPanel graphPanel = new JPanel();
		graphPanel.setPreferredSize(new Dimension(300, 200));
		graphPanel.setLayout(new BorderLayout());
		GraphView graphview = new GraphView(model);
		graphPanel.add(graphview, BorderLayout.CENTER);
		model.addListener(graphview);
		viewMenu.add(graphPanel);
	}
}
