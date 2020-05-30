package com.example.superaccountingmaster;



import com.example.superaccountingmaster.DBOpenHelper;
import com.example.superaccountingmaster.pubFun;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegistActivity extends AppCompatActivity {
    private EditText editPhone;
    private EditText editPwd;
    private Button btnRegist;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPwd = (EditText) findViewById(R.id.editPwd);
        btnRegist = (Button) findViewById(R.id.btnRegist);
    }


    public void OnMyRegistClick(View v)
    {
        boolean isTrue = true;
        if(pubFun.isPhoneNumberValid(editPhone.getText().toString()) == false){
            isTrue = false;
            Toast.makeText(this, R.string.format, Toast.LENGTH_SHORT).show();
            return;
        }
        if(pubFun.isEmpty(editPwd.getText().toString())){
            isTrue = false;
            Toast.makeText(this, R.string.pass_null, Toast.LENGTH_SHORT).show();
            return;
        }

        if(isTrue = true){
            //call DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor c = db.query("user_tb",null,"userID=?",new String[]{editPhone.getText().toString()},null,null,null);
            if(c!=null && c.getCount() >= 1){
                Toast.makeText(this, R.string.exist, Toast.LENGTH_SHORT).show();
                c.close();
            }
            else{
                //insert data
                ContentValues values= new ContentValues();
                values.put("userID",editPhone.getText().toString());
                values.put("pwd",editPwd.getText().toString());
                long rowid = db.insert("user_tb",null,values);

                Toast.makeText(this, R.string.regist_message, Toast.LENGTH_SHORT).show();
                this.finish();
            }
            db.close();
        }else{
            return;
        }
    }
}

