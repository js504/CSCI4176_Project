package in.geobullet.csci_4176_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.User;
import in.geobullet.csci_4176_project.Database.Classes.UserFavorite;

/**
 * Created by Nick on 2017-03-04.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    private static final String LOG = "DatabaseHandler";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "GeoBulletin";

    static final String TABLE_USER = "User";
    static final String TABLE_BOARD = "Board";
    static final String TABLE_POSTER = "Poster";
    static final String TABLE_BOARD_POSTER_PAIR = "BoardPosterPair";
    static final String TABLE_USER_FAVORITE = "UserFavorite";

    private static final String KEY_ID = "Id INTEGER PRIMARY KEY, ";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER +
                " (" +
                    KEY_ID +
                    "FirstName VARCHAR(255), " +
                    "LastName VARCHAR(255), " +
                    "DisplayName VARCHAR(255), " +
                    "Email VARCHAR(255), " +
                    "Password VARCHAR(255), " +
                    "IsAdmin TINYINT " +
                ");";

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
                ");";

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
                    "PhotoName VARCHAR(255), " +
                    "Cost VARCHAR(255) " +
                ");";

    private static final String CREATE_TABLE_BOARD_POSTER_PAIR =
            "CREATE TABLE " + TABLE_BOARD_POSTER_PAIR +
                " (" +
                    KEY_ID +
                    "BoardId INTEGER, " +
                    "PosterId INTEGER " +
                ");";

    private static final String CREATE_TABLE_USER_FAVORITE =
            "CREATE TABLE " + TABLE_USER_FAVORITE +
                " (" +
                    KEY_ID +
                    "UserId INTEGER, " +
                    "BoardPosterPairId INTEGER " +
                ");";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG, "Creating tables..");

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

    public void dropAndRecreateTables() {
        SQLiteDatabase db = this.getWritableDatabase();

        String dropStmt = "DROP TABLE IF EXISTS ";

        db.execSQL(dropStmt + TABLE_USER);
        db.execSQL(dropStmt + TABLE_BOARD);
        db.execSQL(dropStmt + TABLE_POSTER);
        db.execSQL(dropStmt + TABLE_BOARD_POSTER_PAIR);
        db.execSQL(dropStmt + TABLE_USER_FAVORITE);

        onCreate(db);
    }

    /* Begin Board Poster Pair Queries */

    public int addBoardPosterPair(BoardPosterPair bpp) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardPosterPairQueries bppq = new BoardPosterPairQueries(db);

        int id = (int) bppq.addBoardPosterPair(bpp);

        db.close();

        return id;
    }

    public boolean boardPosterPairsExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardPosterPairQueries bppq = new BoardPosterPairQueries(db);

        int num = bppq.getNumBoardPosterPairs();

        db.close();

        return num > 0;
    }

    public BoardPosterPair getBoardPosterPairById(int bppId) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardPosterPairQueries bppq = new BoardPosterPairQueries(db);

        BoardPosterPair bpp = bppq.getBoardPosterPairById(bppId);

        db.close();

        return bpp;
    }

    public List<BoardPosterPair> getAllBoardPosterPairs() {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardPosterPairQueries bppq = new BoardPosterPairQueries(db);
        List<BoardPosterPair> bpps = bppq.getAllBoardPosterPairs();

        db.close();

        return bpps;
    }

    /* End Board Poster Pair Queries */



    /* Begin Board Queries */

    public int addBoard(Board board) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        int id = (int) bqs.addBoard(board);

        db.close();

        return id;
    }

    public boolean boardsExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        int num = bqs.getNumBoards();

        db.close();

        return num > 0;
    }

    public boolean updateBoard(Board board) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        boolean success = bqs.updateBoard(board);

        db.close();

        return success;
    }

    public List<Board> getAllBoards() {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        List<Board> boards = bqs.getAllBoards();

        db.close();

        return boards;
    }

    public List<Board> getAllBoardsForUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        List<Board> boards = bqs.getAllBoardsForUser(userId);

        db.close();

        return boards;
    }

    public Board getBoardById(int boardId) {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        Board b = bqs.getBoardById(boardId);

        db.close();

        return b;
    }

    public Board getFirstBoard() {
        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        Board b = bqs.getFirstBoard();

        db.close();

        return b;
    }

    public List<Board> searchBoardsByLatitudeLongitudeWithinMeters(double latitude, double longitude, int meters) {

        SQLiteDatabase db = this.getWritableDatabase();

        BoardQueries bqs = new BoardQueries(db);

        List<Board> boards = bqs.searchBoardsByLatitudeLongitudeWithinMeters(latitude, longitude, meters);

        db.close();

        return boards;
    }

    /* End Board Queries */



    /* Begin Poster Queries */

    public int addPoster(Poster poster) {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        int id = (int) pq.addPoster(poster);

        db.close();

        return id;
    }

    public boolean postersExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        int num = pq.getNumPosters();

        db.close();

        return num > 0;
    }

    public boolean updatePoster(Poster poster) {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        boolean success = pq.updatePoster(poster);

        db.close();

        return success;
    }

    public Poster getPosterById(int posterId) {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        Poster p = pq.getPosterById(posterId);

        db.close();

        return p;
    }

    public List<Poster> getPostersForBoard(int boardId) {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        List<Poster> posters = pq.getPostersForBoard(boardId);

        db.close();

        return posters;
    }

    public List<Poster> getPostersForUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        PosterQueries pq = new PosterQueries(db);

        List<Poster> posters = pq.getPostersForUser(userId);

        db.close();

        return posters;
    }

    /* End Poster Queries */



    /* Begin User Favorite Queries */

    public int addUserFavorite(UserFavorite userFav) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserFavoriteQueries ufq = new UserFavoriteQueries(db);

        int id = (int) ufq.addUserFavorite(userFav);

        db.close();

        return id;
    }

    public boolean userFavoritesExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        UserFavoriteQueries ufq = new UserFavoriteQueries(db);

        int num = ufq.getNumUserFavorites();

        db.close();

        return num > 0;
    }

    public List<UserFavorite> getUserFavoritesForUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserFavoriteQueries ufq = new UserFavoriteQueries(db);

        List<UserFavorite> userFavs = ufq.getUserFavoritesForUser(userId);

        db.close();

        return userFavs;
    }

    /* End User Favorite Queries */



    /* Begin User Queries */

    public int addUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserQueries uq = new UserQueries(db);
        int id = (int) uq.addUser(u);

        db.close();

        return id;
    }

    public boolean usersExist() {
        SQLiteDatabase db = this.getWritableDatabase();

        UserQueries uq = new UserQueries(db);

        int numUsers = uq.getNumUsers();

        db.close();

        return numUsers > 0;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserQueries uq = new UserQueries(db);
        boolean success = uq.updateUser(user);

        db.close();

        return success;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserQueries uq = new UserQueries(db);
        User u = uq.getUserById(id);

        db.close();

        return u;
    }

    public User getUserByEmailPass(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        UserQueries uq = new UserQueries(db);
        User u = uq.getUserByEmailPass(email, pass);

        db.close();

        return u;
    }

    /* End User Queries */



}
