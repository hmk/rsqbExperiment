package rsqbexpt;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class PrepUploadPanel extends ExptPanel {
	private JTextField _textfield;
	private JLabel _errorlabel;

	public PrepUploadPanel(Dimension d, ExptApplet ef,String winamount){
		super(d,ef);
		
		//create a label for the winnings
		JLabel winlabel = new JLabel("You won: "+winamount);
		_northpanel.add(winlabel);
		
		//add components
		_textfield = new JTextField("ask a proctor for password: ",15);
		_textfield.addFocusListener(new TextFocus());
		_errorlabel = new JLabel("sorry, that is not the correct password, \n please ask a proctor for assistance");
		_errorlabel.setVisible(false); //only show up after invalid input
		JButton subidbtn = new JButton("submit");
		subidbtn.addActionListener(new SubmitUploadListener());
		_c.gridx=0;
		_c.gridy=1;
		_middlepanel.add(_textfield,_c);
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
			String s = _textfield.getText();
			if(s.equals("ROBINSON303C")){
				_errorlabel.setText("prepping upload");
				_errorlabel.setVisible(true);
				ExptUser user = _frame.getUser();
				int numqs = user.getNumberOfQuestions();
				ExcelScribe scribe = _frame.getScribe();
				try {
					scribe.writeResponses(user);
					_errorlabel.setText("upload success!");
					_frame.switchToFinalPanel();

				} catch (Throwable t) {
					_frame.switchToFailurePanel();
					_errorlabel.setText("upload failure!");
				}


			}
		}

	}
	private class TextFocus implements FocusListener{
		public void focusGained(FocusEvent e){
			if(e.getSource() == _textfield){
				_textfield.setText("");
				}
		}
		public void focusLost(FocusEvent e){
		}		
	}
	private class DialogListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
}
