package net.liveandletlearn.opengoaltracker;

import android.content.Context;
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
	}
	
	public static class OGTDbHelper extends SQLiteOpenHelper {
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_NAME = "OGT.db";
		
		public OGTDbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(UserGoals.SQL_CREATE_TABLE);
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Until we've got a 1.0 schema :)
			db.execSQL(UserGoals.SQL_DELETE_TABLE);
			onCreate(db);
		}
	}
}