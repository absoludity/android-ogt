package net.liveandletlearn.opengoaltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class OGTDatabase {
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	
	private DatabaseOpenHelper mDatabaseOpenHelper;
	
	public OGTDatabase(Context context) {
		mDatabaseOpenHelper = new DatabaseOpenHelper(context);
	}

	public void truncateTestDatabase() {
		mDatabaseOpenHelper.truncateTestDatabase();
	}
	
	public long countGoals() {
		return DatabaseUtils.queryNumEntries(
			mDatabaseOpenHelper.getReadableDatabase(),
			OGTDatabase.UserGoals.TABLE_NAME);
	}
	
	public long insertGoal(String title) {
    	ContentValues values = new ContentValues();
    	values.put(UserGoals.COLUMN_NAME_TITLE, title);	
        return mDatabaseOpenHelper.getWritableDatabase().insert(
        	UserGoals.TABLE_NAME, null, values);		
	}
	public Cursor UserGoals(String[] columns) {
		return mDatabaseOpenHelper.getReadableDatabase().query(
			UserGoals.TABLE_NAME, columns, null, null, null, null, null);
	}

	public static abstract class UserGoals implements BaseColumns {
		public static final String TABLE_NAME = "usergoal";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		// Can these be (or should these be) within the UserGoals class?
		private static final String SQL_CREATE_TABLE =
			"CREATE TABLE " + OGTDatabase.UserGoals.TABLE_NAME + " (" +
			OGTDatabase.UserGoals._ID + " INTEGER PRIMARY KEY," +
			OGTDatabase.UserGoals.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			OGTDatabase.UserGoals.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
			" )";
		
		private static final String SQL_DELETE_TABLE =
			"DROP TABLE IF EXISTS " + OGTDatabase.UserGoals.TABLE_NAME;
	}
	
	private static class DatabaseOpenHelper extends SQLiteOpenHelper {
		public static final int DATABASE_VERSION = 1;
		public static boolean mTesting = false;
		
		public static String GetDatabaseName() {
			if (mTesting) {
				return "OGT_test.db";
			} else {
				return "OGT.db";
			}
		}
		
		public DatabaseOpenHelper(Context context) {
			super(context, GetDatabaseName(), null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(UserGoals.SQL_CREATE_TABLE);
		}

		public void truncateTestDatabase() {
			if (mTesting == true) {
				SQLiteDatabase db = this.getWritableDatabase();
				db.execSQL(UserGoals.SQL_DELETE_TABLE);
				onCreate(db);			
			}
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Until we've got a 1.0 schema :)
			db.execSQL(UserGoals.SQL_DELETE_TABLE);
			onCreate(db);			
		}
	}
}