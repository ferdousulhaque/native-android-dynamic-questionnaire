package com.siriusbd.dynamic_questionnaire;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	SQLiteDatabase database;
	private TextView getQuestion;
	Cursor question;
	int rowindex;
	int max;
	int page_value=1;
	int get_type_value;
	String get_QId="Q1a";
	Cursor get_QId_set;
	Cursor max_ques_c;
	int max_ques;
	int ques_count=1;
	//important for database part decleration
	private EditText resp_id;
	String resp_id_n;
	private EditText resp_name;
	String resp_name_n;
	private EditText tab_no;
	String tab_no_n;
	private EditText mobile_no;
	String mobile_no_n;
	Cursor resp_value;
	String resp_text_value;
	String current_date;
	long start_time;
	//end of database part values
	//private RadioButton radioButton;
	final Model_db get_from_model = new Model_db(this);
	
	//for recording part
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "DQ_Recordings";
	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,AUDIO_RECORDER_FILE_EXT_3GP };
	MediaPlayer mp = new MediaPlayer();
	//end for recording part
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_front);
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		current_date=today.monthDay+"/"+today.month+"/"+today.year;
		database=openOrCreateDatabase(Environment.getExternalStorageDirectory().getPath()+"/mysql2sqlite.db",MODE_PRIVATE,null);
		//Toast.makeText(this, "Date: "+current_date, Toast.LENGTH_SHORT).show();
