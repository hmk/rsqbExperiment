package rsqbexpt;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class PrepUploadPanel extends ExptPanel {
	private JTextField _idfield;
	private JLabel _errorlabel;

	public PrepUploadPanel(Dimension d, ExptFrame ef,String winamount){
		super(d,ef);
		
		//create a label for the winnings
		JLabel winlabel = new JLabel("You won: "+winamount);
		_northpanel.add(winlabel);
		
		//add components
		_idfield = new JTextField("ask a proctor for password: ",15);
		_errorlabel = new JLabel("sorry, that is not the correct password, \n please ask a proctor for assistance");
		_errorlabel.setVisible(false); //only show up after invalid input
		JButton subidbtn = new JButton("submit");
		subidbtn.addActionListener(new SubmitUploadListener());
		_c.gridx=0;
		_c.gridy=1;
		_middlepanel.add(_idfield,_c);
		_c.gridx=0;
		_c.gridy=2;
		JPanel subpanel = new JPanel();
		subpanel.setLayout(new FlowLayout());
		subpanel.add(subidbtn);
		_middlepanel.add(subpanel,_c);
		_southpanel.add(_errorlabel);
	}

	private class SubmitUploadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = _idfield.getText();
			if(s.equals("ROBINSON303C")){
				ExptUser user = _frame.getUser();
				int numqs = user.getNumberOfQuestions();
				ExcelScribe scribe = _frame.getScribe();
				try {
					scribe.writeResponses(user);
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BiffException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				_errorlabel.setText("upload success!");

			}
			_errorlabel.setVisible(true);

		}

	}
}
