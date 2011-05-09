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
		
		//TODO: delete (this is for testing only)
		for (int j=0;j<_qarray.length;j++){
			int qindex = _qarray[j]._index;
			int qnumber = _qarray[j].number;
			System.out.println("question at index "+j+" reads as having an index of "+qindex+
					"and is question number "+qnumber);
		}
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
//
//	//REMEBER TO SET TOTAL QUESTIONS
//	int treatment = _frame.getScribe().getTreatmentForUserID(_userid);
//	int totalq = _frame.getScribe().getTotalQsForTreatment(treatment);
//	
//	//create the array that will hold the questions
//	Question[] qarray = _frame.getScribe().getQArrayForTreatment(treatment);
//	//create a vector that will temporarily hold them until we write them to the array
//	Vector<Question> questions = new Vector<Question>();
//	
//	for (int i=1;i<toq+2-fromq; i++){
//		Vector<Integer> options = new Vector<Integer>();
//		for (int j=0;j<_frame.getScribe().getNumQOps(i);j++){
//			options.add(_frame.getScribe().getLotteryNumber(i,j));
//		}
//		
//		Question tempquest = new Question(
//				i, //the question number
//				options, //the vector of options
//				_frame.getScribe().getStatusQuoType(i),
//				_frame.getScribe().getStatusQuoOption(i));
//		questions.add(tempquest);
//		
//	}
//	Collections.shuffle(questions);
//	for (int j=0;j<totalq;j++){
//		qarray[j]=questions.get(j);
//	}
//	return qarray;
}
