/*
*    earth image source: https://icons8.com/web-app/5561/globe
*    pin image http://fabricmate.com/tackable-panels
*
*/

package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Account_info extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        //db user info
        DatabaseHandler db = new DatabaseHandler(this);

        User currentUser = new User();
        currentUser = db.getUserById(1);

        TextView username = (TextView) findViewById(R.id.editUsername);
        username.setText(currentUser.getDisplayName());
        TextView first_name = (TextView) findViewById(R.id.edit_firstname);
        first_name.setText(currentUser.getFirstName());
        TextView last_name = (TextView) findViewById(R.id.editLastname);
        last_name.setText(currentUser.getLastName());
        TextView email = (TextView) findViewById(R.id.edit_Email);
        email.setText(currentUser.getEmail());


        //menu component
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //display login username at toolbar
        getSupportActionBar().setTitle("Login as: "+ currentUser.getDisplayName());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_right_top_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accountInfo) {
            // Handle the camera action

            //startActivity(new Intent(MainActivity.this,Main_GUI.class));

        } else if (id == R.id.nav_MainGUI) {
            Intent i = new Intent(Account_info.this, Main_GUI.class);
            finish();
            startActivity(i);

        } else if (id == R.id.nav_mapGUI) {

            //Intent i = new Intent(MainActivity.this, MapsActivity.class);
            //startService(i);
            //vf.setDisplayedChild(1);

        } else if (id == R.id.create_poster) {

            Intent i = new Intent(Account_info.this, CreateNewPoster.class);
            startActivity(i);
            //vf.setDisplayedChild(2);

        } else if (id == R.id.nav_manageBulletins) {

        } else if (id == R.id.create_nearByBulletins) {

        } else if (id == R.id.nav_searchEvents) {

        } else if (id == R.id.nav_manageEvents) {

        } else if (id == R.id.nav_createEvents) {

        } else if (id == R.id.nav_addEvent) {

        } else if (id == R.id.nav_delBulletinBoards) {

        } else if (id == R.id.nav_achievement) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
