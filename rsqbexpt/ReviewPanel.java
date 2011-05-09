package rsqbexpt;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;


public class ReviewPanel extends ExptPanel {
	private Vector<JLabel> lvect;
	private int _totalqs, _randdelay;
	private JButton _rollbutton,_runLottery;
	public ReviewPanel(Dimension d, ExptFrame ef){
		super(d,ef);

		//set up the random delay which will later select the lottery
		double randdub = new java.util.Random().nextDouble()*20+10;
		_randdelay = (int) randdub;
		//store the totalqs
		_totalqs = _frame._totalquestions;
		//instantiate the lvect
		lvect = new Vector<JLabel>();
		JPanel reviewpanel = new JPanel();
		int cols = (int)java.lang.Math.ceil(java.lang.Math.sqrt(_totalqs));
		//create a jbutton vector
		Vector<JLabel> v = new Vector<JLabel>();
		GridLayout gl = new GridLayout(0,cols,15,15);
		reviewpanel.setLayout(gl);
		for (int i=0;i<_totalqs;i++){
			Question question = _frame.getUser().getQuestionAtIndex(i);
			int lotteryid = question._response;
			String html = _frame.getScribe().getHTMLforLotID(lotteryid);
			int qnum = i+1;
			JLabel revlabel = new JLabel("<html> Question "+qnum+" <br /> "+html+"</html>");
			//make sure it is not bold
			revlabel.setFont(new Font(revlabel.getFont().getName(),Font.PLAIN,revlabel.getFont().getSize()));
			revlabel.setOpaque(true);
			lvect.add(revlabel);
			reviewpanel.add(revlabel);
		}
		_middlepanel.add(reviewpanel);

		//add the "roll" button
		_rollbutton = new JButton("roll");
		_rollbutton.addActionListener(new RollListener());
		_southpanel.add(_rollbutton);

		//add the "run lottery" button
		_runLottery = new JButton("participate in lottery");
		_runLottery.addActionListener(new prepupAL());
		_runLottery.setVisible(false);
		_southpanel.add(_runLottery);




	}

	private class RollListener implements ActionListener{
		private int delay=10;
		private Timer _t;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			_rollbutton.setVisible(false);


			ActionListener taskPerformer = new RollListener() {
				int counter = 0;

				public void actionPerformed(ActionEvent evt) {
					Font oldLabelFont=new Font(lvect.get(counter).getFont().getName(),Font.PLAIN,lvect.get(counter).getFont().getSize());  
					if(counter!=0)
						lvect.get(counter-1).setFont(oldLabelFont);
					else
						lvect.get(_totalqs-1).setFont(oldLabelFont);
					Font newLabelFont=new Font(lvect.get(counter).getFont().getName(),Font.BOLD,lvect.get(counter).getFont().getSize());  
					lvect.get(counter).setFont(newLabelFont);
					if(counter <_totalqs-1)
						counter++;
					else
						counter=0;
					if(delay >600){
						if(counter!=0){
							lvect.get(counter-1).setBackground(java.awt.Color.YELLOW);
							_frame._roll=(counter-1);
						}
						else{
							lvect.get(_totalqs-1).setBackground(java.awt.Color.YELLOW);
							_frame._roll=(_totalqs-1);
							
						}
						_runLottery.setVisible(true);
						_t.stop();
					}
					else{
						delay = delay+_randdelay;
						_t.setDelay(delay);
					}


				}
			};	
			_t = new Timer(delay,taskPerformer);
			_t.start();


		}


	}


	private void roll() {
		for(int i=0;i<lvect.size();i++){

		}

	}

	private class prepupAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			_frame.switchToRunLottery();
		}

	}


}

