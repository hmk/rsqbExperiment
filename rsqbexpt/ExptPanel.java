package rsqbexpt;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public abstract class ExptPanel extends JPanel {
	protected ExptFrame _frame;
	protected JPanel _middlepanel,_northpanel,_southpanel;
	protected GridBagConstraints _c;
	
	public ExptPanel(Dimension d, ExptFrame ef){
		super();
		//init visuals
		this.setSize(d);
		this.setPreferredSize(d);
		this.setVisible(true);
		
		//store instance of frame for subclasses
		_frame = ef;
		
		//set layout
		this.setLayout(new java.awt.BorderLayout());
		
		//set up GridBagConstraints
		_c = new GridBagConstraints();
		_c.ipadx = 0;
		_c.fill = GridBagConstraints.HORIZONTAL;
		
		//set up middle panel
		_middlepanel = new JPanel();
		_middlepanel.setVisible(true);
		_middlepanel.setLayout(new GridBagLayout());
		this.add(_middlepanel, java.awt.BorderLayout.CENTER);
		
		//set up the south panel
		_southpanel = new JPanel();
		_southpanel.setVisible(true);
		_southpanel.setLayout(new GridBagLayout());
		_southpanel.setSize(new java.awt.Dimension(0,80));
		_southpanel.setPreferredSize(new java.awt.Dimension(0,80));
//		_southpanel.setBackground(java.awt.Color.GREEN);//TODO: remove
		this.add(_southpanel, java.awt.BorderLayout.SOUTH);
		
		//set up the south panel
		_northpanel = new JPanel();
		_northpanel.setVisible(true);
		_northpanel.setLayout(new GridBagLayout());
		_northpanel.setSize(new java.awt.Dimension(0,80));
		_northpanel.setPreferredSize(new java.awt.Dimension(0,80));
//		_northpanel.setBackground(java.awt.Color.ORANGE);//TODO:remove
		this.add(_northpanel, java.awt.BorderLayout.NORTH);

	}
}
