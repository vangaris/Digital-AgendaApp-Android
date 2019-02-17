package com.arisvng.alepis.a2nd_assigment_db;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //initializing the database
    public static SQLiteDatabase db;
    //initializing varaliables
    EditText nameEditText;
    EditText mailEditText;
    EditText addressEditText;
    EditText phoneNumberEditText;
    String toastFrom2Activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a connection between java class and R
        nameEditText = findViewById(R.id.nameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        mailEditText = findViewById(R.id.mailEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

//         toastFrom2Activity = getIntent().getStringExtra("toast");
//         Toast.makeText(this,toastFrom2Activity,Toast.LENGTH_LONG).show();

        //creating database
        db = openOrCreateDatabase("unidb2",MODE_PRIVATE,null);

        //creating the tablew students with variables
        db.execSQL("CREATE TABLE IF NOT EXISTS `students` (\n" +
                "`name` TEXT,\n" +
                "\t`mail` TEXT,\n" +
                "\t`address` TEXT,\n" +
                "\t`phoneNumber` TEXT,\n" +
                "PRIMARY KEY (`mail`)\n" +
                ");");

        //inserting records
        db.execSQL("INSERT OR IGNORE INTO 'students' VALUES ('Paris','paris@gmail.com','athinon 31','6972589856')");
        db.execSQL("INSERT OR IGNORE INTO 'students' VALUES ('Giannis','giannis@hotmail.com',' galatsiou 29','6948536895')");
        db.execSQL("INSERT OR IGNORE INTO 'students' VALUES ('Dimitris','dim@gmail.com','afaias 25','6935897412')");
    }


    //creating a method in order to show a message box
    public void showMessage(String title, String message) {
        //creating an object for message(like message box)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting title - message and if it ll be cancelable
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        //Showing the content of builder
        builder.show();
    }

    //event click find
    public void find(View view) {
        if (mailEditText.getText().toString().equals("")){
            showMessage("Sorry", "Please enter email");

        } else {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("mail",mailEditText.getText().toString());
            intent.putExtra("name",nameEditText.getText().toString());
            startActivity(intent);

        }

    }

    //event click delete
    public void delete(View view) {
        if (mailEditText.getText().toString().equals("")){
            showMessage("Sorry", "Please enter email");
        } else{
            db.execSQL("DELETE FROM students WHERE mail = '" + mailEditText.getText().toString() + "';");
            Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show();
        }

    }

    //event click update
    public void update(View view) {
        if (mailEditText.getText().toString().equals("")){
            showMessage("Sorry", "Please enter  email");
        } else {
            try{
                db.execSQL("UPDATE students SET name = '" + nameEditText.getText().toString() + "', " +
                        "address = '"+ addressEditText.getText().toString() +"', " +
                        "phoneNumber ='"+ phoneNumberEditText.getText().toString() +"' " +
                        "WHERE mail = '"+ mailEditText.getText().toString() +"';");
                Toast.makeText(this,"updated",Toast.LENGTH_LONG).show();
            }catch (SQLException e){
                showMessage("Error", e.toString());
            }
        }


    }
    //event click add
    public void add(View view) {

        if (nameEditText.getText().toString().equals("") || mailEditText.getText().toString().equals("") || addressEditText.getText().toString().equals("") || phoneNumberEditText.getText().toString().equals("")){
            showMessage("Sorry", "Please fill out the form");
        }else {
            //inserting records
            db.execSQL("INSERT INTO 'students' VALUES ('"+ nameEditText.getText().toString() +"'," +
                    "'"+ mailEditText.getText().toString() +"'," +
                    "'"+ addressEditText.getText().toString() +"'," +
                    "'"+ phoneNumberEditText.getText().toString() +"')");

            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        }

    }

    //event click fot checking
    public void check(View view) {
        //creation of buffer in order to save the data
        StringBuffer buffer = new StringBuffer();
        //creation of cursor (pointer) which'll point the columns
        Cursor cursor = db.rawQuery("SELECT * FROM students ORDER BY (name) ASC;", null);
        //if has no records
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No records Found", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                //adding with append content to the buffer
                buffer.append("Name : " + cursor.getString(0) + "\n");
                buffer.append("E-mail : " + cursor.getString(1) + "\n");
                buffer.append("Address : " + cursor.getString(2) + "\n");
                buffer.append("Phone Number : " + cursor.getString(3) + "\n");
                buffer.append("------------------------------------\n");
            }
            //saving buffer as a String
            String data = buffer.toString();
            //calling method show
            showMessage("records", data);
        }
        //stopping - closing cursor, bcs is trying to find another column
        cursor.close();
    }
}
