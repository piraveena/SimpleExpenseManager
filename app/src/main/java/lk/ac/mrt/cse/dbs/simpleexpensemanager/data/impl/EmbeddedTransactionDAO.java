package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by PIRAVEENA on 11/20/2016.
 */


public class EmbeddedTransactionDAO implements TransactionDAO {

    //transaction table


    private DBHandler dbhandler;
    SQLiteDatabase db;

    public EmbeddedTransactionDAO(SQLiteDatabase db){

      this.db=db;
    }


    @Override
    //insert values into transaction table
    public void logTransaction(Date date_, String accountNo, ExpenseType expenseType_, double amount_){
        db = dbhandler.getWritableDatabase();

        db.beginTransaction();

        try {
            String insert_query = "INSERT INTO Account_Transaction (accounttNo,expenseType,amount,date) VALUES (?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(insert_query);

            statement.bindString(1,accountNo);
            statement.bindLong(2,(expenseType_ == ExpenseType.EXPENSE) ? 0 : 1);
            statement.bindDouble(3,amount_);
            statement.bindLong(4,date_.getTime());

            statement.executeInsert();

        } catch (SQLException e) {
            e.printStackTrace();
            // Log.d(TAG, "Error while trying to add post to database");
        } finally {

            db.endTransaction();
        }

    }

    @Override
    //get all transactions
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactions = new ArrayList<>();

        String TRANSACTION_DETAIL_SELECT_QUERY = "SELECT * FROM Account_Transaction";

        db = dbhandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(TRANSACTION_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Transaction trans=new Transaction(
                            new SimpleDateFormat("MM/dd/yyyy").parse(cursor.getString(cursor.getColumnIndex("date"))),
                            cursor.getString(cursor.getColumnIndex("accounttNo")),
                            (cursor.getInt(cursor.getColumnIndex("expenseType")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                   cursor.getDouble(cursor.getColumnIndex("amount")));




                    transactions.add(trans);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }


        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        //int size =int numRows = DatabaseUtils.queryNumEntries( Table_1);;


        List<Transaction> transdetail = new ArrayList<>();

        String TRANS_DETAIL_SELECT_QUERY = "SELECT * FROM Account_Transaction LIMIT"+limit;


        Cursor cursor = db.rawQuery(TRANS_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Transaction trans=new Transaction(
                            new SimpleDateFormat("MM/dd/yyyy").parse(cursor.getString(cursor.getColumnIndex(dbhandler.date))),
                            cursor.getString(cursor.getColumnIndex(dbhandler.accounttNo)),
                            (cursor.getInt(cursor.getColumnIndex("expenseType")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                            cursor.getDouble(cursor.getColumnIndex(dbhandler.amount)));


                    transdetail.add(trans);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return  transdetail;
    }





}

