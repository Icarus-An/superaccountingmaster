package com.example.superaccountingmaster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class NewItem1 extends LinearLayout {
    private static final String TAG = "MainActivity";
    private LinearLayout Nameinputbox;
    private LinearLayout Moneyinputbox;
    public EditText editName1;
    public EditText editMoney1;
    public int i=0;
    public int j=1000;
    public int k=0;
    public StringBuffer buffer = new StringBuffer();

    private Context context;
    public String[] editNames;
    public String[] editMoney;

    TextView readtextid;
    TextView readtextmoney;

    public NewItem1(Context context) {
        super(context);
    }


    public void readall()
    {
        Log.d(TAG, "i="+i);
        readtextid =(TextView) findViewById(i);
        Log.d(TAG, "addNewItem: --------"+readtextid.getText().toString());



    }






    //新增Item


    //删除当前Item
    private void deleteItem(){
        ViewGroup viewGroup= (ViewGroup) getParent();
        int index=viewGroup.indexOfChild(this);
        viewGroup.removeViewAt(index);

    }

}
