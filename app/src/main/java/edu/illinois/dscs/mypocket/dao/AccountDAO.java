package edu.illinois.dscs.mypocket.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.dscs.mypocket.model.Account;

/**
 * Created by denniscardoso on 4/16/15.
 */
public class AccountDAO {

    private SQLiteDatabase database;
    private DatabaseHandler dbHandler;
    private String[] allAccount = {DatabaseHandler.KEY_TRANS_ID,
                                   DatabaseHandler.KEY_TRANS_TYPE,
                                   DatabaseHandler.KEY_DESCRIPTION,
                                   DatabaseHandler.KEY_CREATION_DATE,
                                   DatabaseHandler.KEY_CATEGORY_ID,
                                   DatabaseHandler.KEY_ACCOUNT_ID};

    /**
     * DAO Constructor for transactions.
     *
     * @param context the database context in the phone.
     */
    public AccountDAO(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    /**
     * Opens the database handler.
     *
     * @throws java.sql.SQLException if the database cannot be reached.
     */
    public void open() throws SQLException {
        database = dbHandler.getWritableDatabase();
    }

    /**
     * Closes the database handler.
     */
    public void close() {
        dbHandler.close();
    }

    /**
     * Creates a Transaction object from all the data obtained from user interactions,
     * inserting it into the database.
     *
     * @param type         an integer associated with the transaction type (expense or income).
     * @param description  the transaction description.
     * @param value        the transaction value (always non-negative).
     * @param creationDate the transaction's creation date (not necessarily today).
     * @param categoryID   the ID of the category associated with the transaction.
     * @param accountID    the ID of the account that contains the transaction.
     * @return a Transaction object whose information is already inside the database.
     */
    public Transaction createTransaction(int type, String description, double value,
                                         String creationDate, int categoryID, int accountID) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_TRANS_TYPE, type);
        values.put(DatabaseHandler.KEY_DESCRIPTION, description);
        values.put(DatabaseHandler.KEY_TRANS_VALUE, value);
        values.put(DatabaseHandler.KEY_CREATION_DATE, creationDate);
        values.put(DatabaseHandler.KEY_CATEGORY_ID, categoryID);
        values.put(DatabaseHandler.KEY_ACCOUNT_ID, accountID);
        long insertId = database.insert(DatabaseHandler.TABLE_TRANSACTION, null,
                values);
        Cursor cursor = database.query(DatabaseHandler.TABLE_TRANSACTION,
                allTransaction, DatabaseHandler.KEY_TRANS_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Transaction newTransaction = cursorToTransaction(cursor);
        cursor.close();
        return newTransaction;
    }

    /**
     * Deletes a given transaction from the database.
     *
     * @param transaction the transaction marked for deletion.
     */
    public void deleteTransaction(Transaction transaction) {
        long id = transaction.getTransactionID();
        System.out.println("Transaction deleted with id: " + id);
        database.delete(DatabaseHandler.TABLE_TRANSACTION, DatabaseHandler.KEY_TRANS_ID
                + " = " + id, null);
    }

    /**
     * Gets all transactions from the database.
     *
     * @return a list with all items from the Transactions table turned into Transaction objects.
     */
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        Cursor cursor = database.query(DatabaseHandler.TABLE_TRANSACTION,
                allTransaction, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction Transaction = cursorToTransaction(cursor);
            transactions.add(Transaction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
    }

    /**
     * Turns a cursor item into a full transaction.
     *
     * @param cursor the cursor with a given transaction in the database.
     * @return a transaction equivalent to the one pointed by the cursor.
     */
    private Transaction cursorToTransaction(Cursor cursor) {
        Transaction transaction = new Transaction(0, 0, null, 0.0, null, 0, 0);
        transaction.setTransactionID(cursor.getInt(0));
        transaction.setType(cursor.getInt(1));
        transaction.setDescription(cursor.getString(3));
        transaction.setCreationDate(cursor.getString(4));
        transaction.setCategoryID(cursor.getInt(5));
        transaction.setAccountID(cursor.getInt(6));
        return transaction;
    }

}