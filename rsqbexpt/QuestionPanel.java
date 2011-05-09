package rsqbexpt;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class QuestionPanel extends ExptPanel {
	private Question _question;
	int _index;
	private Vector<JRadioButton> _options;

	public QuestionPanel(Dimension d, ExptFrame ef,int qstindx) {
		super(d,ef);
		_question = _frame.getUser().getQuestionAtIndex(qstindx);
		_index = qstindx;


		//do whatever is necessary to set up a status quo
		
		_c.ipadx = 0;
		_c.fill = GridBagConstraints.HORIZONTAL;
		JLabel qnumlabel = new JLabel("<html><h1>Question "+(_index+1)+" of "+_frame._totalquestions+"</h1></html>");
		_c.gridx=0;
		_c.gridy=0;
		_c.anchor = GridBagConstraints.CENTER;
		_middlepanel.add(qnumlabel,_c);
		

		//get the number of options for question at index
		int numOps = _question._lotteries.size();

		//create a vector for the radio buttons
		_options = new Vector<JRadioButton>(); 

		//create path to one to select so that we can select it after making the buttongroup
		JRadioButton selected = null;
		for (int i=0; i<numOps;i++){
			int lotteryNumber = _frame.getScribe().getLotteryNumber(_question.number, i);
			String htmlstring = _frame.getScribe().getHTMLforLotID(_question._lotteries.get(i));
			JRadioButton tempbutton = new JRadioButton("<html>"+htmlstring+"</html>");
			if (lotteryNumber == _question._sq){// we found the status quo!
				switch(_question._sqtype){
				case 0:
					//do nothing
					break;
				case 1:
					//this will just have a suggested option on the top pane
					_northpanel.add(new JLabel("<html>"+htmlstring+"</html>"));
					break;
				case 2:
					//this will just pre-select the status quo option
					selected=tempbutton;
					_question._response = _question._sq;

					break;
				case 3:
					//this will put the SQ above AND pre-select
					selected=tempbutton;
					_northpanel.add(new JLabel("<html>"+htmlstring+"</html"));
					_question._response = _question._sq;
					break;
					
				}
			}
			

			tempbutton.addActionListener(new lradioAL(lotteryNumber));
			_options.add(i,tempbutton);
		}
		//add the labels to a button group
		ButtonGroup bgroup = new ButtonGroup();
		_c.insets = new Insets(3,20,20, 3);
		for (int i=0;i<numOps;i++){
			bgroup.add(_options.get(i));
			_c.gridx=0;
			_c.gridy=i+2;
			_middlepanel.add(_options.get(i),_c);
		}
		
		//select if there is an SQ
		if(selected!=null)
			selected.setSelected(true);
		
		
		
		//add the "next" button with the right text
		JButton next=null;
		if(_frame.testFinalQuestion(qstindx))
			next = new JButton("submit and review all selections");
		else
			next = new JButton("submit and move to next");
		
		next.addActionListener(new nextQuestionAL());
		JPanel wrap = new JPanel();
		_c.gridx=0;
		_c.gridy=30;
		wrap.add(next);
		_middlepanel.add(wrap,_c);
	
	
	
	}
		

//	/* seperate method just to keep code easy to find and manipulate */
//	private void setStatusQuo() {
//		switch(_question._sqtype){
//		case 0:
//			//do nothing because there is no status quo;
//			break;
//		case 1:
//			//this will just have a suggested option in the top pane
//			String s1 = _frame.getScribe().getHTMLforLotID(_question._sq);
//			_northpanel.add(new JLabel("<html>"+s1+"</html>"));
//			break;
//		case 2:
//			//this will pre-select the status quo option
//			String s2 = _frame.getScribe().getHTMLforLotID(_question._sq);
//			for (int i=0;i<_options.size();i++)
//				if (_options.get(i).getText().equals("<html>"+s2+"</html>")){
//
//					_options.get(i).setSelected(true);
//					_question._response = _question._sq;
//				}
//			break;
//		case 3:
//			//this will both put the status quo above AND select the option
//			String s3 = _frame.getScribe().getHTMLforLotID(_question._sq);
//			//softFIX (just take the strings and measure against one another)
//			for (int i=0;i<_options.size();i++)
//				if (_options.get(i).getText().equals("<html>"+s3+"</html>")){
//					if (_options.get(i).getText().equals("lottery "+_question._sq)){
//
//					_northpanel.add(new JLabel("<html>"+s3+"</html>"));
//
//					_options.get(i).setSelected(true);
//					_question._response = _question._sq;
//					}
//					break;
//				}
//		}
//	}
	private class lradioAL implements ActionListener{
		private int id;
		public lradioAL(int i){
			id=i;
			
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			_question._response = id;
		}

	}


	private class nextQuestionAL implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(_question._response!=0){				
				//check to see if we are on the final question, if not, move on to the next question
				if (_frame.testFinalQuestion(_index))
					_frame.switchToReviewPage();	
				else
					_frame.switchToNextQuestion(_index+1);
			}
		}

	}

}
