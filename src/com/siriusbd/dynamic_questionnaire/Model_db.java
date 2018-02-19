package com.siriusbd.dynamic_questionnaire;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;


	public class Model_db extends SQLiteOpenHelper{
	
		//The Android's default system path of your application database.
		private static String DB_PATH = Environment.getExternalStorageDirectory().getPath();
		private static String DB_NAME = "mysql2sqlite.db";
		private SQLiteDatabase myDataBase;
		private SQLiteDatabase myData;
		private final Context myContext;
		
		
		/**
		* Constructor
		* Takes and keeps a reference of the passed context in order to access to the application assets and resources.
		* @param context
		*/
		public Model_db(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	* Creates a empty database on the system and rewrites it with your own database.
	* */
	public void createDataBase() throws IOException{
	
		boolean dbExist = checkDataBase();
			if(dbExist){
			//do nothing - database already exist
			}else{
			CopyFiles();
		}
	}


	private void CopyFiles()
	{
		try
		{
		InputStream is = myContext.getAssets().open(DB_NAME);
		File outfile = new File(DB_PATH,DB_NAME);
		outfile.getParentFile().mkdirs();
		outfile.createNewFile();
		
		if (is == null)
		throw new RuntimeException("stream is null");
		else
		{
		FileOutputStream out = new FileOutputStream(outfile);
		// BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(outfile));
		byte buf[] = new byte[128];
		do {
		int numread = is.read(buf);
		if (numread <= 0) break; out.write(buf, 0, numread); } while (true); is.close(); out.close(); } 
		//AssetFileDescriptor 
		//af = am.openFd("world_treasure_hunter_deluxe.apk"); 
		} catch (IOException e) { 
			throw new RuntimeException(e); 
			} 
		} 
	/** * Check if the database already exist to avoid re-copying the file each time you open the application. * @return true if it exists, false if it doesn't */ 
	
	private boolean checkDataBase(){ 
		SQLiteDatabase checkDB = null; 
		try{ 
			String myPath = DB_PATH + DB_NAME; checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY); 
			}catch(SQLiteException e){ 
				
			} if(checkDB != null){ 
				checkDB.close(); 
				} return checkDB != null ? true : false; 
			}


	public void openDataBase() throws SQLException{
	
		//Open the database
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
	
		if(myDataBase != null)
		myDataBase.close();
		
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	/// Get Question content////////
	public Cursor getQuestion_Content(String QId)
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
		Cursor cur;
		cur=myData.rawQuery("select QDesc1 from t_question where QId='"+QId+"'",null);
		cur.moveToFirst();
		
		
		myData.close();
		return cur;
	};
	//////////////////////////

	/// Get quiz content////////
	public Cursor getT_question_List(String QId)
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cur;
		cur=myData.rawQuery("SELECT	QDesc1,QDesc2,QType,NoOfResponseMax,NoOfResponseMin,Skip,DisplayNextButton,EnableBackButton,EnableExitButton,EnablePauseButton,OrderTag FROM t_question WHERE LanguageId = 2 AND QId='"+QId+"'",null);
		cur.moveToFirst();
		myData.close();
		return cur;
	};
	//////////////////////////
	
	public Cursor getQId_List()
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cur;
		cur=myData.rawQuery("SELECT	QId FROM t_question WHERE LanguageId = 2 ",null);
		cur.moveToFirst();
		myData.close();
		return cur;
	};
	
	/// Get quiz content////////
		public Cursor getTypeInt_to_Name(String QId)
		{
			String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
			myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Cursor cur;
			cur=myData.rawQuery("SELECT	QDesc1,QDesc2,QType,NoOfResponseMax,NoOfResponseMin,Skip,DisplayNextButton,EnableBackButton,EnableExitButton,EnablePauseButton,OrderTag FROM t_question WHERE LanguageId = 2 AND QId='"+QId+"'",null);
			cur.moveToFirst();
			myData.close();
			return cur;
		};
		//////////////////////////
	
	/// getAttributes_List ////////
		public Cursor getAttributes_List(String QId)
		{
			String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
			myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Cursor cur;
			cur=myData.rawQuery("SELECT	AttributeLabel,	AttributeValue FROM	t_optattribute WHERE LanguageId = 2 AND QId='"+QId+"'",null);
			cur.moveToFirst();
			myData.close();
			return cur;
		};
		//////////////////////////
		
		public Cursor getText_Value(String QId,String Response_text)
		{
			String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
			myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Cursor cur;
			cur=myData.rawQuery("SELECT AttributeValue FROM	t_optattribute WHERE LanguageId = 2 AND AttributeLabel='"+ Response_text +"'AND QId='"+QId+"'",null);
			cur.moveToFirst();
			myData.close();
			return cur;
		};
	
	public Cursor get_max_count(String QId)
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cur;
		cur=myData.rawQuery("SELECT	COUNT(*) FROM t_optattribute WHERE LanguageId = 2 AND QId='"+QId+"'",null);
		cur.moveToFirst();
		myData.close();
		return cur;
	};
	
	public Cursor get_max_question()
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cur;
		cur=myData.rawQuery("SELECT	COUNT(*) FROM t_question WHERE LanguageId = 2",null);
		cur.moveToFirst();
		myData.close();
		return cur;
	};
	
	public Cursor getArray(int quizid)
	{
		String myPath = Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db";
		myData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		
		
		Cursor cur;
		cur = myData.rawQuery("select answers from answer where quiz_id='"+quizid+"'", null);
		cur.moveToFirst();
		myData.close();
		return cur;
	}

}