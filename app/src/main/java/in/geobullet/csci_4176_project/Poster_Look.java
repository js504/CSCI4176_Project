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
        String poster_address = null;
        String poster_date = null;

        Bundle bundle = getIntent().getExtras();
        passed_postername = bundle.getString("postername");
        poster_details = bundle.getString("posterdetails");
        poster_address = bundle.getString("posteraddress");
        poster_date = bundle.getString("posterdate");

        ImageView iv = new ImageView(this);
        TextView details = new TextView(this);
        TextView address = new TextView(this);
        TextView date = new TextView(this);

        LinearLayout LinLayout = (LinearLayout) findViewById(R.id.lin_in_scroll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int resID = this.getResources().getIdentifier(passed_postername, "drawable", this.getPackageName());
        iv.setImageResource(resID);

        params.setMargins(0, 0, 0, 0);
        iv.setLayoutParams(params);
        LinLayout.addView(iv);

        details.setText("Details: " + poster_details);
        params.setMargins(10, 0, 0, 0);
        details.setLayoutParams(params);
        LinLayout.addView(details);

        address.setText("Address: " + poster_address);
        params.setMargins(10, 20, 0, 0);
        address.setLayoutParams(params);
        LinLayout.addView(address);

        date.setText("Date:" + poster_date);
        params.setMargins(10, 20, 0, 0);
        date.setLayoutParams(params);
        LinLayout.addView(date);

    }
}
