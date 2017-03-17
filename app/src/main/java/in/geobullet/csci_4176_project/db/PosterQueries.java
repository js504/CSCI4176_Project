package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Poster;

/**
 * Created by Nick on 2017-03-15.
 */

public class PosterQueries {

    private DatabaseHandler dbHandler;

    public PosterQueries(Context c) {
        dbHandler = new DatabaseHandler(c);
    }


    public void addPoster(Poster poster) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

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

        db.insert(DatabaseHandler.TABLE_POSTER, null, vals);

        db.close();
    }

    public Poster getPosterById(int id) {
        Poster poster = new Poster();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_POSTER +
                " WHERE Id = " + id + ";";

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                int addressIndex = cursor.getColumnIndex("Address");

                poster.setAddress(cursor.getString(addressIndex));
                // todo rest of the fields

            } while (cursor.moveToNext());
        }

        db.close();

        return poster;
    }

    public List<Poster> getPostersByBoardId(int boardId) {

        return null;
    }

    // todo: getAllPostersForBoardId


}
