package in.geobullet.csci_4176_project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nick on 2017-03-04.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHandler";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "GeoBulletin";

    private static final String TABLE_USER = "User";
    private static final String TABLE_BOARD = "Board";
    private static final String TABLE_POSTER = "Poster";
    private static final String TABLE_BOARD_POSTER_PAIR = "BoardPosterPair";
    private static final String TABLE_USER_FAVORITE = "UserFavorite";

    private static final String KEY_ID = "Id INTEGER PRIMARY KEY, ";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER +
                " (" +
                    KEY_ID +
                    "FirstName VARCHAR(255)," +
                    "LastName VARCHAR(255), " +
                    "DisplayName VARCHAR(255), " +
                    "Email VARCHAR(255), " +
                    "Password VARCHAR(255), " +
                    "IsAdmin TINYINT " +
                ")";

    private static final String CREATE_TABLE_BOARD =
            "CREATE TABLE " + TABLE_BOARD +
                " (" +
                    KEY_ID +
                    "Created DATETIME, " +
                    "CreatedByUserId INTEGER, " +
                    "Name VARCHAR(255), " +
                    "ExpirationDate DATETIME, " +
                    "RadiusInMeters INTEGER, " +
                    "Longitude DOUBLE, " +
                    "Latitude DOUBLE " +
                ")";

    private static final String CREATE_TABLE_POSTER =
            "CREATE TABLE " + TABLE_POSTER +
                " (" +
                    KEY_ID +
                    "Created DATETIME, " +
                    "CreatedByUserId INTEGER, " +
                    "Title VARCHAR(255), " +
                    "PosterType TINYINT, " + // enum value
                    "Address VARCHAR(255), " +
                    "City VARCHAR(255), " +
                    "StateProv VARCHAR(255), " +
                    "Details TEXT, " +
                    "StartDate DATE, " +
                    "EndDate DATE, " +
                    "StartTime DATETIME, " +
                    "EndTime DATETIME, " +
                    "PhotoName VARCHAR(255) " +
                ")";

    private static final String CREATE_TABLE_BOARD_POSTER_PAIR =
            "CREATE TABLE " + TABLE_BOARD_POSTER_PAIR +
                " (" +
                    KEY_ID +
                    "BoardId INTEGER, " +
                    "PosterId INTEGER " +
                ")";

    private static final String CREATE_TABLE_USER_FAVORITE =
            "CREATE TABLE " + TABLE_USER_FAVORITE +
                " (" +
                    KEY_ID +
                    "UserId INTEGER, " +
                    "BoardPosterPairId INTEGER " +
                ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG, "Creating tables...");

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BOARD);
        db.execSQL(CREATE_TABLE_POSTER);
        db.execSQL(CREATE_TABLE_BOARD_POSTER_PAIR);
        db.execSQL(CREATE_TABLE_USER_FAVORITE);

        Log.d(LOG, "Tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropStmt = "DROP TABLE IF EXISTS ";

        db.execSQL(dropStmt + TABLE_USER);
        db.execSQL(dropStmt + TABLE_BOARD);
        db.execSQL(dropStmt + TABLE_POSTER);
        db.execSQL(dropStmt + TABLE_BOARD_POSTER_PAIR);
        db.execSQL(dropStmt + TABLE_USER_FAVORITE);

        onCreate(db);
    }

    public void addUser() {
        // todo finish
    }


}
