package in.geobullet.csci_4176_project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import in.geobullet.csci_4176_project.db.Classes.User;

/**
 * Created by Nick on 2017-03-15.
 */

public class UserQueries {

    private SQLiteDatabase db;

    public UserQueries(SQLiteDatabase db) {
        this.db = db;
    }

    public long addUser(User user) {
        ContentValues vals = new ContentValues();

        vals.put("FirstName", user.getFirstName());
        vals.put("LastName", user.getLastName());
        vals.put("DisplayName", user.getDisplayName());
        vals.put("Email", user.getEmail());
        vals.put("Password", user.getPassword());
        vals.put("IsAdmin", user.isAdmin());

        return db.insert(DatabaseHandler.TABLE_USER, null, vals);

        // (The calling class is responsible for closing the database)
    }

    public boolean updateUser(User user) {
        ContentValues vals = new ContentValues();

        vals.put("Id", user.getId());
        vals.put("FirstName", user.getFirstName());
        vals.put("LastName", user.getLastName());
        vals.put("DisplayName", user.getDisplayName());
        vals.put("Email", user.getEmail());
        vals.put("Password", user.getPassword());
        vals.put("IsAdmin", user.isAdmin());

        int numRowsAffected = db.update(DatabaseHandler.TABLE_USER, vals, "Id = " + user.getId(), null);

        // (The calling class is responsible for closing the database)

        return numRowsAffected == 1;
    }


    public User getUserById(int userId) {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER +
                " WHERE Id = " + userId + ";";

        User u = null;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                u = new User();

                u = this.setUserFields(cursor, u);

            } while (cursor.moveToNext());
        }

        // (The calling class is responsible for closing the database)

        return u;
    }


    public User getUserByEmailPass(String email, String pass) {

        User u = null;

        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER +
                " WHERE Email LIKE '" + email + "' AND Password LIKE '" + pass + "';";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                u = new User();

                u = this.setUserFields(cursor, u);

            } while (cursor.moveToNext());
        }

        return u;

        // (The calling class is responsible for closing the database)
    }

    private User setUserFields(Cursor cursor, User u) {
        if (u == null) {
            u = new User();
        }

        int idIdx = cursor.getColumnIndex("Id");
        int fNameIdx = cursor.getColumnIndex("FirstName");
        int lNameIdx = cursor.getColumnIndex("LastName");
        int dNameIdx = cursor.getColumnIndex("DisplayName");
        int emailIdx = cursor.getColumnIndex("Email");
        int passIdx = cursor.getColumnIndex("Password");
        int isAdminIdx = cursor.getColumnIndex("IsAdmin");

        u.setId(cursor.getInt(idIdx));
        u.setFirstName(cursor.getString(fNameIdx));
        u.setLastName(cursor.getString(lNameIdx));
        u.setDisplayName(cursor.getString(dNameIdx));
        u.setEmail(cursor.getString(emailIdx));
        u.setPassword(cursor.getString(passIdx));
        u.setAdmin(cursor.getInt(isAdminIdx) == 1);

        return u;
    }

}
