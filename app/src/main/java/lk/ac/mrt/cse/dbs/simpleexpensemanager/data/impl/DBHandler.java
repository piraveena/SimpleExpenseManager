package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import  android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by PIRAVEENA on 11/20/2016.
 */


public   class DBHandler extends SQLiteOpenHelper {
    private static final int version = 1;
    private static final String name = "140431J";
    private  static  DBHandler instance;

    //account table
    static final String Table_1 = "account";
    static String accountNo = "accountNo";
    static String bankName = "bankName";
    static String accountHolderName = "accountHolderName";
    static String balance = "balance";

    //transaction table
    static final String Table_2 = "account_transaction";
    static String date = "date";
    static String accounttNo = "accountNo";
    static String expenseType = "expenseType";
    static String amount = "amount";


    private static String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + Table_1 +
            "(" +
            accountNo + " INTEGER PRIMARY KEY ," +
            bankName + " TEXT," +
            accountHolderName + " TEXT," +
            balance + "REAL" +
            ")";

    private static String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + Table_2 +
            "(" +
            date + "TEXT," +
            accounttNo + " INTEGER PRIMARY KEY ," +
            expenseType + " TEXT," +
            amount + "REAL," +
            ")";

    public static synchronized DBHandler getHelper(Context context) {
        if (instance == null)
            instance = new DBHandler(context);
        return instance;
    }

    public DBHandler(Context context) {
        super(context, name, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + Table_1);
        db.execSQL("DROP TABLE IF EXISTS " + Table_2);

        // create new tables
        onCreate(db);
    }



}

