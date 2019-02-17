package com.arisvng.alepis.a2nd_assigment_db;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.arisvng.alepis.a2nd_assigment_db.MainActivity.db;

public class Main2Activity extends AppCompatActivity {

    TextView textView2, textView3;
    String result, mail, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView2 = findViewById(R.id.textView2);
        mail = getIntent().getStringExtra("mail");
        name = getIntent().getStringExtra("name");



        db = openOrCreateDatabase("unidb2",MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("SELECT * FROM students WHERE mail = '" + mail + "';", null);
        if( cursor.getCount() == 0){
            Toast.makeText(this,"Not found",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                result = "Name: " + cursor.getString(0) + "\n\n"
                        + "Mail: " + cursor.getString(1) + "\n\n"
                        + "Address: " + cursor.getString(2) + "\n\n"
                        + "PhoneNumber: " + cursor.getString(3);
            }
        }
        cursor.close();
        textView2.setText(result);
    }
}
