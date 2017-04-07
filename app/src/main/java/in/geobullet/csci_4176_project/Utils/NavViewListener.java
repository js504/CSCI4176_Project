package in.geobullet.csci_4176_project.Utils;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import in.geobullet.csci_4176_project.Account_info;
import in.geobullet.csci_4176_project.Login;
import in.geobullet.csci_4176_project.Main_GUI;
import in.geobullet.csci_4176_project.Manage_Bulletins;
import in.geobullet.csci_4176_project.Manage_Posters;
import in.geobullet.csci_4176_project.MapsActivity;
import in.geobullet.csci_4176_project.Nearby_bulletin_boards;
import in.geobullet.csci_4176_project.Poster_Search;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Shared.SessionData;

/**
 * Class overrides the navigationview on selected item listener so as to avoid repeat code
 *
 *
 */
public class NavViewListener implements NavigationView.OnNavigationItemSelectedListener{

    private Context context;

    public NavViewListener(Context context){
        this.context = context;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accountInfo) {
            Intent i=null;

            if (SessionData.currentUser == null) {
                i = new Intent(context, Login.class);
                context.startActivity(i);
            }
            else {
                if( !(context instanceof Account_info) ){
                    i = new Intent(context, Account_info.class);
                    context.startActivity(i);
                }
            }
        } else if (id == R.id.nav_MainGUI) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, Main_GUI.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, Main_GUI.class);
                context.startActivity(i);
            }
        } else if (id == R.id.nav_mapGUI) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, MapsActivity.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, MapsActivity.class);
                context.startActivity(i);
            }
        } else if (id == R.id.manage_my_bulletin_boards) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, Manage_Bulletins.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, Manage_Bulletins.class);
                context.startActivity(i);
            }
        } else if (id == R.id.nav_searchEvents) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, Poster_Search.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, Poster_Search.class);
                context.startActivity(i);
            }
        } else if (id == R.id.nav_nearby_bulletin_boards) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, Nearby_bulletin_boards.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, Nearby_bulletin_boards.class);
                context.startActivity(i);
            }
        }else if (id == R.id.manage_my_posters) {
            if ( context instanceof Account_info ) {
                Intent i = new Intent(context, Manage_Posters.class);
                ((Account_info) context).finish();
                context.startActivity(i);
            }else {
                Intent i = new Intent(context, Manage_Posters.class);
                context.startActivity(i);
            }
        }

        return true;
    }
}
