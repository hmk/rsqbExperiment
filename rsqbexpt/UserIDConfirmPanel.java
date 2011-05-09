package rsqbexpt;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class UserIDConfirmPanel extends ExptPanel {
	public UserIDConfirmPanel(Dimension d, ExptFrame ef) {
		super(d,ef);
		
		
		//create textlabel
		JLabel textlabel = new JLabel("<html><br /><h1>User ID: "+ef.getUser().getID()+"</h1><br /><center> is this correct? </center><br /><br /> </html>");
		textlabel.setVisible(true); 
		//confirmation button
		JButton confbtn = new JButton("yes");
		confbtn.addActionListener(new confirmIDActionListener());
		//rejection button
		JButton rejbtn = new JButton("no");
		rejbtn.addActionListener(new rejectIDActionListener());
		
		_c.gridx = 0;
		_c.gridy = 1;
		_middlepanel.add(textlabel,_c);
		_c.gridx = 0;
		_c.gridy = 2;
		JPanel wrap = new JPanel();
		wrap.add(confbtn);
		_middlepanel.add(wrap,_c);
		_c.gridx = 0;
		_c.gridy = 3;
		_middlepanel.add(new JLabel("<html><br /> <br /></html>"),_c);//spacer
		_c.gridx = 0;
		_c.gridy = 4;
		JPanel wrap2 = new JPanel();
		wrap2.add(rejbtn);
		_middlepanel.add(wrap2,_c);


	}
	
	private class confirmIDActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			_frame.switchToFirstQuestion();
		}		
	}
	
	private class rejectIDActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			_frame.switchToUserIDSubmitPanel();
		}		
	}

}
