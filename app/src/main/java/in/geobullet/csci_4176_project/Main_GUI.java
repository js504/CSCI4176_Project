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
import android.widget.HorizontalScrollView;

import java.util.List;

import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Main_GUI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui);


        int BOARD_ID = 0;

        final DatabaseHandler dbHandler = new DatabaseHandler(this);

        Board board = dbHandler.getBoardById(BOARD_ID);

        if (board != null) {

            // todo: Use board to set page title, radius,

            List<Poster> postersForBoard1 = dbHandler.getPostersForBoard(BOARD_ID);

            HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

            for (Poster p : postersForBoard1) {

                // todo finish

                //ImageView iv = new ImageView();

                // something like this
                //iv.setImage(p.getPhotoName());

                //hScrollView.addView(iv);
            }
        }



        //add menu component
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.main_gui_right_top_settings, menu);
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
            Intent i = new Intent(Main_GUI.this, Login.class);
            finish();
            startActivity(i);
        } else if (id == R.id.nav_MainGUI) {

        } else if (id == R.id.nav_mapGUI) {
            Intent i = new Intent(Main_GUI.this, MapsActivity.class);
            startActivity(i);
        } else if (id == R.id.create_poster) {
            Intent i = new Intent(Main_GUI.this, CreateNewPoster.class);
            startActivity(i);
        } else if (id == R.id.nav_manageBulletins) {

        } else if (id == R.id.create_nearByBulletins) {
            //nearby boards
            Intent i = new Intent(Main_GUI.this, NearbyBoards.class);
            startActivity(i);
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
