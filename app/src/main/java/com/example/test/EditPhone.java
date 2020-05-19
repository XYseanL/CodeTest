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

public class EditPhone extends AppCompatActivity {
    Button buttonCheck,
            buttonSave;
    EditText editTextFN,
            editTextLN,
            editTextImage,
            editTextEmail,
            editTextPhone,
            editTextAddress;

    private MyDB datebase;
    SQLiteDatabase db;
    int i;
    MyDB dbHelper = new MyDB(this,"PhoneNumber",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphone);
        buttonSave = findViewById(R.id.check_save);
        buttonCheck = findViewById(R.id.check_back);
        editTextFN = findViewById(R.id.check_edit_FN);
        editTextLN = findViewById(R.id.check_edit_LN);
        editTextImage = findViewById(R.id.check_edit_image);
        editTextEmail = findViewById(R.id.check_edit_Email);
        editTextPhone = findViewById(R.id.check_edit_phone);
        editTextAddress = findViewById(R.id.check_edit_address);

        datebase = new MyDB(this,"PhoneNumber",null,1);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPhone.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //receive data
        Intent intent = getIntent();
        editTextFN.setText(intent.getStringExtra("extra_FN"));
        editTextLN.setText(intent.getStringExtra("extra_LN"));
        editTextImage.setText(intent.getStringExtra("extra_image"));
        editTextEmail.setText(intent.getStringExtra("extra_Email"));
        editTextPhone.setText(intent.getStringExtra("extra_Phone"));
        editTextAddress.setText(intent.getStringExtra("extra_address"));
        i = intent.getIntExtra("extra_i",0);
        final String editName =  intent.getStringExtra("extra_name");


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                //receive input
                String edFN = editTextFN.getText().toString(),
                        edLN = editTextLN.getText().toString(),
                        edimage = editTextImage.getText().toString(),
                        edEmail = editTextEmail.getText().toString(),
                        edPhone = editTextPhone.getText().toString(),
                        edAddress = editTextAddress.getText().toString();

                db = datebase.getWritableDatabase();
                int ID = 0;
                Cursor cursor = db.query("PhoneNumber",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
// retrieve name in database
                        String name = cursor.getString(cursor.getColumnIndex("name"));
// check name
                        if (name.equals(editName)){
// retrive the id if name is same
                            ID = cursor.getInt((cursor.getColumnIndex("id")));
                            break;
                        }
                    }while (cursor.moveToNext());
                }
                values.put("FN",edFN);
                values.put("LN", edLN);
                values.put("image", edimage);
                values.put("Email", edEmail);
                values.put("Phone", edPhone);
                values.put("address",edAddress);

// update
                db.update("PhoneNumber",values,"id = ?",new String[]{String.valueOf(ID)});
                values.clear();
                Toast.makeText(EditPhone.this,"Saved Successfully！",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EditPhone.this,MainActivity.class);
                startActivity(intent1);
            }

        });


    }

}
