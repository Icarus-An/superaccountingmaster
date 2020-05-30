package com.example.superaccountingmaster;



import com.example.superaccountingmaster.DBOpenHelper;
import com.example.superaccountingmaster.pubFun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Choice extends AppCompatActivity {

    public  Double sum=0.00;
    public Double valuee;
    private int type = 0;//0:income   1:payout
    final static int EDIT_MODE = 2;

    private String[] str = null;
    private String[] accountId = null;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePicker = null;
    private AlertDialog dialog = null;
    private ArrayAdapter<String> adapter;
    private List<String> list = null;


    private TextView title_tv = null;
    private RadioGroup trans_type_tab_rg = null;
    private RadioButton rb1=null;
    private RadioButton rb2=null;

    private FrameLayout corporation_fl = null;
    private FrameLayout empty_fl = null;
    private Button cost_btn = null;
    private String  value="0";
    private Spinner first_level_category_spn = null;
    private Spinner sub_category_spn = null;
    private int type_sub_id = 0;
    private Spinner account_spn = null;
    private Spinner corporation_spn = null;
    private Button trade_time_btn = null;
    private Spinner project_spn = null;
    private Button memo_btn = null;
    private Button save_btn = null;
    private Button cancel_btn = null;

    private EditText edit = null;
    private int isInitOnly = 0;

    private Context context;


    private static String[] bigCategoryList = { "" };
    private static String[] defaultSubCategory_info = { "" };

    private static String[][] subCategory_info = new String[][] {{ "" }, { "" }};

    private static String[] accountList = { "" };

    private static String[] shopList = { "" };

    private static String[] noteList = { "" };

    private TextView txtBigCategory_view;
    private Spinner BigCategory_spinner;
    private ArrayAdapter<String> BigCategory_adapter;

    private TextView txtSubCategory_view;
    private Spinner subCategory_spinner;
    private ArrayAdapter<String> subCategory_adapter;

    private TextView txtAccount_view;
    private Spinner account_spinner;
    private ArrayAdapter<String> account_adapter;

    private TextView txtShop_view;
    private Spinner shop_spinner;
    private ArrayAdapter<String> shop_adapter;

    private TextView txtNote_view;
    private Spinner note_spinner;
    private ArrayAdapter<String> note_adapter;

    private String txtBigCategory = "";
    private String txtSubCategory = "";
    private String txtAccount = "";
    private String txtShop = "";
    private String txtNote = "";

    private TextView txtDate;
    private TextView income_expense;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);


        final Intent intent = getIntent();
        type = intent.getIntExtra("strType", 0);

        context = this;

        initSpinner();

        loadingFormation();

        trade_time_btn.setText(pubFun.format(calendar.getTime()));

        cost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Choice.this, KeyPad.class);
                i.putExtra("value", value);

                startActivityForResult(i, 0);
            }
        });
        trade_time_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                openDate();
            }
        });
    }



    private void loadingFormation(){
        cost_btn=(Button)findViewById(R.id.cost_btn);
        trade_time_btn=(Button)findViewById(R.id.trade_time_btn);
    }

    private void openDate() {
        datePicker = new DatePickerDialog(this, mDateSetListenerSatrt,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            Bundle extras = data.getExtras();
            value = extras.getString("value");
            cost_btn.setText(DecimalFormat.getCurrencyInstance().format(Double.parseDouble(value)));
        }
    }


    private DatePickerDialog.OnDateSetListener mDateSetListenerSatrt = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.YEAR, year);
            trade_time_btn.setText(pubFun.format(calendar.getTime()));
        }
    };


    private void initSpinner(){
        if(type == 0){
            bigCategoryList=getResources().getStringArray(R.array.bigCategoryList);

            defaultSubCategory_info=getResources().getStringArray(R.array.defaultSubCategory_info);

            subCategory_info = new String[][] {

            getResources().getStringArray(R.array.diyihang),getResources().getStringArray(R.array.dierhang), getResources().getStringArray(R.array.disanhang1)};

            accountList=getResources().getStringArray(R.array.accountList1);

            shopList=getResources().getStringArray(R.array.shoplist);

            noteList=getResources().getStringArray(R.array.notelist);
        }else{

            bigCategoryList=getResources().getStringArray(R.array.bigCategoryList1);
            defaultSubCategory_info=getResources().getStringArray(R.array.defaultSubCategory_info1);

            subCategory_info = new String[][] {
                    getResources().getStringArray(R.array.diyihang1),
                    getResources().getStringArray(R.array.dierhang1),
                    getResources().getStringArray(R.array.disanhang),
                    getResources().getStringArray(R.array.disihang),
                    getResources().getStringArray(R.array.diwuhang),
                    getResources().getStringArray(R.array.diliuhang),
                    getResources().getStringArray(R.array.diqihang),
                    getResources().getStringArray(R.array.dibayang),
                    getResources().getStringArray(R.array.dijiuhang),


                    };

            accountList=getResources().getStringArray(R.array.accountList1);

            shopList=getResources().getStringArray(R.array.shoplist1);

            noteList=getResources().getStringArray(R.array.notelist);
        }


        txtBigCategory_view = (TextView) findViewById(R.id.txtBigCategory);
        BigCategory_spinner = (Spinner) findViewById(R.id.BigCategory_spinner);

        BigCategory_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bigCategoryList);

        BigCategory_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        BigCategory_spinner.setAdapter(BigCategory_adapter);

        BigCategory_spinner
                .setOnItemSelectedListener(new BigCategory_spinnerSelectedListener());

        BigCategory_spinner.setVisibility(View.VISIBLE);


        txtSubCategory_view = (TextView) findViewById(R.id.txtSubCategory);
        subCategory_spinner = (Spinner) findViewById(R.id.subCategory_spinner);
        subCategory_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, defaultSubCategory_info);
        subCategory_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategory_spinner.setAdapter(subCategory_adapter);
        subCategory_spinner
                .setOnItemSelectedListener(new subCategory_spinnerSelectedListener());
        subCategory_spinner.setVisibility(View.VISIBLE);


        txtAccount_view = (TextView)findViewById(R.id.txtAccount);
        account_spinner = (Spinner) findViewById(R.id.account_spinner);
        account_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, accountList);
        account_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(account_adapter);
        account_spinner
                .setOnItemSelectedListener(new account_spinnerSelectedListener());
        account_spinner.setVisibility(View.VISIBLE);


        txtShop_view = (TextView)findViewById(R.id.txtShop);
        shop_spinner = (Spinner) findViewById(R.id.shop_spinner);
        shop_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, shopList);
        shop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_spinner.setAdapter(shop_adapter);
        shop_spinner
                .setOnItemSelectedListener(new shop_spinnerSelectedListener());
        shop_spinner.setVisibility(View.VISIBLE);


        txtNote_view = (TextView)findViewById(R.id.txtNote);
        note_spinner = (Spinner) findViewById(R.id.note_spinner);
        note_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, noteList);
        note_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        note_spinner.setAdapter(note_adapter);
        note_spinner
                .setOnItemSelectedListener(new note_spinnerSelectedListener());
        note_spinner.setVisibility(View.VISIBLE);
    }


    class BigCategory_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtBigCategory = bigCategoryList[arg2];
            int pos = BigCategory_spinner.getSelectedItemPosition();
            subCategory_adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, subCategory_info[pos]);
            subCategory_spinner.setAdapter(subCategory_adapter);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    class subCategory_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtSubCategory = (String) subCategory_spinner
                    .getItemAtPosition(arg2);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    class account_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtAccount = accountList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }

    }


    class shop_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtShop = shopList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    class note_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtNote = noteList[arg2];
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    public void OnMySaveClick(View v) {
        double valuee = Double.valueOf(value.toString());
        if(type==0){
            sum=sum+valuee;
        }
        else {
            sum=sum-valuee;
        }
        saveInfo();
    }
    public void OnMyCancelClick(View v) {
        exit();
    }


    private void exit() {
        if(type != EDIT_MODE){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            this.setResult(RESULT_OK, getIntent());
            this.finish();
        }
    }


    private void saveInfo() {

        SharedPreferences sharedPreferences= getSharedPreferences("setting",Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("userID", "");

        Log.i("info", R.string.info + userID);

        if(userID.isEmpty()){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.hit)
                    .setMessage(R.string.message)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setResult(RESULT_OK);
                            Intent intent=new Intent(Choice.this,LoginActivity.class);
                            Choice.this.startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            return;
                        }
                    })
                    .show();
        }else{
            if(value.equals("") || value == null || Double.parseDouble(value) <= 0){
                Toast.makeText(getApplicationContext(), getString(R.string.input_message),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values= new ContentValues();
            values.put("userID",userID);
            values.put("Type",type);
            values.put("incomeWay",txtAccount);
            values.put("incomeBy",txtShop);
            values.put("category",txtBigCategory);
            values.put("item",txtSubCategory);
            values.put("cost", value);
            values.put("note", txtNote);
            values.put("makeDate",pubFun.format(calendar.getTime()));
            long rowid = db.insert("basicCode_tb",null,values);


            Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);
            if(c!=null && c.getCount() >= 1){
                String[] cols = c.getColumnNames();
                while(c.moveToNext()){
                    for(String ColumnName:cols){
                        Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                    }
                }
                c.close();
            }
            db.close();

            Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
            exit();
        }
    }
}

