package com.chronosystems.service.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.chronosystems.entity.Device;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android";

	// Login table name
	private static final String TABLE_LOGIN = "device";
	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	private static final String TABLE_LOCATION = "location";
	private static final String KEY_LAST_LATITUDE = "last_latitude";
	private static final String KEY_LAST_LONGITUDE = "last_longitude";

	public DatabaseHandler(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(final SQLiteDatabase db) {
		final String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);

		final String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_LAST_LATITUDE + " TEXT,"
				+ KEY_LAST_LONGITUDE + " TEXT,"
				+ KEY_UID + " TEXT)";
		db.execSQL(CREATE_LOCATION_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(final String name, final String email, final String uid, final String created_at) {
		final SQLiteDatabase db = this.getWritableDatabase();

		final ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Id
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}
	/**
	 * Storing location details in database
	 * */
	public void addLastLocation(final Long id, final Double latitude, final Double longitude) {

		final ContentValues values = new ContentValues();
		values.put(KEY_LAST_LATITUDE, latitude);
		values.put(KEY_LAST_LONGITUDE, longitude);
		values.put(KEY_UID, id);

		if (getRowCountLocation() > 0) {
			// Update Row
			final SQLiteDatabase db = this.getWritableDatabase();
			db.update(TABLE_LOCATION, values, null, null);
			db.close(); // Closing database connection
		} else {
			// Inserting Row
			final SQLiteDatabase db = this.getWritableDatabase();
			db.insert(TABLE_LOCATION, null, values);
			db.close(); // Closing database connection
		}
	}

	/**
	 * Getting user data from database
	 * */
	public Device getCurrentUser(){
		final Device device = new Device();
		final String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		final SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			device.setName(cursor.getString(1));
			device.setEmail(cursor.getString(2));
			device.setId(Long.valueOf(cursor.getString(3)));
		}
		cursor.close();
		db.close();
		return device;
	}

	/**
	 * Getting lastLocation data from database
	 * */
	public Location getLastLocation(){
		Location location = null;
		final String selectQuery = "SELECT * FROM " + TABLE_LOCATION;

		final SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if(cursor.getCount() > 0){
			location = new Location("");
			location.setLatitude(Double.valueOf(cursor.getString(1)));
			location.setLongitude(Double.valueOf(cursor.getString(2)));
		}
		cursor.close();
		db.close();
		return location;
	}

	/**
	 * Getting user login status
	 * return true if rows are there in table
	 * */
	public int getRowCount() {
		final String countQuery = "SELECT * FROM " + TABLE_LOGIN;
		final SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.rawQuery(countQuery, null);
		final int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	public int getRowCountLocation() {
		final String countQuery = "SELECT * FROM " + TABLE_LOCATION;
		final SQLiteDatabase db = this.getReadableDatabase();
		final Cursor cursor = db.rawQuery(countQuery, null);
		final int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	/**
	 * Re crate database
	 * Delete all tables and create them again
	 * */
	public void resetTables(){
		final SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_LOCATION, null, null);
		db.close();
	}

}
