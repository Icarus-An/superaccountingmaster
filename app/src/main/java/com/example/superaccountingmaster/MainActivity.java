package com.example.superaccountingmaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.NameList;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Activity_for_who";
    private TextView msum;
    private TextView msr;
    private TextView mzc;
    private Double summ;
    DBOpenHelper bill;
    private AlertDialog alertDialog_AddRecord;
    public Double buffer=0.0 ;
    public Double buffer1=0.0 ;
    public  Integer MAXX;
    TextView AAsumView;
    int AAsum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msum = (TextView) findViewById(R.id.t_total);
        msr = (TextView) findViewById(R.id.t_income);
        mzc = (TextView) findViewById(R.id.t_outcome);
        AAsumView = (TextView) findViewById(R.id.t_total_AA);
        ImageView add = (ImageView) findViewById(R.id.imageView);
        registerForContextMenu(add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
        bill = new DBOpenHelper(this,"qianbao.db",null,1);

        mLinearLayout = (LinearLayout) findViewById(R.id.LinearShowResult);

//        Bundle backbundle = getIntent().getExtras();
        SharedPreferences debtResultList = getSharedPreferences("debtResult",MODE_PRIVATE);
        if (debtResultList != null) {
//          debtResultshow = backbundle.getStringArrayList("NameAndMoneyDebt");
//          AAsum = backbundle.getInt("totalmoneyData", 0);
            loadArray();
            Log.e(TAG, "debtResultshow: --------"+ debtResultshow.toString());
            for(int i=0;i<(debtResultshow.size()/3);i++)
            {
                addItemView();
            }
            Log.e(TAG, "AAsum: --------"+ AAsum);
            AAsumView.setText(String.valueOf(AAsum)+".00");
            debtResultshow.clear();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AA:
                Intent intentAA = new Intent(getApplicationContext(), Activity_who_pay.class);
                startActivity(intentAA);

                return true;
            case R.id.menuItemRU:
                Intent intent = new Intent(this, Choice.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                intent.putExtra("strType", 0);
                startActivity(intent);
                return true;
            case R.id.menuItemCHU:
                Intent intent1 = new Intent(this, Choice.class);
                Bundle bundle1 = new Bundle();
                intent1.putExtras(bundle1);
                intent1.putExtra("strType", 1);
                startActivity(intent1);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void loadArray()
    {
        SharedPreferences mSharedPreference = getSharedPreferences("debtResult",MODE_PRIVATE);

        int size = mSharedPreference.getInt("Status_size", 0);
        for(int i=0;i<size;i++)
        {
            debtResultshow.add(mSharedPreference.getString("Status_" + i, null));
        }
        AAsum = mSharedPreference.getInt("totalmoneyData",0);
    }

    public void SpendingA(View v){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putDouble("buffer", buffer);
        bundle.putDouble("buffer1", buffer1);
        bundle.putInt("MAXX",MAXX);
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this, SpendingActivity.class);
        this.startActivity(intent);
    }

    public void sx(){

        Cursor res = bill.getAllData();
        Double buffersr=0.0;
        Double bufferzc=0.0;
        Double buffer2 ;
        Integer MAX=0;
        Integer var=0;

        String strType = "";
        while (res.moveToNext()) {
            strType = res.getString(res.getColumnIndex("Type"));
            var=res.getInt(res.getColumnIndex("_id"));
            if(var>MAX){
                MAX=var;
            }
            if(strType.equals("0")){
                buffersr=buffersr+res.getDouble(7);
            }
            else{
                bufferzc=bufferzc+res.getDouble(7);
            }
        }
        buffer2 = buffersr-bufferzc;
        msum.setText(String.valueOf(buffer2));
        msr.setText(String.valueOf(buffersr));
        mzc.setText(String.valueOf(bufferzc));
        buffer=buffersr;
        buffer1=bufferzc;
        MAXX=MAX;
    }
    @Override
    public void onResume() {
        super.onResume();
        sx();

    }

    //add new features
    List<String> debtResultshow = new ArrayList<>();
    DecimalFormat keep2digit = new DecimalFormat("######0.00");

    private LinearLayout mLinearLayout;
    private int position;
    private Map<Integer, View> mapView = new HashMap<>();

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
        textViewpay.setText(debtResultshow.get(3*position));
        textViewreceive.setText(debtResultshow.get((3*position)+1));
        howmuch.setText(debtResultshow.get((3*position)+2));
        position++;
    }

    public void LoginA(MenuItem item) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        this.startActivity(intent);
    }

    public void delete(MenuItem item) {
        final String[] items ;
        items=getResources().getStringArray(R.array.de_type_array);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.de_select);
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bill.deleteData();
                sx();
                alertDialog_AddRecord.dismiss();
            }
        });
        alertDialog_AddRecord = alertBuilder.create();
        alertDialog_AddRecord.show();
    }
}
