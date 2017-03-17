package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.Classes.UserFavorite;

/**
 * Created by Nick on 2017-03-15.
 */

public class UserQueries {

    private DatabaseHandler dbHandler;

    public UserQueries(Context c) {
        dbHandler = new DatabaseHandler(c);
    }

    public void addUser(User user) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues vals = new ContentValues();

        vals.put("FirstName", user.getFirstName());
        vals.put("LastName", user.getLastName());
        vals.put("DisplayName", user.getDisplayName());
        vals.put("Email", user.getEmail());
        vals.put("Password", user.getPassword());
        vals.put("IsAdmin", user.isAdmin());

        db.insert(DatabaseHandler.TABLE_USER, null, vals);

        db.close();
    }


    // todo getUserById


    public User getUserByEmailPass(String email, String pass) {

        List<UserFavorite> favs = new ArrayList<UserFavorite>();

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER +
                " WHERE Email LIKE " + email + " AND Password LIKE ;";

        SQLiteDatabase db = this.dbHandler.getWritableDatabase();
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

        db.close();

        return null;
    }


}
