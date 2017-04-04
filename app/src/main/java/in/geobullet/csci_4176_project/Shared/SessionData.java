package in.geobullet.csci_4176_project.Shared;

import android.location.Location;

import in.geobullet.csci_4176_project.Database.Classes.User;


public class SessionData {

    // If null, user is not logged in, else is logged in
    public static User currentUser = null;
    public static int boardId = -1;
    public static Location location = null;
    public static int radius = -1;
}
