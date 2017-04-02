package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Utils.DateUtil;

/**
 * Created by Nick on 2017-03-15.
 */

public class BoardQueries {

    private SQLiteDatabase db;

    public BoardQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public long addBoard(Board board) {
        ContentValues vals = new ContentValues();

        vals = this.setContentValues(vals, board);

        return db.insert(DatabaseHandler.TABLE_BOARD, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public int getNumBoards() {
        String query = "SELECT COUNT(*) FROM " + DatabaseHandler.TABLE_BOARD;

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();

        // (The calling class is responsible for closing the database)
    }

    public boolean updateBoard(Board board) {

        ContentValues vals = new ContentValues();

        vals = this.setContentValues(vals, board);

        int numRowsAffected = db.update(DatabaseHandler.TABLE_BOARD, vals, "Id = " + board.getId(), null);

        // (The calling class is responsible for closing the database)

        return numRowsAffected == 1;
    }

    public List<Board> getAllBoards() {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD + " ORDER BY Name ASC;";

        Cursor cursor = db.rawQuery(query, null);

        List<Board> boards = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Board b = null;
                b = this.setBoardFields(cursor, b);
                boards.add(b);
            } while (cursor.moveToNext());
        }

        return boards;
    }

    public Board getBoardById(int boardId) {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD + " WHERE Id = " + boardId + ";";

        Cursor cursor = db.rawQuery(query, null);

        Board b = null;

        if (cursor.moveToFirst()) {
            do {
                b = this.setBoardFields(cursor, b);
            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return b;
    }

    public List<Board> getAllBoardsForUser(int userId) {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_BOARD + " WHERE CreatedByUserId=" + userId +
                " ORDER BY Name ASC;";

        Cursor cursor = db.rawQuery(query, null);

        List<Board> boards = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Board b = null;
                b = this.setBoardFields(cursor, b);
                boards.add(b);
            } while (cursor.moveToNext());
        }

        return boards;
    }

    private ContentValues setContentValues(ContentValues vals, Board board) {

        if (board.getCreated() != null) {
            vals.put("Created", DateFormat.format(DateUtil.DATE_FORMAT, board.getCreated()).toString());
        }

        vals.put("CreatedByUserId", board.getCreatedByUserId());
        vals.put("Name", board.getName());

        if (board.getExpirationDate() != null) {
            vals.put("ExpirationDate", DateFormat.format(DateUtil.DATE_FORMAT, board.getExpirationDate()).toString());
        }

        vals.put("RadiusInMeters", board.getRadiusInMeters());
        vals.put("Longitude", board.getLongitude());
        vals.put("Latitude", board.getLatitude());

        return vals;
    }

    private Board setBoardFields(Cursor cursor, Board b) {
        if (b == null) {
            b = new Board();
        }

        int idIdx = cursor.getColumnIndex("Id");
        int createdIdx = cursor.getColumnIndex("Created");
        int cbuidIdx = cursor.getColumnIndex("CreatedByUserId");
        int nameIdx = cursor.getColumnIndex("Name");
        int expIdx = cursor.getColumnIndex("ExpirationDate");
        int radIdx = cursor.getColumnIndex("RadiusInMeters");
        int longIdx = cursor.getColumnIndex("Longitude");
        int latIdx = cursor.getColumnIndex("Latitude");

        b.setId(cursor.getInt(idIdx));

        String created = cursor.getString(createdIdx);

        if (created != null && created.length() > 0) {
            try {
                b.setCreated(DateUtil.getDateValueFromColumn(created));
            } catch (ParseException e) {
                Log.d("Error", "BoardQueries: Couldn't parse created date: " + e.getMessage());
            }
        } else {
            b.setCreated(null);
        }

        b.setCreatedByUserId(cursor.getInt(cbuidIdx));
        b.setName(cursor.getString(nameIdx));

        String expDate = cursor.getString(expIdx);

        if (expDate != null && expDate.length() > 0) {
            try {
                b.setExpirationDate(DateUtil.getDateValueFromColumn(expDate));
            } catch (ParseException e) {
                Log.d("Error", "BoardQueries: Couldn't parse expiration date: " + e.getMessage());
            }
        } else {
            b.setExpirationDate(null);
        }

        b.setRadiusInMeters(cursor.getInt(radIdx));
        b.setLongitude(cursor.getDouble(longIdx));
        b.setLatitude(cursor.getDouble(latIdx));

        return b;
    }


    // todo deleteBoard, getAllBoards, getBoardByLatitudeLongitudeWithinMeters

}
