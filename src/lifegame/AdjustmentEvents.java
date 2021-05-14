package lifegame;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;

public class AdjustmentEvents implements AdjustmentListener{
	private BoardModel model;
	public AdjustmentEvents(BoardModel model) {
		this.model = model;
	}
	
	public void adjustmentValueChanged(AdjustmentEvent ae) {
		JScrollBar sb = (JScrollBar) ae.getAdjustable();
		model.setNewModel(sb.getValue(), sb.getValue());
	}
}
