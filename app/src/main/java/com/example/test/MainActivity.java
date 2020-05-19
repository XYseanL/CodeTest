package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Phone> phones= new ArrayList<>();
    ListAdapter adapter;
    SQLiteDatabase db;
    MyDB dbHelper = new MyDB(this,"PhoneNumber",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button buttonAdd = findViewById(R.id.button_add);
        ListView listViewPhone = findViewById(R.id.list_list);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddPhoneNumber.class);
                startActivity(intent);
            }
        });

        db = dbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("PhoneNumber",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String firstN = cursor.getString(cursor.getColumnIndex("firstN"));
                String lastN = cursor.getString(cursor.getColumnIndex("lastN"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                String email = cursor.getString(cursor.getColumnIndex("Email"));
                String phoneNBer = cursor.getString(cursor.getColumnIndex("PhoneNBer"));
                String Adrss = cursor.getString(cursor.getColumnIndex("Adrss"));

                Phone phoneInfo;
                phoneInfo = new Phone(firstN,lastN,image,email,phoneNBer,Adrss);
                phones.add(phoneInfo);
            }while (cursor.moveToNext());
        }
        adapter = new ListAdapter(MainActivity.this,android.R.layout.simple_list_item_1, phones);
        listViewPhone.setAdapter(adapter);


        listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Phone phone_check = phones.get(i);
                String checkPhone = phone_check.getPhoneNBer(),
                        checkFirstN = phone_check.getFirstN(),
                        checkLastN = phone_check.getLastN(),
                        checkEmail = phone_check.getEmail(),
                        checkAddress = phone_check.getAddress(),
                        checkImage = phone_check.getImage();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage(
                        " First Name：" + checkFirstN + "\n" +
                                " Last Name：" + checkLastN + "\n" +
                                " Image：" + checkImage + "\n" +
                                " Email：" + checkEmail + "\n" +
                                " Phone：" + checkPhone + "\n" +
                                " Address：" + checkAddress + "\n" +
                builder.setTitle(" Check Contacts"));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
// Intent intent = new Intent(MainActivity.this,EditPhone.class);
// intent.putExtra("extra_name",checkName);
// intent.putExtra("extra_phone1",checkPhone1);
// intent.putExtra("extra_phone2",checkPhone2);
// intent.putExtra("extra_housePhone",checkHousePhone);
// intent.putExtra("extra_officePhone",checkOfficePhone);
// intent.putExtra("extra_address",checkAddress);
// intent.putExtra("extra_remark",checkRemark);
// startActivity(intent);
            }
        });
        listViewPhone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeleteDialog(i);
                return true;
            }
        });


        adapter.setOnItemChangesClickListener(new ListAdapter.onItemChangesListener() {
            @Override
            public void onChangesClick(int i) {
                Phone phone_check = phones.get(i);
                String checkPhone = phone_check.getPhoneNBer(),
                        checkFirstN = phone_check.getFirstN(),
                        checkLastN = phone_check.getLastN(),
                        checkEmail = phone_check.getEmail(),
                        checkAddress = phone_check.getAddress(),
                        checkImage = phone_check.getImage();
                Intent intent = new Intent(MainActivity.this, EditPhone.class);
                intent.putExtra("extra_firstN",checkFirstN);
                intent.putExtra("extra_lastN",checkLastN);
                intent.putExtra("extra_image",checkImage);
                intent.putExtra("extra_Email",checkEmail);
                intent.putExtra("extra_Phone",checkPhone);
                intent.putExtra("extra_address",checkAddress);
                intent.putExtra("extra_i",i);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Edit",Toast.LENGTH_SHORT).show();
            }
        });




    }
    private void DeleteDialog(final int positon){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Delete Contact");
        builder.setTitle("Warning");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Phone phone_check = phones.get(positon);
                String checkPhone = phone_check.getPhoneNBer(),
                        checkFirstN = phone_check.getFirstN(),
                        checkLastN = phone_check.getLastN(),
                        checkEmail = phone_check.getEmail(),
                        checkAddress = phone_check.getAddress(),
                        checkImage = phone_check.getImage();
                phones.remove(positon);
                adapter.notifyDataSetChanged();  //更新listView
                db.delete("PhoneNumber","firstN = ? and lastN = ? and image = ? and Email = ? and PhoneNBer = ? and address = ?",new String[]{checkFirstN,checkLastN,checkImage,checkEmail,checkPhone,checkAddress});
                Toast.makeText(MainActivity.this,"Successful Deleted！",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
