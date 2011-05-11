package rsqbexpt;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FailurePanel extends ExptPanel {

	public FailurePanel(Dimension d, ExptFrame ef) {
		super(d, ef);
		//oh no! there was a problem with writing the responses
		_northpanel.add(new JLabel("there was a problem uploading to the server!!!!! responses are given below and were printed to console"));
		ExptUser user = _frame.getUser();
		int numqs = user.getNumberOfQuestions();
		for(int i=0;i<numqs;i++){
			int q = user.getQuestionAtIndex(i).number;
			int r = user.getQuestionAtIndex(i)._response;
			System.out.println("question "+q+" response "+r);
			JLabel templabel = new JLabel("question "+q+" response "+r);
			_c.gridx=0;
			_c.gridy=i;
			_middlepanel.add(templabel,_c);
		}
		
		JPanel wrap = new JPanel();
		JButton finished = new JButton("emergency transcription completed");
		finished.addActionListener(new FinishedAL());
		wrap.add(finished);
		_southpanel.add(wrap);
	}
	
	private class FinishedAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			_frame.switchToFinalPanel();
			
		}
		
	}

	
}
