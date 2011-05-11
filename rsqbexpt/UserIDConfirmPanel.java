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
	public UserIDConfirmPanel(Dimension d, ExptApplet ef) {
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
		_c.gridy = 0;
		_c.gridwidth=2;
		JPanel wrap2 = new JPanel();
		wrap2.add(textlabel);
		_middlepanel.add(wrap2,_c);
		_c.gridwidth=1;
		_c.gridx = 0;
		_c.gridy = 1;
		JPanel wrap = new JPanel();
		wrap.add(confbtn);
		_middlepanel.add(wrap,_c);
		_c.gridx = 1;
		_c.gridy = 1;
		JPanel wrap3 = new JPanel();
		wrap3.add(rejbtn);
		_middlepanel.add(wrap3,_c);


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
