package rsqbexpt;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinalPanel extends ExptPanel {

	public FinalPanel(Dimension d, ExptFrame ef) {
		super(d, ef);
		JLabel tylabel = new JLabel("thank you for participating!");
		JButton closebutton = new JButton("exit");
		closebutton.addActionListener(new CloseListener());
		JPanel wrap = new JPanel();
		wrap.add(closebutton);
		_c.gridy=0;
		_middlepanel.add(tylabel,_c);
		_c.gridy=1;
		_middlepanel.add(wrap,_c);
	}

	private class CloseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
}
