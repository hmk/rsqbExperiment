package rsqbexpt;
import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FirstPanel extends ExptPanel {

	public FirstPanel(Dimension d, ExptApplet f){
		super(d,f);
		//store the frame
		_frame = f;
		
		JLabel instructions = new JLabel("Please ensure you have read and understand the instructions before proceeding.");
		//add components to the middle panel
		JPanel wrappanel = new JPanel();
		JButton bgnbtn = new JButton("begin experiment");
		wrappanel.add(bgnbtn);
		bgnbtn.setFocusPainted(false);
		bgnbtn.addActionListener(new bgnActionListener());
		_c.gridx=0;
		_c.gridy=0;
		_c.anchor = GridBagConstraints.CENTER;
		_middlepanel.add(instructions,_c);
		_c.gridy=1;
		_middlepanel.add(wrappanel,_c);
	}

	private class bgnActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent a) {
			_frame.switchToUserIDSubmitPanel();	
		}
		
	}
}
