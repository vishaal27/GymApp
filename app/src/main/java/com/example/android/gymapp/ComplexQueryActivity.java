package com.example.android.gymapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.gymapp.Databases.Customer;
import com.example.android.gymapp.Databases.CustomerContract;
import com.example.android.gymapp.Databases.DatabaseHelper;
import com.example.android.gymapp.Databases.Equipment;
import com.example.android.gymapp.Databases.EquipmentContract;
import com.example.android.gymapp.Databases.MembershipContract;
import com.example.android.gymapp.Databases.TrainerContract;

public class ComplexQueryActivity extends AppCompatActivity
{

    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_query);


        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();


        outputText=(TextView) findViewById(R.id.display_complex);

        int desiredQuery=getIntent().getIntExtra("COMPLEX", 1);

        DatabaseHelper databaseHelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database=databaseHelper.getReadableDatabase();

        switch(desiredQuery)
        {
            case 1: complex1(database);break;
            case 2: complex2(database);break;
            case 3: complex3(database);break;
            case 4: complex4(database);break;
            case 5: complex5(database);break;
            case 6: complex6(database);break;
        }

    }

    private void complex1(SQLiteDatabase database)
    {

        String selectQuery="SELECT COUNT("+ EquipmentContract.EquipmentEntry.COLUMN_EQUIPMENTID+"), "+ EquipmentContract.EquipmentEntry.COLUMN_TYPE+" FROM "+ EquipmentContract.EquipmentEntry.TABLE_NAME+" GROUP BY "+EquipmentContract.EquipmentEntry.COLUMN_TYPE;
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Count:Type\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=cursor.getInt(0)+":"+cursor.getString(1);
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);
    }

    private void complex2(SQLiteDatabase database)
    {
        String selectQuery="SELECT AVG("+ CustomerContract.CustomerEntry.COLUMN_AGE+"), "+ CustomerContract.CustomerEntry.COLUMN_TRAINERID+" FROM "+ CustomerContract.CustomerEntry.TABLE_NAME+" GROUP BY "+ CustomerContract.CustomerEntry.COLUMN_TRAINERID+" ORDER BY "+ CustomerContract.CustomerEntry.COLUMN_TRAINERID;
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Average:Trainer ID\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=String.valueOf(cursor.getFloat(0))+":"+String.valueOf(cursor.getInt(1));
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);
    }

    private void complex3(SQLiteDatabase database)
    {
        String selectQuery="SELECT "+ CustomerContract.CustomerEntry.COLUMN_NAME+", "+ MembershipContract.MembershipEntry.COLUMN_MEMBERSHIPLENGTH+", "+ MembershipContract.MembershipEntry.COLUMN_DATECREATED+" FROM "+ CustomerContract.CustomerEntry.TABLE_NAME+" a INNER JOIN "+ MembershipContract.MembershipEntry.TABLE_NAME+" b ON a."+ CustomerContract.CustomerEntry.COLUMN_CUSTOMERID+"=b."+ MembershipContract.MembershipEntry.COLUMN_CUSTOMERID;
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Name:Membership Length:Date Created\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=cursor.getString(0)+":"+cursor.getString(1)+":"+cursor.getString(2);
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);

    }

    private void complex4(SQLiteDatabase database)
    {
        String selectQuery="SELECT a."+CustomerContract.CustomerEntry.COLUMN_NAME+", a."+ CustomerContract.CustomerEntry.COLUMN_PHONE+", b."+ TrainerContract.TrainerEntry.COLUMN_PHONE+" FROM "+ CustomerContract.CustomerEntry.TABLE_NAME+" a INNER JOIN "+ TrainerContract.TrainerEntry.TABLE_NAME+" b ON a."+ CustomerContract.CustomerEntry.COLUMN_TRAINERID+"=b."+ TrainerContract.TrainerEntry.COLUMN_TRAINERID+" WHERE a."+ CustomerContract.CustomerEntry.COLUMN_AGE+">18";
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Name:Customer Phone:Trainer Phone\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=cursor.getString(0)+":"+cursor.getString(1)+":"+cursor.getString(2);
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);
    }

    private void complex5(SQLiteDatabase database)
    {
        String selectQuery="SELECT MIN("+ TrainerContract.TrainerEntry.COLUMN_AGE+"), "+ TrainerContract.TrainerEntry.COLUMN_NAME+" , "+ TrainerContract.TrainerEntry.COLUMN_LEVEL+" FROM "+ TrainerContract.TrainerEntry.TABLE_NAME+" GROUP BY "+ TrainerContract.TrainerEntry.COLUMN_LEVEL;
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Min Age:Name:Level\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=String.valueOf(cursor.getInt(0))+":"+cursor.getString(1)+":"+cursor.getString(2);
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);
    }

    private void complex6(SQLiteDatabase database)
    {
        String selectQuery="SELECT b."+ MembershipContract.MembershipEntry.COLUMN_EXPIRYDATE+", c."+ TrainerContract.TrainerEntry.COLUMN_EMAIL+" FROM "+CustomerContract.CustomerEntry.TABLE_NAME+" a INNER JOIN "+ MembershipContract.MembershipEntry.TABLE_NAME+" b on a."+ CustomerContract.CustomerEntry.COLUMN_CUSTOMERID+"=b."+ MembershipContract.MembershipEntry.COLUMN_CUSTOMERID+" INNER JOIN "+ TrainerContract.TrainerEntry.TABLE_NAME+" c on a."+ CustomerContract.CustomerEntry.COLUMN_TRAINERID+"=c."+ TrainerContract.TrainerEntry.COLUMN_TRAINERID;
        Cursor cursor=database.rawQuery(selectQuery, null);

        String output="";
        output+="Membership Expiry Date:Trainer Email\n";

        if(cursor.moveToFirst())
        {
            do
            {
                String ret=cursor.getString(0)+":"+cursor.getString(1);
                Log.d("database", ret);
                output+=ret;
                output+="\n";
            }while(cursor.moveToNext());
        }

        Log.d("database", output);
        outputText.setText(output);
    }
}
