package com.example.superaccountingmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportActivity extends AppCompatActivity {
    private static final String TAG = "ReportActivity";
    List<String> NameList = new ArrayList<>();
    List<String> WhoPayList = new ArrayList<>();
    List<String> PayForWho = new ArrayList<>();
    List<String> debtResult = new ArrayList<>();

    List<Float> assets = new ArrayList<>();

    Set<String> debtset = new HashSet<String>();

    private LinearLayout mLinearLayout;
    private int position;
    private int totalmoney;
    private Map<Integer, View> mapView = new HashMap<>();

    DecimalFormat keep2digit = new DecimalFormat("######0.00");
    Button backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mLinearLayout = (LinearLayout) findViewById(R.id.existing_item_report);

        Bundle bundle = getIntent().getExtras();
        backbutton =  (Button) findViewById(R.id.home_btn);
        if (bundle != null) {
            NameList = bundle.getStringArrayList("NameListdata");
            WhoPayList =bundle.getStringArrayList("WhoPayListdata");
            PayForWho = bundle.getStringArrayList("PayForWhoData");
            totalmoney = bundle.getInt("totalmoneyData",0);
            Log.e(TAG, "WhoPayList:--------"+WhoPayList.toString());
            Log.e(TAG, "PayForWho:--------"+PayForWho.toString());
            debtResult = debtAlgorithm(NameList,WhoPayList,PayForWho);
            for(int i=0;i<(debtResult.size()/3);i++)
            {
                addItemView();
            }
            System.out.println(debtResult);
//            debtset.addAll(debtResult);

        }


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle backbundle = new Bundle();
                Intent backintent = new Intent(getApplicationContext(), MainActivity.class);
                saveArray(debtResult);
//                backbundle.putStringArrayList("NameAndMoneyDebt", (ArrayList<String>) debtResult);
//                backbundle.putInt("totalmoneyData",totalmoney);
//                backintent.putExtras(backbundle);

                startActivity(backintent);
                debtResult.clear();
            }

        });
    }

    public void saveArray(List<String> sKey)
    {
        SharedPreferences sp = getSharedPreferences("debtResult",MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.clear();
        mEdit1.putInt("Status_size", sKey.size());

        for(int i=0;i<sKey.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, sKey.get(i));
        }

        mEdit1.putInt("totalmoneyData",totalmoney);
        mEdit1.apply();
    }


    public List<Float> strListTofList(List<String> strList){
        List<Float> fList = new ArrayList<>();
        for (String value : strList){
            fList.add(Float.valueOf(value));
        }
        return fList;
    }


    public List<String> debtAlgorithm(List<String> namelist, List<String> whopaylist, List<String> payforwho){
        List<String> debtResult = new ArrayList<>();
        List<Float> whopaylist_f = strListTofList(whopaylist);
        List<Float> payforwho_f = strListTofList(payforwho);

        for (int i = 0; i < namelist.size(); i++){
            assets.add(Float.valueOf(keep2digit.format(whopaylist_f.get(i) - payforwho_f.get(i))));
        }
        System.out.println(assets);

        Float Max = Collections.max(assets);
        Float Min = Collections.min(assets);
        int indexMax, indexMin;

        while (!((Max - Min)<1E-2 && (Max - Min)>-1E-2)){
            indexMax = assets.indexOf(Max);
            indexMin = assets.indexOf(Min);
            if((Max+Min) > 0f){
                assets.set(indexMax,Max+Min);
                assets.set(indexMin, 0f);
                debtResult.add(namelist.get(indexMin));
                debtResult.add(namelist.get(indexMax));
                debtResult.add(String.valueOf(-Min));
            }else {
                assets.set(indexMin,Max+Min);
                assets.set(indexMax,0f);
                debtResult.add(namelist.get(indexMin));
                debtResult.add(namelist.get(indexMax));
                debtResult.add(keep2digit.format(Max));
            }
            Max = Collections.max(assets);
            Min = Collections.min(assets);
        }
        return debtResult;
    }




    private void addItemView() {
        View mView = View.inflate(this, R.layout.reportlistlayout, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        TextView textViewpay = mView.findViewById(R.id.NameInputBox1);
        TextView textViewreceive = mView.findViewById(R.id.nameInputBox2);
        TextView howmuch = mView.findViewById(R.id.MoneyInputBox);

        mLinearLayout.addView(mView, params);
        mView.setTag(position);
        mapView.put(position, mView);
        textViewpay.setText(debtResult.get(3*position));
        textViewreceive.setText(debtResult.get((3*position)+1));
        howmuch.setText(debtResult.get((3*position)+2));
        position++;
    }
}

