package rsqbexpt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;


public class ExptUser {
	private Question[] _qarray;
	private int _userid;
	private ExptFrame _frame;

	public ExptUser(ExptFrame ef, String s, int totalq, int fromq, int toq){
		_frame = ef;
		int i = 0;
		try{
		i = Integer.parseInt(s);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		_userid = i;
		
		_qarray = this.generateRandomQuestionArray(totalq,fromq,toq);
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
//	public int getQuestionNumberAtIndex(int qindex){
//		return _qarray[qindex];
//	}
//	
//	public int getResponseAtIndex(int rindex){
//		return _rarray[rindex];
//	}
//	
//	public void writeResponseToIndex(int rindex,int response){
//		_rarray[rindex]=response;
//	}
//	public int[] getResponseArray(){
//		return _rarray;
//	}
//	public int[] getQuestionArray(){
//		return _qarray;
//	}

	private Question[] generateRandomQuestionArray(int totalq, int fromq, int toq) {
		Question[] qarray = new Question[totalq];
		Vector<Question> questions = new Vector<Question>();
		
		for (int i=1;i<toq+2-fromq; i++){
			Vector<Integer> options = new Vector<Integer>();
			for (int j=0;j<_frame.getScribe().getNumQOps(i);j++){
				options.add(_frame.getScribe().getLotteryNumber(i,j));
			}
			
			Question tempquest = new Question(
					i, //the question number
					options, //the vector of options
					_frame.getScribe().getStatusQuoType(i),
					_frame.getScribe().getStatusQuoOption(i));
			questions.add(tempquest);
			
		}
		Collections.shuffle(questions);
		for (int j=0;j<totalq;j++){
			qarray[j]=questions.get(j);
		}
		return qarray;
	}
}
