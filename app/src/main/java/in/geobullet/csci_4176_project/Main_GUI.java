package in.geobullet.csci_4176_project;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import in.geobullet.csci_4176_project.Utils.NavViewListener;
import in.geobullet.csci_4176_project.db.Classes.Board;
import in.geobullet.csci_4176_project.db.Classes.Poster;
import in.geobullet.csci_4176_project.db.Classes.PosterType;
import in.geobullet.csci_4176_project.db.DatabaseHandler;


public class Main_GUI extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__gui);
        final DatabaseHandler dbHandler = new DatabaseHandler(this);
        String selected_poster_type = null;

        Intent intent=this.getIntent();

        if(intent != null)
            selected_poster_type = intent.getStringExtra("postertype");

        int BOARD_ID = 1;//SessionData.boardId;
        int left_margin_index = 1;
        int top_margin_index = 1;
        String poster_name = null;

        Board board = dbHandler.getBoardById(BOARD_ID);

        if (board != null) {

            List<Poster> postersForBoard1 = dbHandler.getPostersForBoard(board.getId());

            for (Poster p : postersForBoard1) {
                ImageButton iv = new ImageButton(this);
                poster_name = p.getPhotoName();
                poster_name = poster_name.substring(0, poster_name.lastIndexOf("."));

                int resID = this.getResources().getIdentifier(poster_name, "drawable", this.getPackageName());
                iv.setImageResource(resID);

                RelativeLayout RelLayout = (RelativeLayout) findViewById(R.id.rel_in_horz);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(selected_poster_type.equals("Event"))
                {
                    if(p.getPosterType().equals(PosterType.Event))
                    {
                        if(left_margin_index == 1)
                        {
                            params.setMargins(50, 50, 0, 0);
                            iv.setLayoutParams(params);
                            RelLayout.addView(iv);
                        }
                        else
                        {
                            params.setMargins(left_margin_index*500, 0, 0, 0);
                            iv.setLayoutParams(params);
                            RelLayout.addView(iv);
                        }
                    }
                    else;
                }
                else        //selected_poster_type == "Service"
                {
                    if(p.getPosterType().equals(PosterType.Service))
                    {
                        if(left_margin_index == 1)
                        {
                            params.setMargins(50, 50, 0, 0);
                            iv.setLayoutParams(params);
                            RelLayout.addView(iv);
                        }
                        else
                        {
                            params.setMargins(left_margin_index*500, 0, 0, 0);
                            iv.setLayoutParams(params);
                            RelLayout.addView(iv);
                        }
                    }
                    else;
                }

                top_margin_index++;
                left_margin_index++;
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


        //Changed how nav view operates, listener has now been moved into its own class so repeat code is avoided
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavViewListener(this));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

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
/*
    @Override
    public void onClick(View view) {

        int i = view.getId();
        int t = 0;
        t++;
        /*
        switch(view.getId()) {
            case
        }

    } */
}