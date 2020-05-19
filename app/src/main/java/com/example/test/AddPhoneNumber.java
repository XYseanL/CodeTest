package com.example.test;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPhoneNumber extends AppCompatActivity {
    //define
    Button buttonBack;
    Button buttonSave;
    EditText editTextFN,
            editTextLN,
            editTextimage,
            editTextEmail,
            editTextPhone,
            editTextAddress;
    //database variable
    private MyDB datebase;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnumber);
        //database object
        datebase = new MyDB(this,"PhoneNumber",null,1);
        //view object
        buttonBack = findViewById(R.id.back);
        buttonSave = findViewById(R.id.save);
        editTextFN = findViewById(R.id.edit_firstN);
        editTextLN = findViewById(R.id.edit_lastN);
        editTextimage = findViewById(R.id.edit_image);
        editTextEmail = findViewById(R.id.edit_Email);
        editTextPhone = findViewById(R.id.edit_phoneNBer);
        editTextAddress = findViewById(R.id.edit_address);

        //set up back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set back to main activity
                Intent intent = new Intent(AddPhoneNumber.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //define event when save is clicked
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// define strings by input
                String edit_FN = editTextFN.getText().toString(),
                        edit_LN = editTextLN.getText().toString(),
                        edit_image = editTextimage.getText().toString(),
                        edit_Email = editTextEmail.getText().toString(),
                        edit_Phone = editTextPhone.getText().toString(),
                        edit_address = editTextAddress.getText().toString();

// determine if this is an empty contact
                if ((!edit_FN.equals("")) && (!edit_LN.equals("") || !edit_image.equals("") || !edit_Email.equals("") || !edit_Phone.equals(""))){
                    //define if contact already exists
                    int count = 0;
                    //define database object
                    db = datebase.getWritableDatabase();
                    //define cursor to check database item
                    Cursor cursor = db.query("PhoneNumber",null,null,null,null,null,null);
                    //determine if database is empty
                    if (cursor.moveToFirst()){
                        do{
// retrieve and define fro the database
                            String FN = cursor.getString(cursor.getColumnIndex("firstN"));
                            String LN = cursor.getString(cursor.getColumnIndex("lastN"));
                            String image = cursor.getString(cursor.getColumnIndex("image"));
                            String Email = cursor.getString(cursor.getColumnIndex("Email"));
                            String Phone = cursor.getString(cursor.getColumnIndex("PhoneNBer"));
                            String address = cursor.getString(cursor.getColumnIndex("Adrss"));

                            //determine if information is already in database
                            if ((FN.equals(edit_FN) && LN.equals(edit_LN)) && (image.equals(edit_image) && Email.equals(edit_Email)) &&
                                    (Phone.equals(edit_Phone) && address.equals(edit_address) || FN.equals(edit_FN))){
// if there is any same ones, increase
                                count ++;
                            }
                        }while (cursor.moveToNext());
                    }


                    if (count == 0){
// define writeable database
                        SQLiteDatabase db = datebase.getWritableDatabase();
// create contentvalue object
                        ContentValues values = new ContentValues();

                        values.put("FN",edit_FN);
                        values.put("LN", edit_LN);
                        values.put("image", edit_image);
                        values.put("Email", edit_Email);
                        values.put("Phone", edit_Phone);
                        values.put("address",edit_address);


                        db.insert("PhoneNumber",null,values);

                        values.clear();

                        Toast.makeText(AddPhoneNumber.this,"Saved successfully！",Toast.LENGTH_SHORT).show();
// jump back to main activity
                        Intent intent = new Intent(AddPhoneNumber.this,MainActivity.class);
                        startActivity(intent);
                    }else{

                        Toast.makeText(AddPhoneNumber.this,"Contact already exists！",Toast.LENGTH_SHORT).show();
                    }
                }else{
// warning if missing information
                    Toast.makeText(AddPhoneNumber.this,"Please fullfill the empty space！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}