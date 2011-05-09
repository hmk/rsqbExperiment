package rsqbexpt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;


public class ExptUser {
	private Question[] _qarray;
	private int _userid;
	private ExptFrame _frame;

	public ExptUser(ExptFrame ef, String s){
		_frame = ef;
		int tempUID = 0;
		try{
		tempUID = Integer.parseInt(s);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		_userid = tempUID;
		int treatment = _frame.getScribe().getTreatmentForUserID(_userid);
		int totalq = _frame.getScribe().getTotalQsForTreatment(treatment);
		_frame._totalquestions = totalq;
		_qarray = _frame.getScribe().getQArrayForTreatment(treatment);
		
	}

	/*
	 * accessor methods
	 */
	public int getID(){
		return _userid;
	}
	public Question getQuestionAtIndex(int i){
		return _qarray[i];
	}
	public int getNumberOfQuestions(){
		return _qarray.length;
	}

}
