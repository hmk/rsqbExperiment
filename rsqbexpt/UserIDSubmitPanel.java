package rsqbexpt;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class UserIDSubmitPanel extends ExptPanel {
	private JTextField _idfield;
	private JLabel _errorlabel;
	
	public UserIDSubmitPanel(Dimension d, ExptFrame ef) {
		super(d,ef);
		
		//add components
		_c.insets = new Insets(3,20,20, 3);

		
		//add components for the middle panel
		_idfield = new JTextField("User ID",5){
			@Override public void setBorder(Border border) {
				// No! Don't set a damn border
			}
		};
		JButton subidbtn = new JButton("submit");
		subidbtn.addActionListener(new submitIDActionListener());
		_c.gridx=0;
		_c.gridy=0;
		_middlepanel.add(_idfield,_c);
		_c.gridx=0;
		_c.gridy=1;
		_middlepanel.add(subidbtn,_c);
		
		//add the focus listener
		_idfield.addFocusListener(new IDFocus());

		
		//add components for the south panel
		_errorlabel = new JLabel("user ID not recognized");
		_errorlabel.setVisible(false);
		_errorlabel.setForeground(java.awt.Color.red);
		_c.gridx=0;
		_c.gridy=3;
		_c.anchor = GridBagConstraints.CENTER;
		_southpanel.add(_errorlabel,_c);

	}
	
	private class submitIDActionListener implements ActionListener{
				@Override
		public void actionPerformed(ActionEvent a) {
			String s = _idfield.getText();
			if(_frame.checkForValidID(_idfield.getText())){
				_frame.createUser(_idfield.getText());
				_frame.switchToUserConfirmPanel();
			}
			else {
				_errorlabel.setVisible(true);
			}
		}
		
	}
	private class IDFocus implements FocusListener{
		public void focusGained(FocusEvent e){
			if(e.getSource() == _idfield){
				_idfield.setText("");
				}
		}
		public void focusLost(FocusEvent e){
		}		
	}
}
