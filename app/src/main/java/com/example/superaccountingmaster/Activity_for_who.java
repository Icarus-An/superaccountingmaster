package com.example.superaccountingmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_for_who extends AppCompatActivity {
    private static final String TAG = "Activity_for_who";
    List<String> Resulttext = null;
    List<String> NameList = null;
    List<String> WhoPayList = null;
    List<String> Payforwho = new ArrayList<>();

    double totalmoney;
    double remainmoney;
    DecimalFormat keep2digit = new DecimalFormat("######0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_who);

        mLinearLayout = (LinearLayout) findViewById(R.id.existing_item);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            NameList = bundle.getStringArrayList("NameListData");
            WhoPayList = bundle.getStringArrayList("WhoPayListData");
            Log.e(TAG, "NameList: --------"+NameList.toString());
            Log.e(TAG, "WhoPayList:--------"+WhoPayList.toString());
            Log.e(TAG, "size: --------"+NameList.size());
        }

        if (bundle != null) {
            Resulttext = bundle.getStringArrayList("NameAndmoneydata");
            Log.d(TAG, "results: --------"+Resulttext.toString());
            Log.d(TAG, "size: --------"+Resulttext.size());
        }

        if (Resulttext != null) {
            //calculate total money
            for(int i=0;i<(Resulttext.size()/2);i++)
            {
                totalmoney += Double.parseDouble(Resulttext.get(2*i+1));
            }
            Log.d(TAG, "total money is: --------"+totalmoney);

            //add view and set listen
            for(int i=0;i<(Resulttext.size()/2);i++)
            {
                addItemView();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }


    private LinearLayout mLinearLayout;
    private EditText mTextEdit2;
    private int position = 0;
    private Map<Integer, View> mapView = new HashMap<>();

    private void addItemView() {
        View mView = View.inflate(this, R.layout.showinscroll, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView mTextView1 = mView.findViewById(R.id.NameInputBox);
        mTextEdit2 = mView.findViewById(R.id.MoneyInputBox);
        mLinearLayout.addView(mView, params);
        Log.e("position", String.valueOf(position));
        mView.setTag(position);
        mapView.put(position, mView);
        mTextView1.setText(NameList.get(position));
        position++;
        mTextEdit2.setHint(keep2digit.format(2*totalmoney/Resulttext.size()));
        mTextEdit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // whentextchange
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // beforetextchange
            }

            @Override
            public void afterTextChanged(Editable s) {
                //scan the change
                int restofline = 0;
                double summoney = 0;
                double averagemoney;
                int linecountnumber;
                int linecountnumber2;
                int linecountnumber3;

                for(int i=0;i<mLinearLayout.getChildCount();i++){
                    View childAtView = mLinearLayout.getChildAt(i);
                    mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                    if(mTextEdit2.getText().toString().equals("")){
                        restofline++;
                    }
                    else{
                        summoney += Double.parseDouble(mTextEdit2.getText().toString());
                    }
                }

                remainmoney = totalmoney - summoney;
                linecountnumber = restofline;
                linecountnumber2 = restofline;
                linecountnumber3 = restofline;
                Log.d(TAG, "remaining money: --------"+remainmoney);

                //Check the value

                if(remainmoney<0){
                    for(int i=0;i<mLinearLayout.getChildCount();i++) {
                        View childAtView = mLinearLayout.getChildAt(i);
                        mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                        mTextEdit2.setTextColor(Color.RED);
                    }
                }
                else{
                    for(int i=0;i<mLinearLayout.getChildCount();i++) {
                        View childAtView = mLinearLayout.getChildAt(i);
                        mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                        mTextEdit2.setTextColor(Color.BLACK);
                    }
                    averagemoney = getaverage(remainmoney,restofline);

                    Log.d(TAG, "averagemoney: --------"+averagemoney);

                    double totalrestnumber = 0;



                    //apply the change

                    for(int i=0;i<mLinearLayout.getChildCount();i++){
                        View childAtView = mLinearLayout.getChildAt(i);
                        mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                        if(mTextEdit2.getText().toString().equals("")){
                            linecountnumber3--;
                            if(linecountnumber3 > 0){
                                mTextEdit2.setHint(keep2digit.format(averagemoney));//keep 2 digits
                            }
                        }
                    }

                    for(int i=0;i<mLinearLayout.getChildCount();i++){
                        View childAtView = mLinearLayout.getChildAt(i);
                        mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                        if(mTextEdit2.getText().toString().equals("")){
                            linecountnumber--;
                            if(linecountnumber > 0){
                                totalrestnumber += Double.parseDouble(mTextEdit2.getHint().toString());
                            }
                        }
                        else{
                            totalrestnumber += Double.parseDouble(mTextEdit2.getText().toString());
                        }
                    }

                    for(int i=0;i<mLinearLayout.getChildCount();i++){
                        View childAtView = mLinearLayout.getChildAt(i);
                        mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                        if(mTextEdit2.getText().toString().equals("")){
                            linecountnumber2--;
                            if(linecountnumber2 == 0){
                                mTextEdit2.setHint(keep2digit.format(totalmoney-totalrestnumber));//make sure sum is totalnumber
                            }
                        }
                    }
                    Log.d(TAG, "totalrestnumber: --------"+totalrestnumber);
                }

            }

        });
    }

    private double getaverage(double summoney, int restofline) {
        if(restofline == 0){
            return 0;
        }
        double averagemoney;
        averagemoney = summoney/restofline;
        return averagemoney;
    }

    private boolean checknumber(){
        double sumnumbercheck = 0;
        for(int i=0;i<mLinearLayout.getChildCount();i++) {
            View childAtView = mLinearLayout.getChildAt(i);
            mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
            if(mTextEdit2.getText().toString().equals("")){
                sumnumbercheck += Double.parseDouble(mTextEdit2.getHint().toString());
            }
            else{
                sumnumbercheck += Double.parseDouble(mTextEdit2.getText().toString());
            }
        }
        Log.d(TAG, "sumnumbercheck: --------"+sumnumbercheck);
        //check the number
        if(!(keep2digit.format(sumnumbercheck).equals(keep2digit.format(totalmoney)))){
            View childAtView = mLinearLayout.getChildAt(mLinearLayout.getChildCount()-1);
            mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
            if(sumnumbercheck>totalmoney)
            mTextEdit2.setHint(keep2digit.format(Double.parseDouble(mTextEdit2.getHint().toString())-0.01));
            else
            mTextEdit2.setHint(keep2digit.format(Double.parseDouble(mTextEdit2.getHint().toString())+0.01));
        }
        return keep2digit.format(sumnumbercheck).equals(keep2digit.format(totalmoney));
    }

    private Boolean getEditItem() {
        checknumber();
        if(remainmoney<0){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                View childAtView = mLinearLayout.getChildAt(i);
                TextView manTextView1 = childAtView.findViewById(R.id.NameInputBox);
                mTextEdit2 = childAtView.findViewById(R.id.MoneyInputBox);
                if (!manTextView1.getText().toString().equals("") && !mTextEdit2.getText().toString().equals("")) {
//                Payforwho.add(manTextView1.getText().toString());
                    Payforwho.add(mTextEdit2.getText().toString());
                }
                if (!manTextView1.getText().toString().equals("") && mTextEdit2.getText().toString().equals("")){
                   // Payforwho.add(manTextView1.getText().toString());
                    Payforwho.add(mTextEdit2.getHint().toString());
                }
            }
            Log.e(TAG, "Payforwho: --------" + Payforwho.toString());
            return true;
        }
    }

    public void toNext(MenuItem item) {
        if(getEditItem()) {
            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("NameListdata", (ArrayList<String>) NameList);
            bundle.putStringArrayList("WhoPayListdata", (ArrayList<String>) WhoPayList);
            bundle.putStringArrayList("PayForWhoData", (ArrayList<String>) Payforwho);
            bundle.putInt("totalmoneyData", (int)totalmoney);

            intent.putExtras(bundle);
            startActivity(intent);
            Payforwho.clear();
        }
        else {
            Payforwho.clear();
        }
    }
}
