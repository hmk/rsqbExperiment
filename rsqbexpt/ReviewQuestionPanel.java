package rsqbexpt;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ReviewQuestionPanel extends QuestionPanel {

	public ReviewQuestionPanel(Dimension d, ExptFrame ef, int qstindx, int selectedIndex) {
		super(d, ef, qstindx);
		this.setVisible(true);
		this.addReturnReviewButton();
	}
	
	public void addReturnReviewButton() {
		JPanel reviewbuttonpanel = new JPanel();
		JButton reviewbutton = new JButton("submit and review");
		reviewbuttonpanel.add(reviewbutton);
		reviewbuttonpanel.setVisible(true);
		this.add(reviewbuttonpanel, BorderLayout.SOUTH);	
	}
	
	

}
