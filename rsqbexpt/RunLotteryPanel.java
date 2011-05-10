package rsqbexpt;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RunLotteryPanel extends ExptPanel{
	Question _q;
	JLabel _winningslabel;
	JButton _playLotButton, _finishButton;
	String winamount;

	public RunLotteryPanel(Dimension d, ExptFrame ef){
		super(d,ef);
		String htmlstring = _frame.getScribe().getHTMLforLotID(_frame.getUser().getQuestionAtIndex(_frame._roll)._response);
		JLabel instructions = new JLabel("<html>"+htmlstring+"</html>");
		_northpanel.add(instructions);

		//add a label
		_winningslabel = new JLabel("");
		JPanel wrap = new JPanel();
		wrap.add(_winningslabel);
		_c.fill = GridBagConstraints.HORIZONTAL;
		_c.gridx=0;
		_c.gridy=49;
		_c.gridwidth=2;
		_middlepanel.add(wrap,_c);
		
		//reset the grid
		_c.gridwidth =1;
		
		// get question and numops
		_q = _frame.getUser().getQuestionAtIndex(_frame._roll);
		int numops = _frame.getScribe().getNumQOps(_q.number);
		Vector<JLabel> v = new Vector<JLabel>();

		//set the prev string
		Double prev = 0.00;

		//instantiate the numbers
		for (int i=0;i<3;i++){
			String s1=_frame.getScribe().getPercent(_q._response, i+1);
			String s2=_frame.getScribe().getPercent(_q._response, i+1+3);
			if (!s1.isEmpty()){
				s1=s1.replaceAll("\\D", "");
				s1=("0."+s1);
				double s1double = Double.parseDouble(s1);
				JLabel templabel = new JLabel("If you roll between a "+prev+" and a "+(prev+s1double)+" you will win: "+s2);
				prev = s1double+prev;
				v.add(templabel);
			}
		}
		for (int i=0;i<v.size();i++){
			_c.insets = new Insets(50,50,50,50);

			_c.gridy=i+3;
			_middlepanel.add(v.get(i),_c);
		}


		//add button to participate in the lottery
		JPanel wrap2 = new JPanel();
		_playLotButton = new JButton("I am ready");
		_playLotButton.addActionListener(new PlayLotteryListener());
		_c.gridy=50;
		wrap2.add(_playLotButton);
		_middlepanel.add(wrap2,_c);

		//add the button to finish the lottery
		_finishButton = new JButton("continue");
		_finishButton.addActionListener(new ContinueListener());
		_finishButton.setVisible(false);
		_southpanel.add(_finishButton);

		_q = _frame.getUser().getQuestionAtIndex(_frame._roll);



		_winningslabel.setVisible(false);

	}

	private class PlayLotteryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			double randdub = new java.util.Random().nextDouble();
			double currdisplacement=0;
			int i=0;
			boolean foundyet = false;
			do{
				String s1=_frame.getScribe().getPercent(_q._response,i+1);
				String s2=_frame.getScribe().getPercent(_q._response, i+1+3);
				s1=s1.replaceAll("\\D", "");
				s1=("0."+s1);
				double s1double = Double.parseDouble(s1);
				if(randdub <=(s1double+currdisplacement)){
					DecimalFormat df = new DecimalFormat("0.0##");
					String newDub = df.format(randdub);
					winamount =s2;
					_winningslabel.setText("<html><center>You rolled a "+newDub+"<br />  Congratulations, you won <br /><h1>"+winamount+"</h1></center></html>");
					foundyet=true;
				}
				else
					currdisplacement=currdisplacement+s1double;
				i=i+1;
			}while (foundyet==false);
			_playLotButton.setVisible(false);
			_winningslabel.setVisible(true);
			_finishButton.setVisible(true);
		}
	}	

	private class ContinueListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			_frame.switchToPrepUpload(winamount);

		}
	}
}

