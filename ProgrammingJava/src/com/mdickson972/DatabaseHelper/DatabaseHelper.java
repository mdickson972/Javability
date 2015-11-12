package com.mdickson972.DatabaseHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class that handles the creation and management of the SQLite Database
 * 
 * References sources from: 'Android Application Development for Dummies' - Donn
 * Felker 'Android SQLite Database' - profgustin -
 * https://www.youtube.com/watch?v=j-IV87qQ00M
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// Class Variables
	public static String DB_PATH;
	public static String DB_NAME;
	public SQLiteDatabase database;
	public final Context context;

	/**
	 * Argument Based Constructor
	 * 
	 * @param context
	 * @param DbName
	 */
	public DatabaseHelper(Context context, String DbName) {
		super(context, DbName, null, 1);
		this.context = context;

		String packageName = context.getPackageName();
		DB_PATH = String.format("//data//data//%s//databases//", packageName);
		DB_NAME = DbName;
		openDataBase();
	}

	/**
	 * Getter for the database object
	 * 
	 * @return
	 */
	public SQLiteDatabase getDb() {
		return database;
	}

	/**
	 * Uses checkDataBase method to see if a database already exists and if not
	 * creates one
	 */
	public void createDataBase() {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e(this.getClass().toString(), "Copying error");
				throw new Error("Error copying database!");
			}
		} else {
			Log.i(this.getClass().toString(), "Database already exists");
		}
	}

	/**
	 * Returns a boolean value to determine if a database currently exists
	 * 
	 * @return
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDb = null;
		try {
			String path = DB_PATH + DB_NAME;
			checkDb = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLException e) {
			Log.e(this.getClass().toString(), "Error while checking db");
		}

		if (checkDb != null) {
			checkDb.close();
		}
		return checkDb != null;
	}

	/**
	 * Copies the current database
	 * 
	 * @throws IOException
	 */
	private void copyDataBase() throws IOException {

		InputStream externalDbStream = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream localDbStream = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = externalDbStream.read(buffer)) > 0) {
			localDbStream.write(buffer, 0, bytesRead);
		}

		localDbStream.close();
		externalDbStream.close();

	}

	/**
	 * Opens the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	public SQLiteDatabase openDataBase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		if (database == null) {
			createDataBase();
			database = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
		}
		return database;
	}

	/**
	 * Closes the database
	 */
	@Override
	public synchronized void close() {
		if (database != null) {
			database.close();
		}
		super.close();
	}

	/**
	 * Adds a new user record to the database taking the details in as
	 * parameters.
	 * 
	 * @param db
	 * @param table
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @return
	 */
	public long newUser(SQLiteDatabase db, String table, String username,
			String password, String emailAddress) {

		ContentValues initialValues = new ContentValues();
		initialValues.put("Username", username);
		initialValues.put("Password", password);
		initialValues.put("EmailAddress", emailAddress);
		return db.insert(table, null, initialValues);
	}

	public void updateUserPassword(SQLiteDatabase db, Integer userId, String password) {

		Cursor csr = db.rawQuery("Update UserData set Password = '" + password
				+ "' where UserId=" + userId, null);
		csr.getCount();
		Log.i("Result Write Status", "Record Updated");

		csr.close();
	}

	/**
	 * Creates a new user results record for an assessment in the database
	 * taking the details in as parameters.
	 * 
	 * @param db
	 * @param table
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @return
	 */
	public void newResultsTable(SQLiteDatabase db, String table,
			Integer userId, String topic, Integer last, Integer attempt2,
			Integer attempt3, Integer attempt4, Integer best) {

		Cursor csr = db.rawQuery("Insert into " + table + " values (" + userId
				+ ", " + topic + ", " + last + ", " + attempt2 + ", "
				+ attempt3 + ", " + attempt4 + ", " + best + ") ", null);
		csr.getCount();
		Log.i("Result Write Status", "Record Created");

		csr.close();
	}

	/**
	 * updates a users results for an assessment in the database taking the
	 * details in as parameters.
	 * 
	 * @param db
	 * @param table
	 * @param username
	 * @param password
	 * @param emailAddress
	 */
	public void updateResultsTable(SQLiteDatabase db, String table,
			Integer userId, String topic, Integer last, Integer attempt2,
			Integer attempt3, Integer attempt4, Integer best) {

		Cursor csr = db.rawQuery("Update " + table + " set Recent1=" + last
				+ ", Recent2=" + attempt2 + ", Recent3=" + attempt3
				+ ", Recent4=" + attempt4 + ", BestScore=" + best
				+ " where UserId=" + userId + " and Topic=" + topic, null);
		csr.getCount();
		Log.i("Result Write Status", "Record Updated");

		csr.close();
	}

	public long deleteRecord(SQLiteDatabase db, String table, int userId,
			String topic) {
		// Query to delete the current record for this topic and user
		return db.delete(table, "UserId = " + userId + " and Topic = " + topic,
				null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