//		database=openOrCreateDatabase("/mnt/sdcard/mysql2sqlite.db",MODE_PRIVATE,null);
//		getQuestion = (TextView) findViewById(R.id.question);  
//		get_QId_set=get_from_model.getQId_List();
//		max_ques_c=get_from_model.get_max_question();
//		max_ques=max_ques_c.getInt(0);
//		displayQuestion(get_QId);
		//Toast.makeText(this, "Maximum question question: "+max_ques, Toast.LENGTH_SHORT).show();
		//db1.execSQL("CREATE TABLE IF NOT EXISTS recuitment(respondentID INTEGER NOT NULL,respondentName TEXT(50),mobileNumber TEXT(25),age TEXT(2),sec TEXT(1),q1 TEXT(1),	q6 TEXT(28), PRIMARY KEY (respondentID));");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void Close(View view){
		System.exit(0);
	}
	public void NextQuestion(View view){
		//Toast.makeText(this, "Maximum question with this  QID: "+max, Toast.LENGTH_SHORT).show();
		//Cursor get_question_all;
		//get_question_all=get_from_model.getT_question_List("Q1b");
		//Toast.makeText(this, "Maximum question with this  Qtype: "+get_question_all.getString(3), Toast.LENGTH_SHORT).show();
		//page_value=page_value+1;
		if(page_value==1){
			resp_id=(EditText) findViewById(R.id.edtres_id);
			resp_id_n=resp_id.getText().toString();
			resp_name=(EditText) findViewById(R.id.edtres_name);
			resp_name_n=resp_name.getText().toString();
			tab_no=(EditText) findViewById(R.id.edttabno);
			tab_no_n=tab_no.getText().toString();
			mobile_no=(EditText) findViewById(R.id.edtmobile);
			mobile_no_n=mobile_no.getText().toString();
			//Toast.makeText(this, "Last Page Respondent Id: "+resp_id_n+" "+resp_name_n+" "+tab_no_n+" "+mobile_no_n+" >", Toast.LENGTH_SHORT).show();
			if(resp_id_n!="" && resp_name_n!="" && tab_no_n!="" && mobile_no_n!=""){
				//insert code for first page
				//database.execSQL("INSERT INTO t_resp_info (resp_id, resp_name, tabno, mobile_no) VALUES ('"+resp_id_n+"', '"+resp_name_n+"', '"+tab_no_n+"', '"+mobile_no_n+"');");
				//end
				page_value=page_value+1;
				start_time=System.currentTimeMillis();
				setContentView(R.layout.activity_main);
				//
				getQuestion = (TextView) findViewById(R.id.question);  
				get_QId_set=get_from_model.getQId_List();
				max_ques_c=get_from_model.get_max_question();
				max_ques=max_ques_c.getInt(0);
				if(get_type_value==2){
					//set the function to get all the checked checkbox
				}
				displayQuestion(get_QId);
			}else{
				Toast.makeText(this, "Please insert value before proceeding Thanks.", Toast.LENGTH_SHORT).show();
			}
		}else{
			if(ques_count<max_ques){
				if(resp_text_value != ""){
					ques_count=ques_count+1;
					long difference;
					difference=System.currentTimeMillis()-start_time;
					//Toast.makeText(this, "Last Page Value to enter: "+resp_text_value, Toast.LENGTH_SHORT).show();
					Toast.makeText(this, "Time elapsed in last page: "+(difference/1000), Toast.LENGTH_SHORT).show();
					//insert values code will be here
					database.execSQL("INSERT INTO t_resp_info (resp_id, resp_name, int_date, start_time, ques_id, resp_type, resp_value, tabno, mobile_no) VALUES ('" + resp_id_n + "','" + resp_name_n + "','" + current_date + "','" + (difference/1000) + "','"+ get_QId +"','" + get_type_value + "','" + resp_text_value + "','" + tab_no_n + "','" + mobile_no_n + "');");
					//database.execSQL("INSERT INTO t_resp_info   (resp_id, resp_name, tabno, mobile_no) VALUES ('"+resp_id_n+"', '"+resp_name_n+"', '"+tab_no_n+"', '"+mobile_no_n+"');");
					//database.execSQL("UPDATE t_resp_info SET int_date = '"+ current_date +"',	start_time = '"+ (difference/1000) +"', ques_id = '"+ get_QId +"',	resp_type = '"+ get_type_value +"',	resp_value = '"+ resp_text_value +"'	WHERE resp_id = '" + resp_id_n +"';");
					//end
					resp_text_value="";
					start_time=System.currentTimeMillis();
					get_QId_set.moveToNext();
					get_QId=get_QId_set.getString(0);
					if(get_type_value==2){
						//set the function to get all the checked checkbox
					}
					displayQuestion(get_QId);
				}else{
					Toast.makeText(this, "Please insert value before proceeding Thanks.", Toast.LENGTH_SHORT).show();
				}
			}else{
				if(get_type_value==2){
					//set the function to get all the checked checkbox
				}
				long difference;
				difference=System.currentTimeMillis()-start_time;
				//Toast.makeText(this, "Last Page Value to enter: "+resp_text_value, Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "Time elapsed in last page: "+(difference/1000), Toast.LENGTH_SHORT).show();
				//insert values code will be here
				database.execSQL("INSERT INTO t_resp_info (resp_id, resp_name, int_date, start_time, ques_id, resp_type, resp_value, tabno, mobile_no) VALUES ('" + resp_id_n + "','" + resp_name_n + "','" + current_date + "','" + (difference/1000) + "','"+ get_QId +"','" + get_type_value + "','" + resp_text_value + "','" + tab_no_n + "','" + mobile_no_n + "');");
				enableButton(R.id.btnStop,false);
				displayQuestion("end");
			}
		}
		//displayQuestion("Q1b");
	}
	
	View.OnClickListener handleOnClick(final RadioButton button) {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	resp_value=get_from_model.getText_Value(get_QId, button.getText().toString());
	        	resp_text_value=resp_value.getString(0);
	        	//Toast.makeText(getApplicationContext(), resp_text_value,Toast.LENGTH_SHORT).show();  
	        }
	    };
	}
	
	View.OnClickListener handleOnClick(final CheckBox button) {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	resp_value=get_from_model.getText_Value(get_QId, button.getText().toString());
	        	resp_text_value=resp_text_value+resp_value.getString(0);
	        	enableCheckbox(button.getId(),false);
	        	//Toast.makeText(getApplicationContext(), resp_text_value,Toast.LENGTH_SHORT).show();  
	        }
	    };
	}
	
	View.OnClickListener handleOnClick(final Button button) {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	//button.getText().toString();
	        	//Toast.makeText(getApplicationContext(), button.getText().toString(),Toast.LENGTH_SHORT).show();
	        	if(button.getText().toString()=="Record"){
	        		recorder = new MediaRecorder();

	        		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	        		recorder.setOutputFormat(output_formats[currentFormat]);
	        		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	        		recorder.setOutputFile(getFilename());

	        		recorder.setOnErrorListener(errorListener);
	        		recorder.setOnInfoListener(infoListener);
	        		
	        		enableButton(1,false);
        			enableButton(2,true);
        			enableButton(3,false);
	        		try {
	        			recorder.prepare();
	        			recorder.start();
	        		} catch (IllegalStateException e) {
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        	}else if(button.getText().toString()=="Stop"){
	        		if (null != recorder) {
	        			recorder.stop();
	        			recorder.reset();
	        			recorder.release();

	        			recorder = null;
	        			resp_text_value=getFilename();
	        			
	        			mp.stop();
	        			enableButton(1,true);
	        			enableButton(2,false);
	        			enableButton(3,true);
	        			Toast.makeText(getApplicationContext(), "The file has been successfully recorded to"+AUDIO_RECORDER_FOLDER+"as "+resp_text_value,Toast.LENGTH_SHORT).show();
	        		}
	        	}else if(button.getText().toString()=="Play"){
	        		enableButton(1,false);
        			enableButton(2,true);
        			enableButton(3,false);
        			
	        	    try {
	        	        mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/"+resp_text_value);
	        	        mp.prepare();
	        	        mp.start();
	        	    } catch (Exception e) {
	        	        e.printStackTrace();
	        	    }
	        	}
	        }
	    };
	}
	
	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}

		return (file.getAbsolutePath() + "/" + resp_id_n+ get_QId + file_exts[currentFormat]);
	}
	
	private void displayQuestion(String QId)
	{		
		if(QId!="end"){
			//Fetching data question data and incrementing on each click
			Cursor max_count_qattr; 
			question=get_from_model.getQuestion_Content(QId);
			
			max_count_qattr=get_from_model.get_max_count(QId);
			max=max_count_qattr.getInt(0);
	
			//c2 =database.getAns(rowIndex++);
			getQuestion.setText(question.getString(0));
			Cursor attributes;
			attributes=get_from_model.getAttributes_List(QId);
			
			
			Cursor for_get_type_value;
			for_get_type_value=get_from_model.getT_question_List(QId);
			get_type_value=Integer.parseInt(for_get_type_value.getString(2));
			//Toast.makeText(this, "Maximum question with this  Qtype: "+for_get_type_value.getString(2), Toast.LENGTH_SHORT).show();
			//get_type_value=3;
			//value="3";
			//page_value=1;
			
			if(get_type_value==1){
				
				RadioGroup mRadioGroup;
				mRadioGroup = new RadioGroup(this);
				TableLayout mainTable = (TableLayout) findViewById(R.id.myTable);
				mainTable.removeAllViews();
				mainTable.addView(mRadioGroup);
				
				mRadioGroup.removeAllViews();
				
				//RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup1);
				//RadioButton mRadioGroup;
				
				for(int i = 0; i < max; i++) {
					RadioButton button = new RadioButton(this);
				    button.setText(attributes.getString(0));
				    button.setId(i);
				    button.setOnClickListener(handleOnClick(button));
				    attributes.moveToNext();
				    mRadioGroup.addView(button);
				}
			}
			else if(get_type_value==2){
				
				TableLayout mainTable = (TableLayout) findViewById(R.id.myTable);
				
				//RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup1);
				//RadioButton mRadioGroup;
				
				mainTable.removeAllViews();
				
				for(int i = 0; i < max; i++) {
					CheckBox button = new CheckBox(this);
				    button.setText(attributes.getString(0));
				    button.setId(i);
				    button.setOnClickListener(handleOnClick(button));
				    attributes.moveToNext();
				    mainTable.addView(button);
				}
			}
			else if(get_type_value==3){
				
				TableLayout mainTable = (TableLayout) findViewById(R.id.myTable);
				
				//RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup1);
				//RadioButton mRadioGroup;
				mainTable.removeAllViews();
				//for(int i = 0; i < max; i++) {
					Button button = new Button(this);
				    button.setText("Record");
				    button.setWidth(200);
				    button.setOnClickListener(handleOnClick(button));
				    //button.setFocusableInTouchMode(true);
				    button.setId(1);
				    //attributes.moveToNext();
				    mainTable.addView(button);
				    
				    Button button2 = new Button(this);
				    button2.setText("Stop");
				    button2.setOnClickListener(handleOnClick(button2));
				    button2.setId(2);
				    //attributes.moveToNext();
				    mainTable.addView(button2);
				    
				    Button button3 = new Button(this);
				    button3.setText("Play");
				    button3.setOnClickListener(handleOnClick(button3));
				    button3.setId(3);
				    //attributes.moveToNext();
				    mainTable.addView(button3);
				    
				    enableButton(3,false);
				    enableButton(2,false);
				    
				    //EditText textBox = new EditText(this);
				    //textBox.setText("Play");
				    //button.setId(i);
				    //attributes.moveToNext();
				    //mainTable.addView(textBox);
				//}
			}
		}
		else{
			TextView question_text=(TextView) findViewById(R.id.question);
			question_text.setText("");
			
			TableLayout mainTable = (TableLayout) findViewById(R.id.myTable);
			
			//RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup1);
			//RadioButton mRadioGroup;
			mainTable.removeAllViews();
			
			TextView text=new TextView(this);
			text.setText("Thank you for perticipating. We may contact you in a few months later.");
			mainTable.addView(text);
		}
	}
	
	public void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}
	
	public void enableCheckbox(int id, boolean isEnable) {
		((CheckBox) findViewById(id)).setEnabled(isEnable);
	}
	
	
	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			//Toast.makeText(this,"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			//Toast.makeText(this,"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

}
