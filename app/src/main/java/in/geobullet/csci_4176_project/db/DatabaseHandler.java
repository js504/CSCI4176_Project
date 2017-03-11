package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("FirstName", user.getFirstName());
        vals.put("LastName", user.getLastName());
        vals.put("DisplayName", user.getDisplayName());
        vals.put("Email", user.getEmail());
        vals.put("Password", user.getPassword());
        vals.put("IsAdmin", user.isAdmin());

        db.insert(TABLE_USER, null, vals);
        db.close();
    }

    // todo getUser


    public void addBoard(Board board) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("Created", board.getCreated().toString());
        vals.put("CreatedByUserId", board.getCreatedByUserId());
        vals.put("Name", board.getName());
        vals.put("ExpirationDate", board.getExpirationDate().toString());
        vals.put("RadiusInMeters", board.getRadiusInMeters());
        vals.put("Longitude", board.getLongitude());
        vals.put("Latitude", board.getLatitude());

        db.insert(TABLE_BOARD, null, vals);
        db.close();
    }

    // todo deleteBoard, getAllBoards, getBoardByLatitudeLongitudeWithinMeters


    public void addPoster(Poster poster) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("Created", poster.getCreated().toString());
        vals.put("CreatedByUserId", poster.getCreatedByUserId());
        vals.put("Title", poster.getTitle());
        vals.put("PosterType", poster.getPosterType().toString());
        vals.put("Address", poster.getAddress());
        vals.put("City", poster.getCity());
        vals.put("StateProv", poster.getStateProv());
        vals.put("Details", poster.getDetails());
        vals.put("StartDate", poster.getStartDate().toString());
        vals.put("EndDate", poster.getEndDate().toString());
        vals.put("StartTime", poster.getStartTime().toString());
        vals.put("EndTime", poster.getEndTime().toString());
        vals.put("PhotoName", poster.getPhotoName());

        db.insert(TABLE_POSTER, null, vals);
        db.close();
    }

    public void addBoardPosterPair(BoardPosterPair bpp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("BoardId", bpp.getBoardId());
        vals.put("PosterId", bpp.getPosterId());

        db.insert(TABLE_BOARD_POSTER_PAIR, null, vals);
        db.close();
    }

    // todo: getAllPostersForBoardId


    public void addUserFavorite(UserFavorite userFav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("UserId", userFav.getUserId());
        vals.put("BoardPosterPairId", userFav.getBoardPosterPairId());

        db.insert(TABLE_USER_FAVORITE, null, vals);
        db.close();
    }

    public List<UserFavorite> getUserFavoritesByUserId(int userId) {
        List<UserFavorite> favs = new ArrayList<UserFavorite>();

        String query = "SELECT * FROM " + TABLE_USER_FAVORITE +
                " WHERE UserId = " + userId + ";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                UserFavorite fav = new UserFavorite();

                int userIdIndex = cursor.getColumnIndex("UserId");
                int bppIndex = cursor.getColumnIndex("BoardPosterPairId");

                fav.setUserId(Integer.parseInt(cursor.getString(userIdIndex)));
                fav.setBoardPosterPairId(Integer.parseInt(cursor.getString(bppIndex)));

                favs.add(fav);

            } while (cursor.moveToNext());
        }

        return favs;
    }
}
