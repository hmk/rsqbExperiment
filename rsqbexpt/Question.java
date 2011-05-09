package rsqbexpt;
/*Models a question with a number (corresponds to the number in the
 * excel spreadsheet). And a vector of lotteries (corresponds with 
 * the vector in the spreadsheet).
 * 
 * It also knows the status quo bias type, the status quo and the
 * response (if one has been given).
 * 
 * @author jheimark
 *
 */
import java.util.Vector;

public class Question {
	public int number,_sqtype,_sq,_response,_index;
	public Vector<Integer> _lotteries;
	
	public Question(int index,int questionnumber, Vector<Integer> lotteries, int sqtype, int sq){
		number = questionnumber;
		_index = index;
		_lotteries=lotteries;
		_sqtype = sqtype;
		_sq=sq;
		_response = 0;// init with no response
	}
}
