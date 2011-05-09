package rsqbexpt;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;


public class ExptFrame extends JFrame {
	private int FRAME_HEIGHT=10, FRAME_WIDTH=10;
	public int _totalquestions,_fromquestion,_toquestion, _roll;
	private int MIN_USER_ID=1, MAX_USER_ID=50;
	private java.awt.Dimension _dimension;
	private JPanel _currentPanel;
	private ExptUser _user;
	private ExcelScribe _scribe;
	
	
	public ExptFrame(){
		super();
		this.setUndecorated(true);
		//set Dimensions
		_dimension = new java.awt.Dimension(FRAME_HEIGHT,FRAME_WIDTH);
		this.setPreferredSize(_dimension);
		this.setSize(_dimension);

		//create and add the first panel
		this.addFirstPanel();
		
		//finally, set up the scribe
		_scribe = new ExcelScribe();
		_fromquestion = _scribe.getFromQuestion();
		_toquestion = _scribe.getToQuestion();
	
	}




/*
 * Switch Panel Methods 
 * 
 * 
 */
	private void addFirstPanel() {
		FirstPanel fp = new FirstPanel(_dimension, this);
		_currentPanel = fp;
		this.add(fp);
		
	}
	
	public void switchToUserIDSubmitPanel() {
		UserIDSubmitPanel up = new UserIDSubmitPanel(_dimension,this);
		_currentPanel.setVisible(false);
		_currentPanel = up;
		this.add(up);
	}

	public void switchToUserConfirmPanel() {
		UserIDConfirmPanel cp = new UserIDConfirmPanel(_dimension,this);
		_currentPanel.setVisible(false);
		_currentPanel = cp;
		this.add(cp);		
	}
	public void switchToFirstQuestion() {
		QuestionPanel first = new QuestionPanel(_dimension, this,0);
		_currentPanel.setVisible(false);
		_currentPanel = first;
		this.add(first);
		
	}
	public void switchToNextQuestion(int i) {
		QuestionPanel nextq = new QuestionPanel(_dimension,this,i);
		_currentPanel.setVisible(false);
		_currentPanel = nextq;
		this.add(nextq);	
	}
	
public void switchToReviewPage() {
		ReviewPanel rp = new ReviewPanel(_dimension,this);
		_currentPanel.setVisible(false);
		_currentPanel = rp;
		this.add(rp);
	}

public void returnToQuestion(int _qid) {
	//TODO: need to have this actually use the selected :)
	QuestionPanel backtoq = new ReviewQuestionPanel(_dimension, this, _qid,1);	
	_currentPanel.setVisible(false);
	_currentPanel = backtoq;
	this.add(backtoq);

}


public void switchToRunLottery() {
	RunLotteryPanel rlp = new RunLotteryPanel(_dimension,this);
	_currentPanel.setVisible(false);
	_currentPanel=rlp;
	this.add(rlp);
}

public void switchToPrepUpload(String winamount) {
	PrepUploadPanel pup = new PrepUploadPanel(_dimension, this, winamount);
	_currentPanel.setVisible(false);
	_currentPanel=pup;
	this.add(pup);
}

	
/*
 * Helper Methods
 * 
 */
	/**
	 * checks a string to see if it's valid
	 */
	public boolean checkForValidID(String s) {
		int i;
		try{
		i = Integer.parseInt(s);
		}catch(NumberFormatException e){
			return false;
		}
		if (i<=MAX_USER_ID && i>=MIN_USER_ID)
			return true;
		else
			return false;
	}

	/**
	 * create a user from a string
	 * the user creates its own question and answer arrays
	 * 
	 * @param text
	 */
	public void createUser(String text) {
		_user = new ExptUser(this,text);
	}
	
/*
 *Accessor Methods 
 */
	/**
	 * returns the user
	 * 
	 * @return
	 */
	public ExptUser getUser(){
		return _user;
	}
	/**
	 * returns the scribe
	 * 
	 * @return
	 */
	public ExcelScribe getScribe(){
		return _scribe;
	}
	/** tests if we are on the final question **/
	public boolean testFinalQuestion(int currindex) {
		if(currindex+1==_totalquestions)
			return true;
		else
			return false;
	}













	



	

}
