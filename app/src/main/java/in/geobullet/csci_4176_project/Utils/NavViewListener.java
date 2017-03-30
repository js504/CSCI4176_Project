package in.geobullet.csci_4176_project.Utils;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import in.geobullet.csci_4176_project.Account_info;
import in.geobullet.csci_4176_project.CreateNewPoster;
import in.geobullet.csci_4176_project.Login;
import in.geobullet.csci_4176_project.Main_GUI;
import in.geobullet.csci_4176_project.Manage_Bulletins;
import in.geobullet.csci_4176_project.MapsActivity;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.SessionData;

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
        //swtich the layout of the content
        //ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accountInfo) {
            // Handle the camera action

            Intent i;
            if(SessionData.currentUser == null){
                i = new Intent(context, Login.class);
            }
            else{
                i = new Intent(context, Account_info.class);
            }


            context.startActivity(i);


        } else if (id == R.id.nav_MainGUI) {

            Intent i = new Intent(context, Main_GUI.class);
            context.startActivity(i);
            //vf.setDisplayedChild(2);

        } else if (id == R.id.nav_mapGUI) {
            Intent i = new Intent(context, MapsActivity.class);
            context.startActivity(i);
            //vf.setDisplayedChild(1);

        } else if (id == R.id.create_poster) {

            Intent i = new Intent(context, CreateNewPoster.class);
            context.startActivity(i);
            //vf.setDisplayedChild(2);

        } else if (id == R.id.nav_manageBulletins) {
            Intent i = new Intent(context, Manage_Bulletins.class);
            context.startActivity(i);
        } else if (id == R.id.nav_searchEvents) {

        } else if (id == R.id.nav_manageEvents) {

        }  else if (id == R.id.nav_delBulletinBoards) {

        }

        return true;
    }
}
