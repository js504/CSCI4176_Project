package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.db.Utils.DateUtil;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.PosterType;

/**
 * Created by Nick on 2017-03-15.
 */

public class PosterQueries {

    private SQLiteDatabase db;

    public PosterQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public long addPoster(Poster poster) {
        ContentValues vals = new ContentValues();

        if (poster.getCreated() != null) {
            vals.put("Created", DateFormat.format(DateUtil.DATE_FORMAT, poster.getCreated()).toString());
        }

        vals.put("CreatedByUserId", poster.getCreatedByUserId());
        vals.put("Title", poster.getTitle());
        vals.put("PosterType", poster.getPosterType().toString());
        vals.put("Address", poster.getAddress());
        vals.put("City", poster.getCity());
        vals.put("StateProv", poster.getStateProv());
        vals.put("Details", poster.getDetails());

        if (poster.getStartDate() != null) {
            vals.put("StartDate", DateFormat.format(DateUtil.DATE_FORMAT, poster.getStartDate()).toString());
        }
        if (poster.getEndDate() != null) {
            vals.put("EndDate", DateFormat.format(DateUtil.DATE_FORMAT, poster.getEndDate()).toString());
        }
        if (poster.getStartTime() != null) {
            vals.put("StartTime", DateFormat.format(DateUtil.DATE_FORMAT, poster.getStartTime()).toString());
        }
        if (poster.getEndTime() != null) {
            vals.put("EndTime", DateFormat.format(DateUtil.DATE_FORMAT, poster.getEndTime()).toString());
        }
        vals.put("PhotoName", poster.getPhotoName());

        // (The calling class is responsible for closing the database)

        return db.insert(DatabaseHandler.TABLE_POSTER, null, vals);
    }

    public boolean updatePoster(Poster poster) {

        ContentValues vals = new ContentValues();

        vals.put("Id", poster.getId());

        if (poster.getCreated() != null) {
            vals.put("Created", DateFormat.format(DateUtil.DATE_FORMAT, poster.getCreated()).toString());
        }

        vals.put("CreatedByUserId", poster.getCreatedByUserId());
        vals.put("Title", poster.getTitle());
        vals.put("PosterType", poster.getPosterType().toString());
        vals.put("Address", poster.getAddress());
        vals.put("City", poster.getCity());
        vals.put("StateProv", poster.getStateProv());
        vals.put("Details", poster.getDetails());

        if (poster.getStartDate() != null) {
            vals.put("StartDate", DateFormat.format(DateUtil.DATE_FORMAT, poster.getStartDate()).toString());
        }
        if (poster.getEndDate() != null) {
            vals.put("EndDate", DateFormat.format(DateUtil.DATE_FORMAT, poster.getEndDate()).toString());
        }
        if (poster.getStartTime() != null) {
            vals.put("StartTime", DateFormat.format(DateUtil.DATE_FORMAT, poster.getStartTime()).toString());
        }
        if (poster.getEndTime() != null) {
            vals.put("EndTime", DateFormat.format(DateUtil.DATE_FORMAT, poster.getEndTime()).toString());
        }
        vals.put("PhotoName", poster.getPhotoName());

        int numRowsAffected = db.update(DatabaseHandler.TABLE_POSTER, vals, "Id = " + poster.getId(), null);

        // (The calling class is responsible for closing the database)

        return numRowsAffected == 1;
    }

    public Poster getPosterById(int posterId) {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_POSTER +
                " WHERE Id = " + posterId + ";";

        Poster p = null;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                p = new Poster();

                p = this.setPosterFields(cursor, p);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return p;
    }

    public List<Poster> getPostersForBoard(int boardId) {

        BoardPosterPairQueries bppQueries = new BoardPosterPairQueries(this.db);

        List<BoardPosterPair> allBpps = bppQueries.getAllBoardPosterPairs();

        List<Integer> posterIds = new ArrayList<>();

        for (BoardPosterPair bpp: allBpps) {
            posterIds.add(bpp.getPosterId());
        }

        List<Poster> posters = new ArrayList<>();

        String query = "SELECT * FROM Poster WHERE PosterId IN (" + StringUtils.join(posterIds, ",") + ");";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Poster p = new Poster();

                p = this.setPosterFields(cursor, p);

                posters.add(p);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return posters;
    }

    public List<Poster> getPostersForUser(int userId) {

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_POSTER +" WHERE CreatedByUserId = " + userId + ";";

        List<Poster> posters = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Poster p = new Poster();

                p = this.setPosterFields(cursor, p);

                posters.add(p);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return posters;
    }


    private Poster setPosterFields(Cursor cursor, Poster p) {
        if (p == null) {
            p = new Poster();
        }

        int idIdx = cursor.getColumnIndex("Id");
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

        p.setId(cursor.getInt(idIdx));

        String created = cursor.getString(createdIdx);

        if (created != null && created.length() > 0) {
            try {
                p.setCreated(DateUtil.getDateValueFromColumn(created));
            } catch (ParseException e) {
                Log.d("Error", "PosterQueries: Couldn't parse created date: " + e.getMessage());
            }
        } else {
            p.setCreated(null);
        }

        p.setCreatedByUserId(cursor.getInt(cbuidIdx));
        p.setTitle(cursor.getString(titleIdx));

        int posterTypeVal = cursor.getInt(posterTypeIdx);

        switch (posterTypeVal) {
            case 0:
                p.setPosterType(PosterType.Event);
                break;
            case 1:
                p.setPosterType(PosterType.Service);
                break;
        }

        p.setAddress(cursor.getString(addIdx));
        p.setCity(cursor.getString(cityIdx));
        p.setStateProv(cursor.getString(stateProvIdx));
        p.setDetails(cursor.getString(detailsIdx));

        String startDate = cursor.getString(sDateIdx);

        if (startDate != null && startDate.length() > 0) {
            try {
                p.setStartDate(DateUtil.getDateValueFromColumn(startDate));
            } catch (ParseException e) {
                Log.d("Error", "PosterQueries: Couldn't parse start date: " + e.getMessage());
            }
        } else {
            p.setStartDate(null);
        }

        String endDate = cursor.getString(eDateIdx);

        if (endDate != null && endDate.length() > 0) {
            try {
                p.setEndDate(DateUtil.getDateValueFromColumn(endDate));
            } catch (ParseException e) {
                Log.d("Error", "PosterQueries: Couldn't parse end date: " + e.getMessage());
            }
        } else {
            p.setEndDate(null);
        }

        String startTime = cursor.getString(sTimeIdx);

        if (startTime != null && startTime.length() > 0) {
            try {
                p.setStartTime(DateUtil.getDateValueFromColumn(startTime));
            } catch (ParseException e) {
                Log.d("Error", "PosterQueries: Couldn't parse start time: " + e.getMessage());
            }
        } else {
            p.setStartTime(null);
        }

        String endTime = cursor.getString(eTimeIdx);

        if (endTime != null && endTime.length() > 0) {
            try {
                p.setEndTime(DateUtil.getDateValueFromColumn(endTime));
            } catch (ParseException e) {
                Log.d("Error", "PosterQueries: Couldn't parse end time: " + e.getMessage());
            }
        } else {
            p.setEndTime(null);
        }

        p.setPhotoName(cursor.getString(photoNameIdx));

        return p;
    }

}
