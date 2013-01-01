package net.liveandletlearn.opengoaltracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class OGTContract {
	private OGTContract() {}
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	
	
	public static abstract class UserGoals implements BaseColumns {
		public static final String TABLE_NAME = "usergoal";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		// Can these be (or should these be) within the UserGoals class?
		private static final String SQL_CREATE_TABLE =
			"CREATE TABLE " + OGTContract.UserGoals.TABLE_NAME + " (" +
			OGTContract.UserGoals._ID + " INTEGER PRIMARY KEY," +
			OGTContract.UserGoals.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			OGTContract.UserGoals.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
			" )";
		
		private static final String SQL_DELETE_TABLE =
			"DROP TABLE IF EXISTS " + OGTContract.UserGoals.TABLE_NAME;
		
		public static long count(SQLiteDatabase db) {
			return DatabaseUtils.queryNumEntries(db, OGTContract.UserGoals.TABLE_NAME);
		}
		
		public static long insert(SQLiteDatabase db, String title) {
	    	ContentValues values = new ContentValues();
	    	values.put(COLUMN_NAME_TITLE, title);	
	        return db.insert(TABLE_NAME, null, values);
		}
		
	}
	
	public static class OGTDbHelper extends SQLiteOpenHelper {
		public static final int DATABASE_VERSION = 1;
		public static boolean mTesting = false;
		
		public static String GetDatabaseName() {
			if (mTesting) {
				return "OGT_test.db";
			} else {
				return "OGT.db";
			}
		}
		
		public OGTDbHelper(Context context) {
			super(context, GetDatabaseName(), null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(UserGoals.SQL_CREATE_TABLE);
		}

		public void truncateTestDatabase() {
			if (mTesting == true) {
				SQLiteDatabase db = getWritableDatabase();
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