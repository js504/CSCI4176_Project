package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.db.Classes.Poster;

/**
 * Created by Nick on 2017-03-15.
 */

public class PosterQueries {

    private DatabaseHandler dbHandler;
    private Context context;

    public PosterQueries(Context c) {
        this.context = c;
        dbHandler = new DatabaseHandler(c);
    }


    public void addPoster(Poster poster) {
        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("Created", DateFormat.format("yyyy-MM-dd HH:mm:ss", poster.getCreated()).toString());
        vals.put("CreatedByUserId", poster.getCreatedByUserId());
        vals.put("Title", poster.getTitle());
        vals.put("PosterType", poster.getPosterType().toString());
        vals.put("Address", poster.getAddress());
        vals.put("City", poster.getCity());
        vals.put("StateProv", poster.getStateProv());
        vals.put("Details", poster.getDetails());
        vals.put("StartDate", DateFormat.format("yyyy-MM-dd HH:mm:ss", poster.getStartDate()).toString());
        vals.put("EndDate", DateFormat.format("yyyy-MM-dd HH:mm:ss", poster.getEndDate()).toString());
        vals.put("StartTime", DateFormat.format("yyyy-MM-dd HH:mm:ss", poster.getStartTime()).toString());
        vals.put("EndTime", DateFormat.format("yyyy-MM-dd HH:mm:ss", poster.getEndTime()).toString());
        vals.put("PhotoName", poster.getPhotoName());

        db.insert(DatabaseHandler.TABLE_POSTER, null, vals);

        db.close();
    }

    public Poster getPosterById(int id) {

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_POSTER +
                " WHERE Id = " + id + ";";

        Poster p = null;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                p = new Poster();

                int createdIdx = cursor.getColumnIndex("Created");
                int cbuidIdx = cursor.getColumnIndex("CreatedByUserId");
                int titleIdx = cursor.getColumnIndex("Title");
                int posterTypeIdx = cursor.getColumnIndex("PosterType");
                int addIdx = cursor.getColumnIndex("Address");
                int cityIdx = cursor.getColumnIndex("City");
                int stateProvIdx = cursor.getColumnIndex("StateProv");
                int detailsIdx = cursor.getColumnIndex("Details");
                int sDateIdx = cursor.getColumnIndex("StartDate");
                int eDateIdx = cursor.getColumnIndex("EndDate");
                int sTimeIdx = cursor.getColumnIndex("StartTime");
                int eTimeIdx = cursor.getColumnIndex("EndTime");
                int photoNameIdx = cursor.getColumnIndex("PhotoName");

//                p.setCreated(Date.valueOf(cursor.get));
                p.setAddress(cursor.getString(addIdx));


            } while (cursor.moveToNext());
        }

        db.close();

        return p;
    }

    public List<Poster> getPostersByBoardId(int boardId) {

        BoardPosterPairQueries bppQueries = new BoardPosterPairQueries(this.context);

        List<BoardPosterPair> allBpps = bppQueries.getAllBoardPosterPairs();

        List<Integer> posterIds = new ArrayList<>();

        List<Poster> posters = new ArrayList<>();

        for (BoardPosterPair bpp: allBpps) {
            posterIds.add(bpp.getPosterId());
        }

        String query = "SELECT * FROM Poster WHERE PosterId IN (" + StringUtils.join(posterIds, ",") + ");";

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {



            } while (cursor.moveToNext());
        }

        db.close();

        return posters;
    }

    // todo: getAllPostersForBoardId


}
