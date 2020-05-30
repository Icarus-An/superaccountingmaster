package com.example.superaccountingmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_who_pay extends AppCompatActivity {
    private Button float_add_round;

    private static final String TAG = "MainActivity";
//    SharedPreferences pref =getSharedPreferences("AA_data",MODE_PRIVATE);
//    SharedPreferences.Editor pref_Editor = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_pay);

        minsidescrollerLinearLayout = (LinearLayout) findViewById(R.id.ll_add_new_item_1);//initView
        float_add_round = (Button) findViewById(R.id.float_add_round);

        float_add_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    private View addgivemoneyview;
    private LinearLayout minsidescrollerLinearLayout;
    private EditText peopleTextView1, moneyTextView2;
    private int position = 0;
    private Map<Integer, View> ScrollmapView = new HashMap<>();
    private List<String> NameList = new ArrayList<>();
    private List<String> WhoPayList = new ArrayList<>();
    private List<String> editTexts = new ArrayList<>();

    public void addNewItem() {
        addgivemoneyview= new View(Activity_who_pay.this);
        addgivemoneyview = View.inflate(this, R.layout.addinscroll, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        peopleTextView1 = addgivemoneyview.findViewById(R.id.NameInputBox);
        moneyTextView2 = addgivemoneyview.findViewById(R.id.MoneyInputBox);
        setDeletelisten();
        minsidescrollerLinearLayout.addView(addgivemoneyview, params);
        addgivemoneyview.findViewById(R.id.btn_delete).setTag(position);
        ScrollmapView.put(position, addgivemoneyview);

        position++;
    }

    private boolean getEditItem() {
        editTexts.clear();
        String people;
        String money;

        for (int i = 0; i < minsidescrollerLinearLayout.getChildCount(); i++) {
            View childAtView = minsidescrollerLinearLayout.getChildAt(i);
            peopleTextView1 = childAtView.findViewById(R.id.NameInputBox);
            moneyTextView2 = childAtView.findViewById(R.id.MoneyInputBox);
            if(!peopleTextView1.getText().toString().equals("") && moneyTextView2.getText().toString().equals("")){
                editTexts.add(peopleTextView1.getText().toString());
                editTexts.add(String.valueOf(0));
            }

            if (!peopleTextView1.getText().toString().equals("") && !moneyTextView2.getText().toString().equals("")) {
                editTexts.add(peopleTextView1.getText().toString());
                editTexts.add(moneyTextView2.getText().toString());
            }
        }
        Log.d(TAG, "datais: --------"+editTexts.toString());

        for (int i = 0; i < minsidescrollerLinearLayout.getChildCount(); i++) {
            //将每一个view遍历出来，然后findViewById后，就可以获取EditText的值
            View childAtView = minsidescrollerLinearLayout.getChildAt(i);
            peopleTextView1 = childAtView.findViewById(R.id.NameInputBox);
            moneyTextView2 = childAtView.findViewById(R.id.MoneyInputBox);

            people = peopleTextView1.getText().toString();
            if(moneyTextView2.getText().toString().equals("")){
                money = moneyTextView2.getHint().toString();
            }
            else{
                money = moneyTextView2.getText().toString();
            }


            if (!people.equals("") && !money.equals("")) {
                NameList.add(people);
                WhoPayList.add(money);
//                pref_Editor.putString(people, money);
            } else{
                Toast.makeText(this, this.getString(R.string.toast1) + (i + 1) + this.getString(R.string.toast2), Toast.LENGTH_LONG).show();

//                Toast.makeText(this, "The" + (i + 1) + "item‘s name has not filled completely.", Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        Log.e(TAG, "NameList: --------"+NameList);
        Log.e(TAG,"WhoPayList:-------"+WhoPayList);
        return true;
    }

    private void setDeletelisten() {
        addgivemoneyview.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionView = (int) view.getTag();
                minsidescrollerLinearLayout.removeView(ScrollmapView.get(positionView));
                ScrollmapView.remove(positionView);
                if (ScrollmapView.size() == 0) {
                    position = 0;
                    NameList.clear();
                    WhoPayList.clear();
                    minsidescrollerLinearLayout.removeAllViews();
                }
            }
        });

    }
    public void toNext(MenuItem item){
        if(getEditItem() && position>=2){
            Intent intent = new Intent(getApplicationContext(), Activity_for_who.class);
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("NameListData", (ArrayList<String>) NameList);
            bundle.putStringArrayList("WhoPayListData",(ArrayList<String>)WhoPayList);
            bundle.putStringArrayList("NameAndmoneydata", (ArrayList<String>) editTexts);

            intent.putExtras(bundle);
            startActivity(intent);
            NameList.clear();
            WhoPayList.clear();
            editTexts.clear();
        }
        else if(position<2) {
            Toast.makeText(this, R.string.toast3, Toast.LENGTH_LONG).show();
            NameList.clear();
            WhoPayList.clear();
            editTexts.clear();
        }
        else{
            NameList.clear();
            WhoPayList.clear();
            editTexts.clear();
        }
    }

}



