package rsqbexpt;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelScribe {
	private String _questionName,_responseName, _relLocalPath, _extension,_remoteHostDomain,
					_remoteHostUsername,_remoteHostPass, _remotePath,_localBackupPath,
					_remoteBackupPath;
	public int _firstQuestionRow,_lastQuestionRow,_firstOptionColumn,_lastOptionColumn,
	_firstLotteryRow,_lastLotteryRow,_htmlLotteryColumn, _sqTypeColumn, _sqOptionColumn;
	private Workbook _questionbook;

	
	public ExcelScribe(){
		//local file location strings
		_questionName = "experimentAQuestions";
		_responseName = "experimentAResponses";
		_relLocalPath = "src/";
		_extension = ".xls";
		//ftp settings
		_remoteHostDomain = "troop10ri.com";
		_remoteHostUsername = "jakeheimark";
		_remoteHostPass = "Y4g00g!";
		_remotePath = "exptHOST/";
		_remoteBackupPath = "backup/";
		
		//download the latest version of the questions
		this.downloadQuestions();
		this.setQuestionBook();
		this.readSheetParameters();		
		
	}
	
	/** does all the work of pulling the external response sheet, 
	 * creating a local backup, writing variables to the local sheet,
	 * uploading the local sheet with a timestamp to the server (for backup purposes)
	 * and then overwriting the actual sheet on the server (so that others d/l and u/l the same)
	 * 
	 * @param id
	 * @param questions
	 * @param responses
	 * @throws IOException
	 * @throws WriteException
	 * @throws BiffException 
	 */
	public void writeResponses(ExptUser user) throws IOException, WriteException, BiffException{
		//first, we need to download the response file to the local disk
		FTPHelper ftp = new FTPHelper();
		File localFile = new File(_responseName+_extension);
		try {
			ftp.download(_remoteHostDomain, _remoteHostUsername, _remoteHostPass, 
					_remotePath+_responseName+_extension, localFile);
		} catch (MalformedURLException e) {
			System.out.println("something wrong with the URL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Error!");
			e.printStackTrace();
		}
		//now, lets create a timestamp		
		WritableWorkbook backup = null;
		java.util.Date today = new java.util.Date();
		Timestamp timestamp = new Timestamp(today.getTime());
		String timestring= timestamp.toString();
		//and sanitize the timestamp
		timestring = timestring.replaceAll(" ", "");
		timestring = timestring.replaceAll(":","");
		timestring = timestring.replaceAll("-","");
		timestring = timestring.trim().replace(".","");
		
		//grab the downloaded response file
		Workbook answerbook = Workbook.getWorkbook(localFile);
		//back it up with the timeString
		String backuppath = _responseName+timestring+_extension;
		backup = Workbook.createWorkbook(new File(backuppath),answerbook); 
		backup.write();
		backup.close();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create a copy of this thing
	    WritableWorkbook copy = Workbook.createWorkbook(new File(_responseName+_extension), Workbook.getWorkbook(localFile));
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//grab the first sheet of our copy
		WritableSheet s = copy.getSheet(0);
		
		//create the font in which we will write
		WritableFont arial12pt = new WritableFont(WritableFont.ARIAL, 12);
		//create the format
		WritableCellFormat arial = new WritableCellFormat(arial12pt);
		//setthefontwrap
			arial.setWrap(true);
	
		
		//soft write the number in the cell
		Number n;
		for (int i=0;i<user.getNumberOfQuestions();i++){
			Question question = user.getQuestionAtIndex(i);
			n = new Number(user.getID(),question.number,question._response, arial);
			//write the value in the cell you were asked to write to
				s.addCell(n);
		}
		
		//write the full sheet and close it
		copy.write();
		copy.close();
		
		//now, try uploading a backup of the file to the server
		this.uploadFile(_responseName+_extension, _remoteBackupPath+"experimentAResponses"+timestring+".xls");
		//now, try overwriting the actual file on the server
		this.uploadFile(_responseName+_extension, "experimentAResponses"+".xls");
	}
	
	/** uploads a file to the server */
	private void uploadFile(String localresponsepath, String filename) {
		FTPHelper ftp = new FTPHelper();
		try {
			ftp.upload(_remoteHostDomain, _remoteHostUsername, _remoteHostPass, _remotePath+filename, new File(localresponsepath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int getNumQOps(int q){
		int numOps = 0;
		//make sure that the question is within the sheet
		if(q<_lastQuestionRow-_firstQuestionRow+2 && q>0){
			
			
			while (this.readInt(1,_firstOptionColumn+numOps,q)!=0) {
				numOps++;
			}
		}
		return numOps;
	}
	
	/** sets up the sheet parameters */
	private void readSheetParameters() {
		_firstQuestionRow =  this.readInt(0,0,11);
		_lastQuestionRow = this.readInt(0,0,13);
		_firstOptionColumn = this.readInt(0, 0, 15);
		_lastOptionColumn = this.readInt(0,0,17);
		
		_firstLotteryRow = this.readInt(0, 0, 40);
		_lastLotteryRow = this.readInt(0, 0, 42);
		_htmlLotteryColumn = this.readInt(0, 0, 44);
		
		_sqTypeColumn = this.readInt(0, 0, 19);
		_sqOptionColumn = this.readInt(0, 0, 21);
		
	}

	/** returns the html value of the lottery with lotteryID */
	public String getHTMLforLotID(int lotteryID) {
		//now, grab the HTML for that lottery		
		//grab the correct sheet, this time we want sheet 2
		Sheet sheet = _questionbook.getSheet(2);
		Cell cell = sheet.getCell(_htmlLotteryColumn,lotteryID+_firstLotteryRow-1);
		//return the contents of that cell
		return cell.getContents();
	}
	
	/** returns the lottery number of the option i for question qnum */
	public int getLotteryNumber(int qnum, int optionnum){
		//read the lottery ID
		//we know sheet will be 1 because that is the questionlist
		int lotID = this.readInt(1, _firstOptionColumn+optionnum, qnum+_firstQuestionRow-1);
		return lotID;
	}
	
	/** returns the value of a cell (only if its an int) */
	private int readInt(int sht,int col, int row){
		String value = null;
		//grab the correct sheet
		Sheet sheet = _questionbook.getSheet(sht);
		
		//grab the cell asked for
		Cell cell = sheet.getCell(col,row);
		value = cell.getContents();
		//TODO: write catches for non-ints
		int i = 0;
		try{
			i = Integer.parseInt(value);
			return i;
		} catch(NumberFormatException e){
//			System.out.println("cell value is not an int!");//uncomment to express
		}
		
		return 0;
	}
	
	/** sets up the question workbook */
	private void setQuestionBook() {
		try {
			_questionbook = Workbook.getWorkbook(new File(_questionName+_extension));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/** downloads the questions from troop10ri.com */
	private void downloadQuestions() {
		//get the file from FTP and write it to the local disk
		FTPHelper ftp = new FTPHelper();
		try {
			ftp.download(_remoteHostDomain, _remoteHostUsername, _remoteHostPass, 
					_remotePath+_questionName+_extension, 
					new File(_questionName+_extension));
		} catch (MalformedURLException e) {
			System.out.println("something wrong with the URL!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Error!");
			e.printStackTrace();
		}
	}

	public int getTotalQs() {
		return this.readInt(0, 0, 26);
	}
	
	public int getFromQuestion(){
		return this.readInt(0, 0, 28);
	}
	
	public int getToQuestion(){
		return this.readInt(0, 0, 30);
	}

	public int getStatusQuoType(int q) {
		//check that we are in a valid question!
		if(q<_lastQuestionRow-_firstQuestionRow+2 && q>0){
		return this.readInt(1, _sqTypeColumn, q);
		}
		//TODO: else throw an error
		else return 0;
	}

	public int getStatusQuoOption(int q) {
		return this.readInt(1,_sqOptionColumn, q);
	}

	public String getPercent(int lotterynumber, int optionnumber) {
		String s = this.readString(2,_firstLotteryRow+lotterynumber-1,1+optionnumber);
		return s;
	}

	private String readString(int sht,int row, int col) {
		String value = null;
		//grab the correct sheet
		Sheet sheet = _questionbook.getSheet(sht);
		
		//grab the cell asked for
		Cell cell = sheet.getCell(col,row);
		value = cell.getContents();		
		return value;
	}


}
