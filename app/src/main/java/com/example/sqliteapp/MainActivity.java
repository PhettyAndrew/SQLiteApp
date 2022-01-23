package com.example.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText etname, etsurname, etmarks, etId;
    Button btn_add_data, btnViewAll, btnUpdate, btnDelete;
    String strname, strsurname, strmarks,strId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        etname=(EditText)findViewById(R.id.name);
        etsurname=(EditText)findViewById(R.id.surname);
        etmarks=(EditText)findViewById(R.id.marks);
        etId=(EditText)findViewById(R.id.id);
        btn_add_data=(Button)findViewById(R.id.btn_add_data);
        btnViewAll=(Button)findViewById(R.id.btnView_all);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnDelete=(Button)findViewById(R.id.btnDelete);

        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strId=etId.getText().toString().trim();
                Integer deletedRows=myDb.deleteData(strId);
                if (deletedRows > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strId=etId.getText().toString().trim();
                strname=etname.getText().toString().trim();
                strsurname=etsurname.getText().toString().trim();
                strmarks=etmarks.getText().toString().trim();
                boolean isUpdate= myDb.updateData(strId,strname,strsurname,strmarks);
                if(isUpdate == true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(){
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname=etname.getText().toString().trim();
                strsurname=etsurname.getText().toString().trim();
                strmarks=etmarks.getText().toString().trim();
                boolean isInserted=myDb.insertData(strname,strsurname,strmarks);
                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ViewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cursor makes us get the data one by one
                Cursor res=myDb.getAllData();
                //Checking if data exists in database
                if (res.getCount() == 0){
                    showMessage("Error","Nothing Data Found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n");
                    buffer.append("Marks :"+res.getString(3)+"\n\n");
                }
                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
