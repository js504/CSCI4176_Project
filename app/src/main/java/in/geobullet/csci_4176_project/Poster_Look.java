package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by darrellread on 2017-04-03.
 */

public class Poster_Look extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poster_look);
        String passed_postername = null;
        String poster_details = null;

        Bundle bundle = getIntent().getExtras();
        passed_postername = bundle.getString("postername");
        poster_details = bundle.getString("posterdetails");


        ImageView iv = new ImageView(this);
        TextView tv = new TextView(this);

        LinearLayout LinLayout = (LinearLayout) findViewById(R.id.poster_look_lay);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int resID = this.getResources().getIdentifier(passed_postername, "drawable", this.getPackageName());
        iv.setImageResource(resID);

        params.setMargins(0, 0, 0, 0);
        iv.setLayoutParams(params);
        LinLayout.addView(iv);

        tv.setText(poster_details);
        params.setMargins(20, 0, 0, 0);
        tv.setLayoutParams(params);
        LinLayout.addView(tv);

    }
}
